package com.example.erpnext.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.ModeOfPaymentsAdapter;
import com.example.erpnext.adapters.POSInvoiceItemsAdapter;
import com.example.erpnext.adapters.viewHolders.POSInvoiceItemsViewHolder;
import com.example.erpnext.callbacks.SelectedInvoiceCallback;
import com.example.erpnext.models.Doc;
import com.example.erpnext.models.DocOPE;
import com.example.erpnext.models.Field;
import com.example.erpnext.models.Invoice;
import com.example.erpnext.models.InvoicePayment;
import com.example.erpnext.models.PaymentReconciliation;
import com.example.erpnext.models.PosTransaction;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.GetDocPOSOpeningResponse;
import com.example.erpnext.network.serializers.response.GetLinkedInvoicesResponse;
import com.example.erpnext.network.serializers.response.SaveClosingEntryResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.FileUpload;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AddNewClosingActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkResponse, SelectedInvoiceCallback, DateTime.datePickerCallback {
    public static HashMap<String, String> data = new HashMap<String, String>();
    public static float netTotal = 0;
    public static float grandTotal = 0;
    public static float totalQuantity = 0;
    public static float openingAmount = 0;
    public static TextView netTotal_tv, grandTotal_tv, totalQuantity_tv;
    private final String selectedDate = "";
    private final List<Field> fieldList = new ArrayList<>();
    private final List<Field> linkFieldList = new ArrayList<>();
    public EditText closing_amount;
    private String selectedDateField = "";
    private AutoCompleteTextView invoice_edit;
    private TextView modeOfPayment, counter, opening_amount, difference_amount, expected_amount;
    private TextView addNewInvoice, postingDate_tv, periodStartDate_tv, periodEndDate_tv, posProfile_tv, company_tv, cashier_tv;
    private Doc doc;
    private String selectOpeningEntry;
    private RecyclerView selectedInvoiceRV, modeOfPaymentRv, readOnlyRv;
    private POSInvoiceItemsAdapter posInvoiceItemsAdapter;
    private ImageView back;
    private AutoCompleteTextView posOpeningEntry;
    private LinearLayout openingAmountLayout;
    private Button save_btn;
    private Dialog dialog;
    private String selectedInvoice;

    public static void setTotal() {
        netTotal_tv.setText("$ " + Utils.round(netTotal, 2));
        grandTotal_tv.setText("$ " + Utils.round(grandTotal, 2));
        totalQuantity_tv.setText("" + Utils.round(totalQuantity, 2));
        data.put("grand_total", String.valueOf(grandTotal));
        data.put("net_total", String.valueOf(netTotal));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_closing);
        initViews();
        setClickisteners();
        if (getIntent().hasExtra("doc")) {
            String docJsonString = (String) getIntent().getExtras().get("doc");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            doc = gson.fromJson(docJsonString, Doc.class);
        }
    }

    private void setClickisteners() {
        back.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        addNewInvoice.setOnClickListener(this);
        postingDate_tv.setOnClickListener(this);
        periodEndDate_tv.setOnClickListener(this);
        periodStartDate_tv.setOnClickListener(this);

    }

    private void initViews() {
        addNewInvoice = findViewById(R.id.add);
        postingDate_tv = findViewById(R.id.posting_date_edit);
        periodStartDate_tv = findViewById(R.id.period_start_date_edit);
        back = findViewById(R.id.back);
        save_btn = findViewById(R.id.save);
        periodEndDate_tv = findViewById(R.id.period_end_date_edit);
        posProfile_tv = findViewById(R.id.pos_profile_edit);
        cashier_tv = findViewById(R.id.cashier_edit);
        company_tv = findViewById(R.id.company_edit);
        grandTotal_tv = findViewById(R.id.grand_total_edit);
        netTotal_tv = findViewById(R.id.net_total_edit);
        totalQuantity_tv = findViewById(R.id.total_quantity_edit);
        posOpeningEntry = findViewById(R.id.pos_opening_edit);
        selectedInvoiceRV = findViewById(R.id.pos_invoice_rv);
        modeOfPaymentRv = findViewById(R.id.mode_of_payment_rv);
        modeOfPayment = findViewById(R.id.mode_ofPayment);
        opening_amount = findViewById(R.id.opening_amount);
        closing_amount = findViewById(R.id.closing_amount);
        expected_amount = findViewById(R.id.expected_amount);
        difference_amount = findViewById(R.id.difference_amount);
        posOpeningEntry.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                getLinkSearch("POS Opening Entry", "POS Closing Entry");
            }
        });
        posOpeningEntry.setOnItemClickListener((parent, view, position, id) -> {
            selectOpeningEntry = (String) parent.getItemAtPosition(position);
            getPOSOpeningDoc();
        });

    }

    private void setInvoicesAdapter(List<Invoice> invoices) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        posInvoiceItemsAdapter = new POSInvoiceItemsAdapter(this, invoices, this);
        selectedInvoiceRV.setLayoutManager(linearLayoutManager);
        selectedInvoiceRV.setAdapter(posInvoiceItemsAdapter);
    }

    private void setModeOfPaymentsAdapter(List<InvoicePayment> balanceDetailList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ModeOfPaymentsAdapter modeOfPaymentsAdapter = new ModeOfPaymentsAdapter(this, balanceDetailList);
        modeOfPaymentRv.setLayoutManager(linearLayoutManager);
        modeOfPaymentRv.setAdapter(modeOfPaymentsAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.add:
                showInvoiceDialog();
                break;
            case R.id.posting_date_edit:
                DateTime.showDatePicker(this, this);
                selectedDateField = "posting_date";
                break;

            case R.id.save:
                if (!fieldError()) {
                    List<Invoice> invoices = posInvoiceItemsAdapter.getAllItems();
                    List<PosTransaction> posTransactionList = getPosTransactions(invoices);
                    List<PaymentReconciliation> paymentReconciliationList = getPaymentReconciliation(invoices);
                    data.put("posting_date", postingDate_tv.getText().toString());
                    JSONObject json = new JSONObject(data);
                    try {
                        Gson gson = new Gson();
                        JSONArray jsonObject = new JSONArray(gson.toJson(posTransactionList));
                        JSONArray jsonObject1 = new JSONArray(gson.toJson(paymentReconciliationList));
                        json.putOpt("pos_transactions", jsonObject);
                        json.putOpt("payment_reconciliation", jsonObject1);
                        saveDoc(json);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

                break;
        }

    }

    private List<PaymentReconciliation> getPaymentReconciliation(List<Invoice> invoices) {
        PaymentReconciliation paymentReconciliation = new PaymentReconciliation();
        paymentReconciliation.setClosingAmount(Float.parseFloat(closing_amount.getText().toString()));
        paymentReconciliation.setModeOfPayment("Cash");
        paymentReconciliation.setDoctype("POS Closing Entry Detail");
        paymentReconciliation.setName("pos-closing-entry-detail");
        paymentReconciliation.setDocstatus(1);
        paymentReconciliation.setDifference(Double.parseDouble(expected_amount.getText().toString()));
        paymentReconciliation.setDifference(Double.parseDouble(difference_amount.getText().toString()));
        paymentReconciliation.setOpeningAmount(String.valueOf(openingAmount));
        paymentReconciliation.setIdx(1);
        paymentReconciliation.setIslocal(1);
        paymentReconciliation.setUnsaved(1);
        paymentReconciliation.setParent("new-pos-closing-entry");
        paymentReconciliation.setParentfield("payment_reconciliation");
        paymentReconciliation.setParenttype("POS Closing Entry");
        paymentReconciliation.setOwner(invoices.get(0).getOwner());
        List<PaymentReconciliation> paymentReconciliationList = new ArrayList<>();
        paymentReconciliationList.add(paymentReconciliation);
        return paymentReconciliationList;
    }

    private List<PosTransaction> getPosTransactions(List<Invoice> invoices) {
        List<PosTransaction> posTransactionList = new ArrayList<>();
        for (Invoice invoice : invoices) {
            PosTransaction posTransaction = new PosTransaction();
            posTransaction.setDocstatus(invoice.getDocstatus());
            posTransaction.setIslocal(1);
            posTransaction.setUnsaved(1);
            posTransaction.setDoctype("POS Invoice Reference");
            posTransaction.setCustomer(invoice.getCustomer());
            posTransaction.setIdx(invoice.getIdx());
            posTransaction.setGrandTotal(Float.parseFloat(String.valueOf(invoice.getGrandTotal())));
            posTransaction.setParent("new-pos-closing-entry");
            posTransaction.setParentfield("pos_transactions");
            posTransaction.setParenttype("POS Closing Entry");
            posTransaction.setPosInvoice(invoice.getName());
            posTransaction.setPostingDate(invoice.getPostingDate());
            posTransactionList.add(posTransaction);
        }
        return posTransactionList;
    }

    private boolean fieldError() {
        if (!data.containsKey("period_start_date") || data.get("period_start_date") == null || data.get("period_start_date").isEmpty()) {
            Notify.Toast(getString(R.string.select_period_start));
            return true;
        } else if (!data.containsKey("posting_date") || data.get("posting_date") == null || data.get("posting_date").isEmpty()) {
            Notify.Toast(getString(R.string.select_posting_date));
            return true;
        } else if (!data.containsKey("company") || data.get("company") == null || data.get("company").isEmpty()) {
            Notify.Toast(getString(R.string.select_company));
            return true;
        } else if (!data.containsKey("pos_profile") || data.get("pos_profile") == null || data.get("pos_profile").isEmpty()) {
            Notify.Toast(getString(R.string.select_pos_profile));
            return true;
        } else if (!data.containsKey("user") || data.get("user") == null || data.get("user").isEmpty()) {
            Notify.Toast(getString(R.string.select_cashier));
            return true;
        } else if (posInvoiceItemsAdapter.getAllItems() == null || posInvoiceItemsAdapter.getAllItems().isEmpty()) {
            Notify.Toast(getString(R.string.close_entry));
            return true;
        } else {
            return false;
        }
    }

    private void showInvoiceDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_invoice_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        invoice_edit = dialog.findViewById(R.id.pos_invoice_edit);
        Button add = dialog.findViewById(R.id.add_invoice);

        invoice_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getLinkSearch("POS Invoice", "POS Invoice Reference");
                }
            }
        });
        invoice_edit.setOnItemClickListener((parent, view, position, id) -> {
            selectedInvoice = (String) parent.getItemAtPosition(position);
            invoice_edit.setText(selectedInvoice);
        });
        invoice_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                add.setEnabled(!s.toString().equalsIgnoreCase(""));
            }
        });
        add.setOnClickListener(v -> {
//            posInvoiceItemsAdapter.addItem(selectedInvoice);
            dialog.dismiss();
        });

    }

    @Override
    public void onDeleteClick(Invoice item, POSInvoiceItemsViewHolder viewHolder, int position) {
        posInvoiceItemsAdapter.removeItem(item, position);
        netTotal = netTotal - Float.parseFloat(String.valueOf(item.getNetTotal()));
        grandTotal = grandTotal - Float.parseFloat(String.valueOf(item.getGrandTotal()));
        totalQuantity = totalQuantity - Float.parseFloat(String.valueOf(item.getTotalQty()));
        setTotal();
    }

    @Override
    public void onSelected(String date) {
        if (selectedDateField.equalsIgnoreCase("posting_date")) {
            postingDate_tv.setText(date);
        } else if (selectedDateField.equalsIgnoreCase("period_start_date")) {
            periodStartDate_tv.setText(date);
        } else if (selectedDateField.equalsIgnoreCase("period_end_date")) {
            periodEndDate_tv.setText(date);
        }
        AddNewOpeningActivity.data.put(selectedDateField, date);
    }

    private void setvalues(DocOPE docOPE) {
        data.put("doctype", doc.getName());
        data.put("docstatus", doc.getDocstatus().toString());
        data.put("name", "new-pos-closing-entry");
        data.put("__islocal", "1");
        data.put("__unsaved", "1");
        data.put("owner", docOPE.getOwner());
        data.put("status", "Draft");
        data.put("company", docOPE.getCompany());
        data.put("period_end_date", "" + docOPE.getPeriodEndDate());
        data.put("posting_date", docOPE.getPostingDate());
        data.put("user", docOPE.getUser());
        data.put("pos_profile", docOPE.getPosProfile());
        data.put("period_start_date", docOPE.getPeriodStartDate());
        data.put("pos_opening_entry", selectOpeningEntry);
    }

    private void saveDoc(JSONObject jsonObject) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SAVE_DOC)
                .autoLoadingCancel(Utils.getLoading(this, "Please wait..."))
//                .enque(Network.apis().saveClosingEntry(jsonObject, "Submit"))
                .enque(Network.apis().saveClosingEntry(FileUpload.saveDoc(jsonObject, "Save")))
                .execute();
    }

    public void getPOSOpeningDoc() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_DOC)
                .autoLoadingCancel(Utils.getLoading(this, "Loading..."))
                .enque(Network.apis().getDocPOSOpening("POS Opening Entry", selectOpeningEntry))
                .execute();
    }

    public void getLinkedInvoices(String start, String end, String posProfile, String user) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_INVOICES)
                .autoLoadingCancel(Utils.getLoading(this, "Loading..."))
                .enque(Network.apis().getLinkedInvoices(start, end, posProfile, user))
                .execute();
    }

    private void getLinkSearch(String doctype, String reference) {
        int requestCode = RequestCodes.API.LINK_SEARCH;
        String filters = null;
        if (reference.equalsIgnoreCase("POS Invoice Reference")) {
            requestCode = RequestCodes.API.LINK_SEARCH_INVOICE;
        }
        if (doctype.equalsIgnoreCase("POS Opening Entry")) {
            filters = "{\"status\":\"Open\",\"docstatus\":1}";
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(this, "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", doctype, "0", filters, reference, null)))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.SAVE_DOC) {
            if (response.code() == 200) {
                SaveClosingEntryResponse res = (SaveClosingEntryResponse) response.body();
                Intent intent = new Intent();
                intent.putExtra("saveProfile", true);
                setResult(RESULT_OK, intent);
                Notify.Toast(getString(R.string.successfully_created));
                finish();

            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);
                posOpeningEntry.setAdapter(adapter);
                posOpeningEntry.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.GET_DOC) {
            GetDocPOSOpeningResponse res = (GetDocPOSOpeningResponse) response.body();
            if (res != null && res.getDocOPE() != null) {
                company_tv.setText(res.getDocOPE().getCompany());
                posProfile_tv.setText(res.getDocOPE().getPosProfile());
                cashier_tv.setText(res.getDocOPE().getUser());
                postingDate_tv.setText(res.getDocOPE().getPostingDate());
                periodStartDate_tv.setText(res.getDocOPE().getPeriodStartDate());
                periodEndDate_tv.setText("" + res.getDocOPE().getPeriodEndDate());
                openingAmount = res.getDocOPE().getOpening_amount();
                getLinkedInvoices(res.getDocOPE().getPeriodStartDate(), DateTime.getServerCurrentDateTime(), res.getDocOPE().getPosProfile(), res.getDocOPE().getUser());

                setvalues(res.getDocOPE());

            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_INVOICE) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue() + ",\n" + searchResult.getDescription());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);
                invoice_edit.setAdapter(adapter);
                invoice_edit.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.GET_INVOICES) {
            GetLinkedInvoicesResponse res = (GetLinkedInvoicesResponse) response.body();
            if (res.getInvoices() != null && !res.getInvoices().isEmpty()) {
                grandTotal = 0;
                netTotal = 0;
                totalQuantity = 0;
                setInvoicesAdapter(res.getInvoices());

                float expectedAmount = 0;
                for (Invoice invoice : res.getInvoices()) {
                    for (InvoicePayment invoicePayment : invoice.getPayments()) {
                        expectedAmount = expectedAmount + Float.parseFloat(String.valueOf(invoicePayment.getBaseAmount()));
                    }
                }
                expected_amount.setText(String.valueOf(expectedAmount));
                difference_amount.setText(String.valueOf(-Math.abs(expectedAmount)));
                opening_amount.setText(String.valueOf(openingAmount));
            } else {
                Notify.Toast(getString(R.string.no_linked_invoice));
                setInvoicesAdapter(new ArrayList<Invoice>());
                expected_amount.setText(String.valueOf(0));
                difference_amount.setText(String.valueOf(Math.abs(0)));
                opening_amount.setText(String.valueOf(0));

                netTotal = 0;
                grandTotal = 0;
                totalQuantity = 0;
                setTotal();
            }
        }


    }


    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.Toast(response.getMessage());
    }


}