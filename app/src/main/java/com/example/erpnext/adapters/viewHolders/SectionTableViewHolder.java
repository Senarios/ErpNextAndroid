package com.example.erpnext.adapters.viewHolders;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewPOSProfileActivity;
import com.example.erpnext.callbacks.SectionBreaksCallback;
import com.example.erpnext.fragments.POSProfileDetailFragment;
import com.example.erpnext.models.Field;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkRequestBody;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.roomDB.data.DBSearchLink;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class SectionTableViewHolder extends RecyclerView.ViewHolder implements OnNetworkResponse, AdapterView.OnItemSelectedListener {

    public TextView fieldName, editFieldLabel, required, spinnerLabel, spinnerRequired;
    public CheckBox checkBox;
    public Spinner spinner;
    public AutoCompleteTextView editText;
    public View parent;
    boolean isEditMode;
    Field field;
    RelativeLayout editLayout, spinnerLayout;
    Context context;
    String doctype, filters, query, reference;

    public SectionTableViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        fieldName = itemView.findViewById(R.id.name);
        editFieldLabel = itemView.findViewById(R.id.field_label);
        editLayout = itemView.findViewById(R.id.edit_field_layout);
        editText = itemView.findViewById(R.id.autoCompleteEdit);
        required = itemView.findViewById(R.id.required);
        spinner = itemView.findViewById(R.id.spinner);
        spinnerLabel = itemView.findViewById(R.id.spinner_field_label);
        spinnerLayout = itemView.findViewById(R.id.spinner_layout);
        spinnerRequired = itemView.findViewById(R.id.spinner_required);
        checkBox = itemView.findViewById(R.id.check_box);


    }

    public void setData(Context context, Field field, SectionBreaksCallback callback, boolean isEditMode) {
        this.context = context;
        this.field = field;
        this.isEditMode = isEditMode;
        if (field.getFieldtype().equalsIgnoreCase("Check")) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setText(field.getLabel());
            ifCheckField();
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (isEditMode) {
                        POSProfileDetailFragment.editedData.put(field.getFieldname(), "1");
                    } else {
                        AddNewPOSProfileActivity.data.put(field.getFieldname(), "1");
                    }
                } else {
                    if (isEditMode) {
                        POSProfileDetailFragment.editedData.put(field.getFieldname(), "0");
                    } else {
                        AddNewPOSProfileActivity.data.put(field.getFieldname(), "0");
                    }
                }
            });

        } else if (field.getFieldtype().equalsIgnoreCase("Link")) {
            editLayout.setVisibility(View.VISIBLE);
            editFieldLabel.setText(field.getLabel());
            isLinkField();
            if (field.getReqd() == 1) {
                required.setVisibility(View.VISIBLE);
            }

        } else if (field.getFieldtype().equalsIgnoreCase("Select") || field.getFieldtype().equalsIgnoreCase("Table")) {
//            if (field.getLabel().equalsIgnoreCase("Applicable for users")) {
//                editLayout.setVisibility(View.VISIBLE);
//                editFieldLabel.setText(field.getLabel());
//                isLinkField();
//                if (field.getReqd() == 1) {
//                    required.setVisibility(View.VISIBLE);
//                }
//            } else
            if (!field.getLabel().equalsIgnoreCase("Applicable for users") && !field.getLabel().equalsIgnoreCase("Item Groups") && !field.getLabel().equalsIgnoreCase("Customer Groups")) {
                editLayout.setVisibility(View.VISIBLE);
                editFieldLabel.setText(field.getLabel());
                isLinkField();
                if (field.getReqd() == 1) {
                    required.setVisibility(View.VISIBLE);
                }

//                spinnerLayout.setVisibility(View.VISIBLE);
//                spinnerLabel.setText(field.getLabel());
//                if (field.getReqd() == 1) {
//                    spinnerRequired.setVisibility(View.VISIBLE);
//                }
//                spinner.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                            if (field.getFieldname().equalsIgnoreCase("apply_discount_on")) {
//                                String[] lines = field.getOptions().split("\\n");
//                                setSpinner(lines, spinner);
//                            } else getLinkSearch(field, RequestCodes.API.TABLE_SEARCH);
//                        }
//                        return false;
//                    }
//                });
            }
        } else {
            fieldName.setVisibility(View.VISIBLE);
            fieldName.setText(field.getLabel());
        }

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (field.getLabel().equalsIgnoreCase("Write Off Account")) {
                        getWriteOfAccountLinkSearch(field, RequestCodes.API.LINK_SEARCH);
                    } else if (field.getLabel().equalsIgnoreCase("Write Off Cost Center")) {
                        getWriteOfCostAccountLinkSearch(field, RequestCodes.API.LINK_SEARCH);
                    } else if (field.getLabel().equalsIgnoreCase("Income Account")) {
                        getIncomeAccountLinkSearch(field, RequestCodes.API.LINK_SEARCH);
                    } else if (field.getLabel().equalsIgnoreCase("Expense Account")) {
                        getExpenseAccountLinkSearch(field, RequestCodes.API.LINK_SEARCH);
                    } else if (field.getLabel().equalsIgnoreCase("Account for change amount")) {
                        getChangeAmountLinkSearch(field, RequestCodes.API.LINK_SEARCH);
                    } else if (field.getLabel().equalsIgnoreCase("Taxes and Charges")) {
                        getTaxesAndChargesLinkSearch(field, RequestCodes.API.LINK_SEARCH);
                    } else if (field.getLabel().equalsIgnoreCase("Terms and conditions")) {
                        getTermsAndConditionLinkSearch(field, RequestCodes.API.LINK_SEARCH);
                    } else if (field.getLabel().equalsIgnoreCase("Apply Discount On")) {
                        String[] lines = field.getOptions().split("\\n");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                android.R.layout.simple_list_item_1, lines);
                        editText.setAdapter(adapter);
                    } else if (field.getLabel().equalsIgnoreCase("Terms and conditions")) {
                        getTermsAndConditionLinkSearch(field, RequestCodes.API.LINK_SEARCH);
                    } else if (field.getLabel().equalsIgnoreCase("Applicable for Users")) {

                    } else {
                        getLinkSearch(field, RequestCodes.API.LINK_SEARCH);
                    }
                }
            }
        });

        editText.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            if (isEditMode) {
                POSProfileDetailFragment.editedData.put(field.getFieldname(), selectedItem);
            } else {
                AddNewPOSProfileActivity.data.put(field.getFieldname(), selectedItem);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEditMode) {
                    POSProfileDetailFragment.editedData.put(field.getFieldname(), s.toString());
                } else {
                    AddNewPOSProfileActivity.data.put(field.getFieldname(), s.toString());
                }
            }
        });

//        parent.setOnClickListener(view -> callback.onProfileClick(item));


    }

    private void isLinkField() {
        if (isEditMode) {
//            editText.setEnabled(false);
            if (field.getLabel().equalsIgnoreCase("Print Format")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getPrintFormat());
            } else if (field.getLabel().equalsIgnoreCase("Letter Head")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getLetterHead());
            } else if (field.getLabel().equalsIgnoreCase("Terms and Conditions")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getTcName());
            } else if (field.getLabel().equalsIgnoreCase("Print Heading")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getSelectPrintHeading());
            } else if (field.getLabel().equalsIgnoreCase("Price List")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getSellingPriceList());
            } else if (field.getLabel().equalsIgnoreCase("Currency")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getCurrency());
            } else if (field.getLabel().equalsIgnoreCase("Write Off Account")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getWriteOffAccount());
            } else if (field.getLabel().equalsIgnoreCase("Write Off Cost Center")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getWriteOffCostCenter());
            } else if (field.getLabel().equalsIgnoreCase("Account for Change Amount")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getAccountForChangeAmount());
            } else if (field.getLabel().equalsIgnoreCase("Income Account")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getIncomeAccount());
            } else if (field.getLabel().equalsIgnoreCase("Expense Account")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getExpenseAccount());
            } else if (field.getLabel().equalsIgnoreCase("Taxes and Charges")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getTaxesAndCharges());
            } else if (field.getLabel().equalsIgnoreCase("Tax Category")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getTaxCategory());
            } else if (field.getLabel().equalsIgnoreCase("Terms and conditions")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getTcName());
            } else if (field.getLabel().equalsIgnoreCase("Apply Discount On")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getApplyDiscountOn());
            } else if (field.getLabel().equalsIgnoreCase("Cost Center")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getCostCenter());
            } else if (field.getLabel().equalsIgnoreCase("Mode of payment")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getPayments().get(0).getModeOfPayment());
            }
        }
    }

    private void ifCheckField() {
        if (isEditMode) {
//            checkBox.setEnabled(false);

            if (field.getLabel().equalsIgnoreCase("Hide Images")) {
                checkBox.setChecked(POSProfileDetailFragment.docDetail.getDocs().get(0).getHideImages() == 1);
            } else if (field.getLabel().equalsIgnoreCase("Hide Unavailable Items")) {
                checkBox.setChecked(POSProfileDetailFragment.docDetail.getDocs().get(0).getHideUnavailableItems() == 1);
            } else if (field.getLabel().equalsIgnoreCase("Automatically Add Filtered Item To Cart")) {
                checkBox.setChecked(POSProfileDetailFragment.docDetail.getDocs().get(0).getAutoAddItemToCart() == 1);
            } else if (field.getLabel().equalsIgnoreCase("Update Stock")) {
                checkBox.setChecked(POSProfileDetailFragment.docDetail.getDocs().get(0).getUpdateStock() == 1);
            } else if (field.getLabel().equalsIgnoreCase("Ignore Pricing Rule")) {
                checkBox.setChecked(POSProfileDetailFragment.docDetail.getDocs().get(0).getIgnorePricingRule() == 1);
            } else if (field.getLabel().equalsIgnoreCase("Allow User to Edit Rate")) {
                checkBox.setChecked(POSProfileDetailFragment.docDetail.getDocs().get(0).getAllowRateChange() == 1);
            } else if (field.getLabel().equalsIgnoreCase("Allow User to Edit Discount")) {
                checkBox.setChecked(POSProfileDetailFragment.docDetail.getDocs().get(0).getAllowDiscountChange() == 1);
            }
        }
    }

    private void getLinkSearch(Field field, int RequestCode) {
        doctype = field.getLabel().trim();
        filters = null;
        query = null;
        reference = field.getParent();
        if (Utils.isNetworkAvailable()) {
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCode)
                    .autoLoadingCancel(Utils.getLoading((Activity) context, "searching"))
                    .enque(Network.apis().getSearchLink(new SearchLinkRequestBody("", field.getLabel().trim(), "0", field.getParent())))
                    .execute();
        } else {
            DBSearchLink.loadFromRef(context, doctype, filters, reference, editText);
        }
    }

    private void getWriteOfAccountLinkSearch(Field field, int RequestCode) {
        doctype = "Account";
        filters = "{\"report_type\":\"Profit and Loss\",\"is_group\":0,\"company\":\"Izat Afghan Limited\"}";
        query = "";
        reference = field.getParent();
        if (Utils.isNetworkAvailable()) {
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCode)
                    .autoLoadingCancel(Utils.getLoading((Activity) context, "searching"))
                    .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", doctype, "0", filters, reference, query)))
                    .execute();
        } else DBSearchLink.load(context, doctype, filters, reference, query, editText);
    }

    private void getWriteOfCostAccountLinkSearch(Field field, int RequestCode) {
        doctype = "Cost Center";
        filters = "{\"is_group\":0,\"company\":\"Izat Afghan Limited\"}";
        query = "";
        reference = field.getParent();
        if (Utils.isNetworkAvailable()) {
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCode)
                    .autoLoadingCancel(Utils.getLoading((Activity) context, "searching"))
                    .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", doctype, "0", filters, field.getParent(), "")))
                    .execute();
        } else DBSearchLink.load(context, doctype, filters, reference, query, editText);
    }

    private void getIncomeAccountLinkSearch(Field field, int RequestCode) {
        doctype = "Account";
        filters = "{\"is_group\":0,\"company\":\"Izat Afghan Limited\",\"account_type\":\"Income Account\"}";
        query = "";
        reference = field.getParent();
        if (Utils.isNetworkAvailable()) {
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCode)
                    .autoLoadingCancel(Utils.getLoading((Activity) context, "searching"))
                    .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", doctype, "0", filters, field.getParent(), "")))
                    .execute();
        } else DBSearchLink.load(context, doctype, filters, reference, query, editText);
    }

    private void getExpenseAccountLinkSearch(Field field, int RequestCode) {
        doctype = "Account";
        filters = "{\"report_type\":\"Profit and Loss\",\"company\":\"Izat Afghan Limited\",\"is_group\":0}";
        query = "";
        reference = field.getParent();
        if (Utils.isNetworkAvailable()) {
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCode)
                    .autoLoadingCancel(Utils.getLoading((Activity) context, "searching"))
                    .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", doctype, "0", filters, field.getParent(), "")))
                    .execute();
        } else DBSearchLink.load(context, doctype, filters, reference, query, editText);
    }


    private void getChangeAmountLinkSearch(Field field, int RequestCode) {
        doctype = "Account";
        filters = "{\"account_type\":[\"in\",[\"Cash\",\"Bank\"]]}";
        query = "";
        reference = field.getParent();
        if (Utils.isNetworkAvailable()) {
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCode)
                    .autoLoadingCancel(Utils.getLoading((Activity) context, "searching"))
                    .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", doctype, "0", filters, field.getParent(), "")))
                    .execute();
        } else DBSearchLink.load(context, doctype, filters, reference, query, editText);
    }

    private void getTaxesAndChargesLinkSearch(Field field, int RequestCode) {
        doctype = "Sales Taxes and Charges Template";
        filters = "[[\"Sales Taxes and Charges Template\",\"company\",\"=\",\"Izat Afghan Limited\"],[\"Sales Taxes and Charges Template\",\"docstatus\",\"!=\",2]]";
        query = "";
        reference = field.getParent();
        if (Utils.isNetworkAvailable()) {
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCode)
                    .autoLoadingCancel(Utils.getLoading((Activity) context, "searching"))
                    .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", doctype, "0", filters, field.getParent(), "")))
                    .execute();
        } else DBSearchLink.load(context, doctype, filters, reference, query, editText);
    }

    private void getTermsAndConditionLinkSearch(Field field, int RequestCode) {
        doctype = "Terms and Conditions";
        filters = "{\"selling\":1}";
        query = "";
        reference = field.getParent();
        if (Utils.isNetworkAvailable()) {
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCode)
                    .autoLoadingCancel(Utils.getLoading((Activity) context, "searching"))
                    .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", "Terms and Conditions", "0", "{\"selling\":1}", field.getParent(), "")))
                    .execute();
        } else DBSearchLink.load(context, doctype, filters, reference, query, editText);
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null) {
                if (filters == null && query == null) {
                    DBSearchLink.saveWithRef(res, doctype, filters, reference);
                } else DBSearchLink.save(res, doctype, filters, reference, query);
            }
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, list);
                editText.setAdapter(adapter);
                editText.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.TABLE_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                String[] stringlist = list.toArray(new String[0]);
                setSpinner(stringlist, spinner);
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }

    private void setSpinner(String[] list, Spinner spinner) {
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(context, R.layout.checkout_spinner_text, list);
        spinnerAdapter.setDropDownViewResource(R.layout.checkout_spinner_layout);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        if (isEditMode) {
            POSProfileDetailFragment.editedData.put(field.getFieldname(), selectedItem);
        } else {
            AddNewPOSProfileActivity.data.put(field.getFieldname(), selectedItem);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
