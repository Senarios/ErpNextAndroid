package com.example.erpnext.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.PurchaseInvoiceAdapter;
import com.example.erpnext.adapters.PurchaseItemsAdapter;
import com.example.erpnext.adapters.SearchItemsAdapter;
import com.example.erpnext.adapters.viewHolders.PurchaseInvoiceViewHolder;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.PurchaseInvoiceCallback;
import com.example.erpnext.callbacks.PurchaseItemCallback;
import com.example.erpnext.callbacks.SearchItesmCallback;
import com.example.erpnext.databinding.ActivityAddNewPurchaseReceiptBinding;
import com.example.erpnext.databinding.AddPurchaseItemDialogBinding;
import com.example.erpnext.models.PurchaseItem;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddPurchaseReceiptRepo;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.AddNewPurchaseReceiptViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AddNewPurchaseReceiptActivity extends AppCompatActivity implements View.OnClickListener, SearchItesmCallback, DateTime.datePickerCallback, PurchaseItemCallback, PurchaseInvoiceCallback {
    public static HashMap<String, String> data = new HashMap<String, String>();
    public static ActivityAddNewPurchaseReceiptBinding binding;
    public static float netWeight = 0;
    public static float totalQuantity = 0;
    public static float totalUSD = 0;
    public static float baseGrandTotal = 0;
    public static float grandTotal = 0;
    float base_discount_amount = 0;
    float additional_discount_percentage = 0;
    AddPurchaseItemDialogBinding addItemsBinding;
    AddNewPurchaseReceiptViewModel mViewModel;
    Dialog dialog;
    PurchaseItemsAdapter purchaseItemsAdapter;
    PurchaseInvoiceAdapter purchaseInvoiceAdapter;
    String selectedItemCode, supplier;

    public static void setTotal() {
        binding.total.setText("$ " + Utils.round(totalUSD, 2));
        binding.totalNetWeight.setText("" + Utils.round(netWeight, 2));
        data.put("total_net_weight", String.valueOf(Utils.round(netWeight, 2)));
        binding.totalQuantityEdit.setText("" + Utils.round(totalQuantity, 2));
        binding.grandTotal.setText("" + Utils.round(baseGrandTotal, 4));
        binding.roundedTotal.setText("" + Utils.round(baseGrandTotal, 2));
        float roundingAmount = Utils.round(baseGrandTotal, 4) - Utils.round(baseGrandTotal, 2);
        binding.roundingAdjustment.setText(String.valueOf(Utils.round(roundingAmount, 4)));
    }

    private void setGrandTotal() {
        grandTotal = baseGrandTotal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_purchase_receipt);
        mViewModel = new ViewModelProvider(this).get(AddNewPurchaseReceiptViewModel.class);

        setClickListeners();
        setFocusListeners();
        setOnItemSelectListeners();
        setObservers();
        setSelectedItemsAdapter();

    }

    private void setClickListeners() {
        binding.opencurrencyAndPrice.setOnClickListener(this);
        binding.openDiscountLayout.setOnClickListener(this);
        binding.openTransporterLayout.setOnClickListener(this);
        binding.addressContactLayout.setOnClickListener(this);
        binding.add.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.addInvoice.setOnClickListener(this);
        binding.date.setText(DateTime.getCurrentDate());
        binding.postingTime.setText(DateTime.getCurrentServerTimeOnly());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.openDiscount_layout:
                if (binding.discountInlayout.getVisibility() == View.VISIBLE) {
                    binding.discountInlayout.setVisibility(View.GONE);
                    binding.discountArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_down_24));
                } else {
                    binding.discountInlayout.setVisibility(View.VISIBLE);
                    binding.discountArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_up_24));
                }
                break;
            case R.id.openTransporter_layout:
                if (binding.transporterInlayout.getVisibility() == View.VISIBLE) {
                    binding.transporterInlayout.setVisibility(View.GONE);
                    binding.transporterArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_down_24));
                } else {
                    binding.transporterInlayout.setVisibility(View.VISIBLE);
                    binding.transporterArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_up_24));
                }
                break;
            case R.id.opencurrencyAndPrice:
                if (binding.currencyPriceLayout.getVisibility() == View.VISIBLE) {
                    binding.currencyPriceLayout.setVisibility(View.GONE);
                    binding.currencyPriceArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_down_24));
                } else {
                    binding.currencyPriceArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_up_24));
                    binding.currencyPriceLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.addressContactLayout:
                if (data.get("supplier") != null && !data.get("supplier").isEmpty()) {
                    if (binding.addressContactInLayout.getVisibility() == View.VISIBLE) {
                        binding.addressContactInLayout.setVisibility(View.GONE);
                        binding.addressContactArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_down_24));
                    } else {
                        binding.addressContactArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_up_24));
                        binding.addressContactInLayout.setVisibility(View.VISIBLE);
                    }
                } else Notify.Toast("Please select supplier  first");
                break;
            case R.id.add:
                if (supplier != null && !supplier.isEmpty()) {
                    setItemsObservers();
                    showAddItemDialog();
                } else Notify.Toast("Please select supplier to continue");
                break;
            case R.id.addInvoice:
                if (supplier != null && !supplier.isEmpty()) {
                    showGetItemDialog();
                } else Notify.Toast("Please select supplier to continue");
                break;
            case R.id.save:
                if (!fieldError()) {
                    List<SearchItemDetail> items = purchaseItemsAdapter.getAllItems();
                    List<PurchaseItem> purchaseItems = new ArrayList<>();
                    for (SearchItemDetail itemDetail : items) {
                        PurchaseItem purchaseItem = new PurchaseItem();
                        purchaseItem.setDocstatus(0);
                        purchaseItem.setDoctype("Purchase Receipt Item");
                        purchaseItem.setName("new-purchase-receipt-item");
                        purchaseItem.setIslocal(1);
                        purchaseItem.setUnsaved(1);
                        purchaseItem.setOwner(AppSession.get("email"));
                        purchaseItem.setStockUom(itemDetail.getUom());
                        purchaseItem.setRetainSample(0);
                        purchaseItem.setParent("new-purchase-receipt");
                        purchaseItem.setParentfield("Items");
                        purchaseItem.setParenttype("Purchase Receipt");
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
                    JSONObject jsonObject1 = new JSONObject(data);
                    JSONObject jsonObject;
                    String jsonString = "{\"docstatus\":0,\"doctype\":\"Purchase Receipt\",\"name\":\"new-purchase-receipt-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"naming_series\":\"MAT-PRE-.YYYY.-\",\"company\":\"Izat Afghan Limited\",\"posting_date\":\"" + DateTime.getCurrentDate() + "\",\"set_posting_time\":\"" + DateTime.getCurrentServerTimeOnly() + "\",\"apply_putaway_rule\":0,\"is_return\":0,\"currency\":\"USD\",\"buying_price_list\":\"Standard Buying\",\"price_list_currency\":\"USD\",\"ignore_pricing_rule\":0,\"is_subcontracted\":\"No\",\"apply_discount_on\":\"Grand Total\",\"disable_rounded_total\":0,\"status\":\"Draft\",\"is_internal_supplier\":0,\"represents_company\":null,\"letter_head\":\"Izat Afghan\",\"group_same_items\":0,\"posting_time\":\"14:09:29\",\"conversion_rate\":1,\"plc_conversion_rate\":1,\"base_discount_amount\":0,\"base_net_total\":0,\"net_total\":0,\"base_total\":0,\"total\":0,\"total_qty\":1,\"rounding_adjustment\":0,\"grand_total\":0,\"taxes_and_charges_deducted\":0,\"taxes_and_charges_added\":0,\"base_grand_total\":0,\"base_taxes_and_charges_added\":0,\"base_taxes_and_charges_deducted\":0,\"total_taxes_and_charges\":0,\"base_total_taxes_and_charges\":0,\"base_rounding_adjustment\":0,\"rounded_total\":0,\"base_rounded_total\":0,\"in_words\":\"\",\"base_in_words\":\"\",\"total_net_weight\":0,\"additional_discount_percentage\":0,\"discount_amount\":0,\"discount_percentage\":null,\"price_list_rate\":0,\"rate_with_margin\":0,\"base_rate_with_margin\":0,\"rate\":0,\"supplier_name\":\"" + data.get("supplier") + "\",\"supplier\":\"" + data.get("supplier") + "\",\"supplier_address\":null,\"address_display\":null,\"contact_person\":null,\"contact_display\":null,\"contact_email\":null,\"contact_mobile\":null,\"language\":\"en\",\"tax_category\":\"\",\"taxes_and_charges\":null,\"set_warehouse\":\"Testing Ravail Warehouse - IAL\"}";

                    try {
                        jsonObject = new JSONObject(jsonString);
                        jsonObject.put("set_warehouse", data.get("set_warehouse"));
                        jsonObject.put("grand_total", Utils.round(baseGrandTotal, 2));
                        jsonObject.put("net_total", Utils.round(baseGrandTotal, 2));
                        jsonObject.put("total", Utils.round(grandTotal, 2));
                        jsonObject.put("base_total", Utils.round(grandTotal, 2));


                        JSONObject json = new JSONObject();
                        Iterator i1 = jsonObject1.keys();
                        Iterator i2 = jsonObject.keys();
                        String tmp_key;
                        while (i1.hasNext()) {
                            tmp_key = (String) i1.next();
                            json.put(tmp_key, jsonObject1.get(tmp_key));
                        }
                        while (i2.hasNext()) {
                            tmp_key = (String) i2.next();
                            json.put(tmp_key, jsonObject.get(tmp_key));
                        }
                        json.put("base_discount_amount", Utils.round(base_discount_amount, 2));
                        json.put("discount_amount", Utils.round(base_discount_amount, 2));
                        json.put("additional_discount_percentage", Utils.round(additional_discount_percentage, 2));

                        Gson gson = new Gson();
                        JSONArray jsonArray = new JSONArray(gson.toJson(purchaseItems));
                        json.putOpt("items", jsonArray);
                        mViewModel.saveDocApi(json);
                        setSaveObserver();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    private void setObservers() {

        mViewModel.getItemDetail().observe(this, itemDetail -> {
            purchaseItemsAdapter.addItem(itemDetail);
        });

        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.SUPPLIER_LINK_SEARCH) {
                    setSearchAdapter(binding.supplier, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SUPPLIER_ADDRESS_LINK_SEARCH) {
                    setSearchAdapter(binding.supplierAddress, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SUPPLIER_DELEIVERY_LINK_SEARCH) {
                    setSearchAdapter(binding.supplierDeleivery, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SUPPLIER_BILLING_ADDRESS_LINK_SEARCH) {
                    setSearchAdapter(binding.billingAddress, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SUPPLIER_CONTACT_PERSON_LINK_SEARCH) {
                    setSearchAdapter(binding.contactPerson, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.ACCEPTED_WAREHOUSE_SEARCH) {
                    setSearchAdapter(binding.acceptedWarehouse, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.REJECTED_WAREHOUSE_LINK_SEARCH) {
                    setSearchAdapter(binding.rejectedWarehouse, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.CURRENCY_LINK_SEARCH) {
                    setSearchAdapter(binding.currency, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.PRICE_LIST_LINK_SEARCH) {
                    setSearchAdapter(binding.priceList, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SUPPLIER_SHIPPING_ADDRESS_LINK_SEARCH) {
                    setSearchAdapter(binding.shippingAddress, searchLinkResponse);
                }
            }
        });
    }

    private void setItemsObservers() {
        mViewModel.getSearchedItems().observe(this, items -> {
            setSearchedItemsAdapter(items);
        });
    }

    private void setSaveObserver() {
        mViewModel.docSaved().observe(this, doc -> {
            Intent intent = new Intent();
            intent.putExtra("saved", true);
            setResult(RESULT_OK, intent);
            Notify.Toast("Successfully created");
            finish();
        });
    }

    private void setSearchAdapter(AutoCompleteTextView textView, SearchLinkResponse searchItemResponse) {
        List<String> list = new ArrayList<>();
        for (SearchResult searchResult : searchItemResponse.getResults()) {
            list.add(searchResult.getValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        textView.setAdapter(adapter);
        textView.showDropDown();
    }

    private void setSearchedItemsAdapter(List<List<String>> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddNewPurchaseReceiptActivity.this);
        SearchItemsAdapter searchItemsAdapter = new SearchItemsAdapter(AddNewPurchaseReceiptActivity.this, lists, this);
        addItemsBinding.itemsRv.setLayoutManager(linearLayoutManager);
        addItemsBinding.itemsRv.setAdapter(searchItemsAdapter);
    }

    private void setPurchaseInvoiceAdapter(List<List<String>> lists, RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddNewPurchaseReceiptActivity.this);
        purchaseInvoiceAdapter = new PurchaseInvoiceAdapter(AddNewPurchaseReceiptActivity.this, lists, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(purchaseInvoiceAdapter);
    }

    private void setSelectedItemsAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddNewPurchaseReceiptActivity.this);
        purchaseItemsAdapter = new PurchaseItemsAdapter(AddNewPurchaseReceiptActivity.this, new ArrayList<>(), this, "New Purchase Receipt");
        binding.itemsRv.setLayoutManager(linearLayoutManager);
        binding.itemsRv.setAdapter(purchaseItemsAdapter);
    }

    @Override
    public void onDeleteClick(SearchItemDetail itemDetail) {
        purchaseItemsAdapter.removeItem(itemDetail);
    }


    @Override
    public void onItemClick(List<String> list) {
        selectedItemCode = list.get(0);
        dialog.dismiss();
        showQuantityDialog();
    }

    private void setFocusListeners() {
        binding.supplier.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                mViewModel.getSearchLinkApi("Supplier", "erpnext.controllers.queries.supplier_query", null, RequestCodes.API.SUPPLIER_LINK_SEARCH);
        });
        binding.supplierAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi("Address", "frappe.contacts.doctype.address.address.address_query", "{\"link_doctype\":\"Supplier\",\"link_name\":\"" + supplier + "\"}", RequestCodes.API.SUPPLIER_ADDRESS_LINK_SEARCH);
            }
        });
        binding.contactPerson.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                mViewModel.getSearchLinkApi("Supplier", "frappe.contacts.doctype.address.address.address_query", "{\"link_doctype\":\"Supplier\",\"link_name\":\"" + supplier + "\"}", RequestCodes.API.SUPPLIER_CONTACT_PERSON_LINK_SEARCH);

        });
        binding.shippingAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                mViewModel.getSearchLinkApi("Address",
                        "frappe.contacts.doctype.address.address.address_query",
                        "{\"is_your_company_address\":1,\"link_doctype\":\"Company\",\"link_name\":\"Izat Afghan Limited\"}",
                        RequestCodes.API.SUPPLIER_SHIPPING_ADDRESS_LINK_SEARCH);

        });
        binding.billingAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                mViewModel.getSearchLinkApi("Address",
                        "frappe.contacts.doctype.address.address.address_query",
                        "{\"is_your_company_address\":1,\"link_doctype\":\"Company\",\"link_name\":\"Izat Afghan Limited\"}",
                        RequestCodes.API.SUPPLIER_BILLING_ADDRESS_LINK_SEARCH);
        });
        binding.acceptedWarehouse.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                mViewModel.getSearchLinkApi("Warehouse",
                        null,
                        "[[\"Warehouse\",\"company\",\"in\",[\"\",\"Izat Afghan Limited\"]],[\"Warehouse\",\"is_group\",\"=\",0]]",
                        RequestCodes.API.ACCEPTED_WAREHOUSE_SEARCH);
        });
        binding.rejectedWarehouse.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                mViewModel.getSearchLinkApi("Warehouse",
                        null,
                        "[[\"Warehouse\",\"company\",\"in\",[\"\",\"Izat Afghan Limited\"]],[\"Warehouse\",\"is_group\",\"=\",0]]",
                        RequestCodes.API.REJECTED_WAREHOUSE_LINK_SEARCH);
        });
        binding.currency.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                mViewModel.getSearchLinkApi("Currency",
                        null,
                        null,
                        RequestCodes.API.CURRENCY_LINK_SEARCH);
        });
        binding.priceList.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                mViewModel.getSearchLinkApi("Price List",
                        null,
                        "{\"buying\":1}",
                        RequestCodes.API.PRICE_LIST_LINK_SEARCH);
        });
        binding.applyDiscountOn.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                SearchLinkResponse searchLinkResponse = new SearchLinkResponse();
                List<SearchResult> searchResults = new ArrayList<>();
                SearchResult searchResult = new SearchResult();
                searchResult.setValue("Grand Total");
                SearchResult searchResult1 = new SearchResult();
                searchResult1.setValue("Net Total");
                searchResults.add(searchResult);
                searchResults.add(searchResult1);
                searchLinkResponse.setResults(searchResults);
                setSearchAdapter(binding.applyDiscountOn, searchLinkResponse);
            }
        });
        binding.vehicleDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                DateTime.showDatePicker(this, this);
            }
        });

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
        grandTotal = total;
        binding.discountAmount.setText("");
        setTotal();
    }

    private void setOnItemSelectListeners() {
        binding.supplier.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("supplier", selected);
            supplier = selected;
        });

        binding.supplierAddress.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("supplier_address", selected);
        });
        binding.contactPerson.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("contact_person", selected);
        });
        binding.billingAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                data.put("billing_address", selected);
            }
        });
        binding.shippingAddress.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("shipping_address", selected);
        });
        binding.currency.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("currency", selected);
        });
        binding.priceList.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("buying_price_list", selected);
        });
        binding.acceptedWarehouse.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("set_warehouse", selected);
        });
        binding.rejectedWarehouse.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("rejected_warehouse", selected);
        });

        binding.applyDiscountOn.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("apply_discount_on", selected);
        });
        binding.ignorePricingRule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    data.put("ignore_pricing_rule", "1");
                } else data.put("ignore_pricing_rule", "0");
            }
        });
        binding.supplierDeleivery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.put("supplier_delivery_note", s.toString());
            }
        });
        binding.transpoerterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.put("transporter_name", s.toString());
            }
        });
        binding.vehicleNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.put("lr_no", s.toString());
            }
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
                        base_discount_amount = Utils.round(discountedAmount, 2);
                        additional_discount_percentage = percentfloat;
                        binding.discountAmount.setText(String.valueOf(Utils.round(discountedAmount, 3)));
                        binding.grandTotal.setText(String.valueOf(afterDiscount));
                        baseGrandTotal = afterDiscount;
                        setTotal();
                    }
                } else {
                    additional_discount_percentage = 0;
                    base_discount_amount = 0;
                    getTotal();
                }
            }
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
                mViewModel.getItemDetailApi(selectedItemCode, supplier, quantity.getText().toString());
            } else Notify.Toast("Please enter quantity");
        });

    }

    private void showGetItemDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.get_purchase_invoice_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        AutoCompleteTextView searchEdit = dialog.findViewById(R.id.search_bar);
        ImageView search = dialog.findViewById(R.id.search);
        TextView supplier = dialog.findViewById(R.id.supplier);
        supplier.setText("Supplier: " + this.supplier);
        RecyclerView invoiceRv = dialog.findViewById(R.id.invoice_rv);
        mViewModel.getPurchaseInvoiceApi("", this.supplier);
        search.setOnClickListener(v -> {
            String searchText = searchEdit.getText().toString();
            if (searchText != null && !searchText.isEmpty()) {
                mViewModel.getPurchaseInvoiceApi(searchText, this.supplier);
            } else Notify.Toast("Please type purchase invoice name");
        });
        mViewModel.getInvoice().observe(this, invoices -> {
            if (invoices != null && invoices.getValues() != null && !invoices.getValues().isEmpty()) {
                setPurchaseInvoiceAdapter(invoices.getValues(), invoiceRv);
            } else setPurchaseInvoiceAdapter(new ArrayList<>(), invoiceRv);
        });
        mViewModel.getInvoiceDetail().observe(this, invoice -> {
            if (invoice != null && invoice.getPurchaseInvoice() != null && invoice.getPurchaseInvoice().getItems() != null) {
                purchaseItemsAdapter.addItem(invoice.getPurchaseInvoice().getItems());
                dialog.dismiss();
                Notify.Toast("Items Added");
                AddPurchaseReceiptRepo.getInstance().invoiceDetail.setValue(null);
            }
        });
    }

    @Override
    public void onPurchaseInvoiceClick(List<String> item, PurchaseInvoiceViewHolder viewHolder, int position) {
        mViewModel.getInvoiceDetail(item.get(1));
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
        mViewModel.getSearchItemApi("", supplier);
        addItemsBinding.search.setOnClickListener(v -> {
            if (addItemsBinding.searchBar.getText().toString() != null) {
                mViewModel.getSearchItemApi(addItemsBinding.searchBar.getText().toString(), "ABC");
            } else Notify.Toast("Please enter name or code");
        });
    }

    private boolean fieldError() {
        if (!data.containsKey("supplier") || data.get("supplier") == null || data.get("supplier").isEmpty()) {
            Notify.Toast("Please select supplier to continue");
            return true;
        } else if (!data.containsKey("set_warehouse") || data.get("set_warehouse") == null || data.get("set_warehouse").isEmpty()) {
            Notify.Toast("Please select accepted warehouse to continue");
            return true;
        } else if (purchaseItemsAdapter.getAllItems() == null || purchaseItemsAdapter.getAllItems().isEmpty()) {
            Notify.Toast("Please add items to continue");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSelected(String date) {
        data.put("lr_date", date.toString());
        binding.vehicleDate.setText(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AddPurchaseReceiptRepo.getInstance().searchedItems.setValue(null);
        AddPurchaseReceiptRepo.getInstance().itemDetails.setValue(null);
        AddPurchaseReceiptRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
    }


}