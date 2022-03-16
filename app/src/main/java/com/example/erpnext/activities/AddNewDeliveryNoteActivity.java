package com.example.erpnext.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.erpnext.R;
import com.example.erpnext.adapters.PurchaseItemsAdapter;
import com.example.erpnext.adapters.SearchItemsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.PurchaseItemCallback;
import com.example.erpnext.callbacks.SearchItesmCallback;
import com.example.erpnext.databinding.ActivityAddNewDeliveryNoteBinding;
import com.example.erpnext.databinding.AddPurchaseItemDialogBinding;
import com.example.erpnext.models.PartyDetail;
import com.example.erpnext.models.PurchaseItem;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.AddNewDeliveryNoteViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class AddNewDeliveryNoteActivity extends AppCompatActivity implements DateTime.datePickerCallback, SearchItesmCallback, PurchaseItemCallback {

    public static float netWeight = 0;
    public static float totalQuantity = 0;
    public static float totalUSD = 0;
    public static float baseGrandTotal = 0;
    public static float grandTotal = 0;
    public static HashMap<String, String> data = new HashMap<String, String>();
    static ActivityAddNewDeliveryNoteBinding binding;
    AddNewDeliveryNoteViewModel mViewModel;
    String postingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String postingTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    AddPurchaseItemDialogBinding addItemsBinding;
    PurchaseItemsAdapter purchaseItemsAdapter;
    PartyDetail partyDetail = new PartyDetail();
    Dialog dialog;
    String selectedItemCode;

    public static void setTotal() {
        binding.total.setText("$ " + Utils.round(totalUSD, 2));
        binding.totalNetWeight.setText("" + Utils.round(netWeight, 2));
        binding.totalQuantityEdit.setText("" + Utils.round(totalQuantity, 2));
        binding.grandTotal.setText("" + Utils.round(baseGrandTotal, 4));
        binding.roundedTotal.setText("" + Utils.round(baseGrandTotal, 2));
        float roundingAmount = Utils.round(baseGrandTotal, 4) - Utils.round(baseGrandTotal, 2);
        binding.roundingAdjustment.setText(String.valueOf(Utils.round(roundingAmount, 4)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_delivery_note);
        mViewModel = new ViewModelProvider(this).get(AddNewDeliveryNoteViewModel.class);

        //  binding.customer.
        setTextViews();
        setClickListeners();
        setFocusListeners();
        setObservers();
        setSelectedItemsAdapter();
    }

    private void resetValues() {
        netWeight = 0;
        totalQuantity = 0;
        totalUSD = 0;
        baseGrandTotal = 0;
        grandTotal = 0;
    }

    private void setTextViews() {
        binding.seriesDeliveryNote.setText("MAT-DN-.YYYY.-");
        binding.date.setText(postingDate);
        binding.postingTime.setText(postingTime);
    }

    private void setClickListeners() {
        binding.customerPODetailsLayout.setOnClickListener(v -> {
            if (binding.customerPODetailsInLayout.getVisibility() == View.VISIBLE) {
                binding.customerPODetailsInLayout.setVisibility(View.GONE);
                binding.customerPODetailsArrow.setImageDrawable(ContextCompat.getDrawable(AddNewDeliveryNoteActivity.this, R.drawable.ic_baseline_keyboard_arrow_down_24));
            } else {
                binding.customerPODetailsInLayout.setVisibility(View.VISIBLE);
                binding.customerPODetailsArrow.setImageDrawable(ContextCompat.getDrawable(AddNewDeliveryNoteActivity.this, R.drawable.ic_baseline_keyboard_arrow_up_24));
            }
        });

        binding.addressAndContactLayout.setOnClickListener(v -> {
            if (binding.addressAndContactInLayout.getVisibility() == View.VISIBLE) {
                binding.addressAndContactInLayout.setVisibility(View.GONE);
                binding.addressAndContactArrow.setImageDrawable(ContextCompat.getDrawable(AddNewDeliveryNoteActivity.this, R.drawable.ic_baseline_keyboard_arrow_down_24));
            } else {
                binding.addressAndContactInLayout.setVisibility(View.VISIBLE);
                binding.addressAndContactArrow.setImageDrawable(ContextCompat.getDrawable(AddNewDeliveryNoteActivity.this, R.drawable.ic_baseline_keyboard_arrow_up_24));
            }
        });

        binding.taxesAndChargesLayout.setOnClickListener(v -> {
            if (binding.taxesAndChargesInLayout.getVisibility() == View.VISIBLE) {
                binding.taxesAndChargesInLayout.setVisibility(View.GONE);
                binding.taxesAndChargesArrow.setImageDrawable(ContextCompat.getDrawable(AddNewDeliveryNoteActivity.this, R.drawable.ic_baseline_keyboard_arrow_down_24));
            } else {
                binding.taxesAndChargesInLayout.setVisibility(View.VISIBLE);
                binding.taxesAndChargesArrow.setImageDrawable(ContextCompat.getDrawable(AddNewDeliveryNoteActivity.this, R.drawable.ic_baseline_keyboard_arrow_up_24));
            }
        });

        binding.discountLayout.setOnClickListener(v -> {
            if (binding.discountInlayout.getVisibility() == View.VISIBLE) {
                binding.discountInlayout.setVisibility(View.GONE);
                binding.discountArrow.setImageDrawable(ContextCompat.getDrawable(AddNewDeliveryNoteActivity.this, R.drawable.ic_baseline_keyboard_arrow_down_24));
            } else {
                binding.discountInlayout.setVisibility(View.VISIBLE);
                binding.discountArrow.setImageDrawable(ContextCompat.getDrawable(AddNewDeliveryNoteActivity.this, R.drawable.ic_baseline_keyboard_arrow_up_24));
            }
        });

        binding.add.setOnClickListener(v -> {
            if (!binding.customer.getText().toString().isEmpty()) {
                //setItemsObservers();
                showAddItemDialog();
            } else Notify.Toast(getString(R.string.select_customer_to_continue));
        });

        binding.customer.setOnItemClickListener((parent, view, position, id) -> {
            data.put("customer", binding.customer.getText().toString());
            mViewModel.getPartyDetailsApi(binding.customer.getText().toString());
        });

        binding.sourceWarehouse.setOnItemClickListener((parent, view, position, id) -> data.put("set_warehouse", binding.sourceWarehouse.getText().toString()));


        binding.back.setOnClickListener(v -> {
            mViewModel.clearData();
            //resetValues();
            onBackPressed();
        });

        binding.discountPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty() && !s.toString().equalsIgnoreCase("0")) {
                    String percent = binding.discountPercent.getText().toString();
                    if (percent != null && !percent.isEmpty() && !percent.equalsIgnoreCase("0")) {
                        float percentfloat = Float.parseFloat(percent);
                        float discountedAmount = baseGrandTotal * percentfloat;
                        discountedAmount = discountedAmount / 100;
                        float afterDiscount = baseGrandTotal - discountedAmount;
                        binding.discountAmount.setText(String.valueOf(discountedAmount));
                        binding.grandTotal.setText(String.valueOf(afterDiscount));
                        baseGrandTotal = afterDiscount;
                        setTotal();
                    }
                } else {
                    getTotal();
                }
            }
        });

        binding.save.setOnClickListener(v -> {
            if (!fieldError()) {
                List<PurchaseItem> purchaseItems = getPurchaseItems();

                JSONObject jsonObject1 = new JSONObject(data);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject("{\"docstatus\":0,\"doctype\":\"Delivery Note\",\"name\":\"new-delivery-note-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"rvlirshad@gmail.com\",\"naming_series\":\"MAT-DN-.YYYY.-\",\"company\":\"Izat Afghan Limited\",\"posting_date\":\"2021-09-27\",\"set_posting_time\":0,\"is_return\":0,\"issue_credit_note\":0,\"currency\":\"USD\",\"selling_price_list\":\"Standard Selling\",\"price_list_currency\":\"USD\",\"ignore_pricing_rule\":0,\"apply_discount_on\":\"Grand Total\",\"disable_rounded_total\":0,\"lr_date\":\"2021-09-27\",\"is_internal_customer\":0,\"represents_company\":null,\"customer_group\":\"Nazeer\",\"territory\":\"Char qala\",\"letter_head\":\"Izat Afghan\",\"print_without_amount\":0,\"group_same_items\":0,\"status\":\"Draft\",\"conversion_rate\":1,\"plc_conversion_rate\":1,\"base_discount_amount\":0.611045134,\"total_commission\":null,\"base_net_total\":12.220902682,\"net_total\":12.220902682,\"base_total\":19.5,\"total\":19.5,\"total_qty\":4,\"rounding_adjustment\":-0.000902682,\"grand_total\":12.220902682,\"base_grand_total\":12.220902682,\"total_taxes_and_charges\":0,\"base_total_taxes_and_charges\":0,\"base_rounding_adjustment\":-0.000902682,\"rounded_total\":12.22,\"base_rounded_total\":12.22,\"in_words\":\"\",\"base_in_words\":\"\",\"tax_id\":null,\"customer_name\":\"abass\",\"customer\":\"abass\",\"customer_address\":null,\"address_display\":null,\"shipping_address_name\":\"\",\"shipping_address\":\"\",\"company_address_display\":\"\",\"contact_person\":\"abass-abass\",\"contact_display\":\"abass\",\"contact_email\":\"\",\"contact_mobile\":\"0799333814\",\"language\":\"en\",\"tax_category\":\"\",\"sales_team\":[{\"docstatus\":0,\"doctype\":\"Sales Team\",\"name\":\"new-sales-team-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"rvlirshad@gmail.com\",\"parent\":\"new-delivery-note-1\",\"parentfield\":\"sales_team\",\"parenttype\":\"Delivery Note\",\"idx\":1,\"sales_person\":\"Nazeer\",\"allocated_percentage\":100}],\"shipping_rule\":\"\",\"total_net_weight\":36,\"po_no\":\"\",\"set_warehouse\":\"khan  - IAL\"}");
                    jsonObject.put("owner", AppSession.get("email"));
                    jsonObject.put("posting_date", binding.date.getText().toString());
                    jsonObject.put("po_no", binding.customerPurchaseOrderNo.getText().toString());
                    jsonObject.put("po_date", binding.customerPurchaseOrderDate.getText().toString());
                    jsonObject.put("shipping_address", binding.shippingAddress.getText().toString());
                    jsonObject.put("company_address", binding.companyAddressName.getText().toString());
                    jsonObject.put("grand_total", String.valueOf(baseGrandTotal));
                    jsonObject.put("net_total", String.valueOf(baseGrandTotal));
                    jsonObject.put("rounded_total", binding.roundedTotal.getText().toString());
                    if (!binding.discountPercent.getText().toString().isEmpty() && !binding.discountAmount.getText().toString().isEmpty()) {
                        jsonObject.put("additional_discount_percentage", Float.parseFloat(binding.discountPercent.getText().toString()));
                        jsonObject.put("discount_amount", Float.parseFloat(binding.discountAmount.getText().toString()));
                    }

                    JSONObject json = new JSONObject();
                    Iterator i1 = jsonObject1.keys();
                    Iterator i2 = jsonObject.keys();
                    String tmp_key;

                    while (i2.hasNext()) {
                        tmp_key = (String) i2.next();
                        json.put(tmp_key, jsonObject.get(tmp_key));
                    }

                    while (i1.hasNext()) {
                        tmp_key = (String) i1.next();
                        json.put(tmp_key, jsonObject1.get(tmp_key));
                    }

                    Gson gson = new Gson();
                    JSONArray jsonArray = new JSONArray(gson.toJson(partyDetail.getSalesTeam()));
                    json.putOpt("sales_team", jsonArray);

                    Gson gson2 = new Gson();
                    JSONArray jsonArray2 = new JSONArray(gson2.toJson(purchaseItems));
                    json.putOpt("items", jsonArray2);

                    mViewModel.saveDocApi(json);
                    setSaveObserver();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.companyAddressName.setOnClickListener(v -> {
            mViewModel.getSearchLinkApi("Address", RequestCodes.API.LINK_SEARCH_COMPANY_ADDRESS_NAME);
        });

        binding.shippingAddress.setOnClickListener(v -> {
            mViewModel.getSearchLinkWithFilterApi("Address", RequestCodes.API.LINK_SEARCH_SHIPPING_ADDRESS, "frappe.contacts.doctype.address.address.address_query", "{\"link_doctype\":\"Customer\",\"link_name\":\"" + binding.customer.getText().toString() + "\"}");
        });
    }

    @NonNull
    private List<PurchaseItem> getPurchaseItems() {
        List<SearchItemDetail> items = purchaseItemsAdapter.getAllItems();
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        for (SearchItemDetail itemDetail : items) {
            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setDocstatus(0);
            purchaseItem.setDoctype("Delivery Note Item");
            purchaseItem.setName("new-delivery-note-item");
            purchaseItem.setIslocal(1);
            purchaseItem.setUnsaved(1);
            purchaseItem.setOwner(AppSession.get("email"));
            purchaseItem.setStockUom(itemDetail.getUom());
            purchaseItem.setRetainSample(0);
            purchaseItem.setParent("new-delivery-note");
            purchaseItem.setParentfield("items");
            purchaseItem.setParenttype("Delivery Note");
            purchaseItem.setIdx(1);
            purchaseItem.setUnedited(false);
            purchaseItem.setItemCode(itemDetail.getItemCode());
            purchaseItem.setQty(itemDetail.getQty().intValue());
            purchaseItem.setReceivedQty(itemDetail.getQty().intValue());
            purchaseItem.setStockQty(itemDetail.getQty().intValue());
            purchaseItem.setTotalWeight(itemDetail.getWeightPerUnit().floatValue() * itemDetail.getQty().floatValue());
            purchaseItem.setWeightUom(itemDetail.getWeightUom());
            purchaseItem.setPriceListRate(itemDetail.getPriceListRate());
            purchaseItem.setBasePriceListRate(itemDetail.getPriceListRate());
            purchaseItem.setMarginRateOrAmount(0);
            purchaseItem.setRateWithMargin(0);
            purchaseItem.setHasBatchNo(0);
            purchaseItem.setHasSerialNo(0);
            purchaseItem.setUpdateStock(0);
            purchaseItem.setRate(itemDetail.getPriceListRate());
            purchaseItem.setBaseRate(itemDetail.getPriceListRate());
            purchaseItem.setBaseAmount(itemDetail.getAmount());
            purchaseItem.setWeightPerUnit(itemDetail.getWeightPerUnit());
            purchaseItem.setItemName(itemDetail.getItemName());
            purchaseItem.setIncomeAccount(itemDetail.getIncomeAccount());
            purchaseItem.setExpenseAccount(itemDetail.getExpenseAccount());
            purchaseItem.setValuationRate(itemDetail.getValuationRate());
            purchaseItem.setImage(itemDetail.getImage());
            purchaseItem.setLastPurchaseRate(itemDetail.getLastPurchaseRate());
            purchaseItem.setTransactionDate(itemDetail.getTransactionDate());
            purchaseItem.setWeightUom(itemDetail.getWeightUom());
            purchaseItem.setItemGroup(itemDetail.getItemGroup());
            purchaseItems.add(purchaseItem);
        }
        return purchaseItems;
    }

    private boolean fieldError() {
        if (!data.containsKey("customer") || data.get("customer") == null || data.get("customer").isEmpty()) {
            Notify.Toast(getString(R.string.select_customer_to_continue));
            return true;
        } else if (purchaseItemsAdapter.getAllItems() == null || purchaseItemsAdapter.getAllItems().isEmpty()) {
            Notify.Toast(getString(R.string.add_items_to_continue));
            return true;
        } else if (!data.containsKey("set_warehouse") || data.get("set_warehouse") == null || data.get("set_warehouse").isEmpty()) {
            Notify.Toast(getString(R.string.select_source_warehouse));
            return true;
        } else {
            return false;
        }
    }

    private void getTotal() {
        float total = 0;
        List<SearchItemDetail> items = purchaseItemsAdapter.getAllItems();
        if (items != null) {
            for (SearchItemDetail itemDetail : items) {
                total = total + itemDetail.getAmount().floatValue();
            }
        }
        baseGrandTotal = total;
        binding.discountAmount.setText("");
        setTotal();
    }

    private void setFocusListeners() {
        binding.customer.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi("Customer", RequestCodes.API.LINK_SEARCH_CUSTOMER);
            }
        });

        binding.customerPurchaseOrderDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                DateTime.showDatePicker(this, this);
            }
        });

        binding.applyDiscountOn.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                SearchLinkResponse searchLinkResponse = getSearchLinkResponse();
                setSearchAdapter(binding.applyDiscountOn, searchLinkResponse);
            }
        });

        binding.sourceWarehouse.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkWithFilterApi("Warehouse", RequestCodes.API.LINK_SEARCH_SOURCE_WAREHOUSE, "", "[[\"Warehouse\",\"company\",\"in\",[\"\",\"Izat Afghan Limited\"]],[\"Warehouse\",\"is_group\",\"=\",0]]");
            }
        });
    }

    @NonNull
    private SearchLinkResponse getSearchLinkResponse() {
        SearchLinkResponse searchLinkResponse = new SearchLinkResponse();
        List<SearchResult> searchResults = new ArrayList<>();
        SearchResult searchResult = new SearchResult();
        searchResult.setValue("Grand Total");
        SearchResult searchResult1 = new SearchResult();
        searchResult1.setValue("Net Total");
        searchResults.add(searchResult);
        searchResults.add(searchResult1);
        searchLinkResponse.setResults(searchResults);
        return searchLinkResponse;
    }

    private void setObservers() {
        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse.getRequestCode() == RequestCodes.API.LINK_SEARCH_CUSTOMER) {
                setSearchAdapter(binding.customer, searchLinkResponse);
            } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.LINK_SEARCH_SHIPPING_ADDRESS) {
                setSearchAdapter(binding.shippingAddress, searchLinkResponse);
            } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.LINK_SEARCH_COMPANY_ADDRESS_NAME) {
                setSearchAdapter(binding.companyAddressName, searchLinkResponse);
            } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.LINK_SEARCH_SOURCE_WAREHOUSE) {
                setSearchAdapter(binding.sourceWarehouse, searchLinkResponse);
            }
        });

        mViewModel.getPartyDetail().observe(this, partyDetailResponse -> {
            partyDetail = partyDetailResponse.getMessage();
            setPartyFields();
        });

        mViewModel.getSearchedItems().observe(this, items -> {
            setSearchedItemsAdapter(items);
        });

        mViewModel.getItemDetail().observe(this, itemDetail -> {
            purchaseItemsAdapter.addItem(itemDetail);
        });

    }

    private void setSaveObserver() {
        mViewModel.docSaved().observe(this, doc -> {
            Notify.Toast(getString(R.string.successfully_created));
            mViewModel.clearData();
            finish();
        });
    }

    private void setPartyFields() {
        binding.shippingAddress.setText(partyDetail.getShippingAddress());
        binding.contactPerson.setText(partyDetail.getContactPerson());
        binding.contact.setText(partyDetail.getContactDisplay());
        binding.companyAddressName.setText(partyDetail.getCompanyAddressDisplay());
    }

    private void setSearchAdapter(AutoCompleteTextView textView, SearchLinkResponse searchItemResponse) {
        if (ViewCompat.isAttachedToWindow(textView)) {
            List<String> list = new ArrayList<>();
            for (SearchResult searchResult : searchItemResponse.getResults()) {
                list.add(searchResult.getValue());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, list);
            textView.setAdapter(adapter);
            textView.showDropDown();
        }
    }

    private void setSearchedItemsAdapter(List<List<String>> lists) {
        if (addItemsBinding != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            SearchItemsAdapter searchItemsAdapter = new SearchItemsAdapter(this, lists, this);
            addItemsBinding.itemsRv.setLayoutManager(linearLayoutManager);
            addItemsBinding.itemsRv.setAdapter(searchItemsAdapter);
        }
    }

    private void setSelectedItemsAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        purchaseItemsAdapter = new PurchaseItemsAdapter(this, new ArrayList<>(), this, "New Delivery Note");
        binding.itemsRv.setLayoutManager(linearLayoutManager);
        binding.itemsRv.setAdapter(purchaseItemsAdapter);
    }

    private void showAddItemDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.add_purchase_item_dialog, null, false);
        dialog.setContentView(addItemsBinding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        mViewModel.getSearchItemApi("");
        addItemsBinding.search.setOnClickListener(v -> {
            if (addItemsBinding.searchBar.getText().toString() != null) {
                mViewModel.getSearchItemApi(addItemsBinding.searchBar.getText().toString());
            } else Notify.Toast(getString(R.string.enter_name_code));
        });
    }


    private void showQuantityDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.set_quantity_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText quantity = dialog.findViewById(R.id.quantity_edit);
        Button setQuantity = dialog.findViewById(R.id.set);
        quantity.setText("1");
        setQuantity.setOnClickListener(v -> {
            if (quantity.getText().toString() != null && !quantity.getText().toString().isEmpty() && !quantity.getText().toString().equalsIgnoreCase("0")) {
                dialog.dismiss();
                mViewModel.getItemDetailApi(selectedItemCode, quantity.getText().toString(), partyDetail);
            } else Notify.Toast(getString(R.string.enter_quantity));
        });

    }

    @Override
    public void onSelected(String date) {
        binding.customerPurchaseOrderDate.setText(date);
    }

    @Override
    public void onItemClick(List<String> list) {
        selectedItemCode = list.get(0);
        dialog.dismiss();
        showQuantityDialog();
    }

    @Override
    public void onDeleteClick(SearchItemDetail itemDetail) {
        purchaseItemsAdapter.removeItem(itemDetail);
    }
}