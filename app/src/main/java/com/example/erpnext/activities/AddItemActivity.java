package com.example.erpnext.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.ActivityAddItemBinding;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddItemRepo;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.viewmodels.AddItemViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {

    HashMap<String, String> data = new HashMap<>();
    ActivityAddItemBinding binding;
    AddItemViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_item);
        mViewModel = new ViewModelProvider(this).get(AddItemViewModel.class);

        setClickListeners();
        setFocusListeners();
        setOnItemSelectListeners();
        setObservers();
    }

    private void setClickListeners() {

        binding.back.setOnClickListener(this);
        binding.save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.save:
                if (!fieldError()) {
                    try {
                        JSONObject jsonObject = new JSONObject("{\"docstatus\":0,\"doctype\":\"Item\",\"name\":\"new-item-4\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"naming_series\":\"STO-ITEM-.YYYY.-\",\"is_item_from_hub\":0,\"stock_uom\":\"Box\",\"disabled\":0,\"allow_alternative_item\":0,\"is_stock_item\":1,\"include_item_in_manufacturing\":1,\"is_fixed_asset\":0,\"auto_create_assets\":0,\"end_of_life\":\"2099-12-31\",\"default_material_request_type\":\"Purchase\",\"valuation_method\":\"\",\"has_batch_no\":0,\"create_new_batch\":0,\"has_expiry_date\":0,\"retain_sample\":0,\"has_serial_no\":0,\"has_variants\":0,\"variant_based_on\":\"Item Attribute\",\"is_purchase_item\":1,\"min_order_qty\":0,\"is_customer_provided_item\":0,\"delivered_by_supplier\":0,\"country_of_origin\":\"Afghanistan\",\"is_sales_item\":1,\"enable_deferred_revenue\":0,\"enable_deferred_expense\":0,\"inspection_required_before_purchase\":0,\"inspection_required_before_delivery\":0,\"is_sub_contracted_item\":0,\"show_in_website\":0,\"show_variant_in_website\":0,\"publish_in_hub\":0,\"synced_with_hub\":0,\"__run_link_triggers\":1,\"item_code\":\"fhwvd\",\"item_name\":\"csdc\",\"item_group\":\"Raw Material\",\"opening_stock\":0,\"standard_rate\":0,\"create_variant\":0}");
                        jsonObject.put("item_code", data.get("item_code"));
                        jsonObject.put("item_name", data.get("item_name"));
                        jsonObject.put("item_group", data.get("item_group"));
                        jsonObject.put("stock_uom", data.get("stock_uom"));
                        jsonObject.put("opening_stock", data.get("opening_stock"));
                        jsonObject.put("standard_rate", data.get("standard_rate"));
                        setSaveObserver();
                        mViewModel.saveDocApi(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void setObservers() {
        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.ITEM_GROUP) {
                    setSearchAdapter(binding.itemGroup, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.DEFAULT_UNIT_OF_MEASURE) {
                    setSearchAdapter(binding.defaultUnitOfMeasure, searchLinkResponse);
                }
            }
        });
    }


    private void setSaveObserver() {
        mViewModel.docSaved().observe(this, doc -> {
            Intent intent = new Intent();
            intent.putExtra("saved", true);
            setResult(RESULT_OK, intent);
            Notify.Toast(getString(R.string.successfully_created));
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
        if (textView == binding.defaultUnitOfMeasure) {
            list.add("Carton");
        }
        textView.setAdapter(adapter);
        textView.showDropDown();
    }

    private void setFocusListeners() {
        binding.itemGroup.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi("", "Item Group", null, null, RequestCodes.API.ITEM_GROUP);
            }
        });
        binding.defaultUnitOfMeasure.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi("", "UOM", null, null, RequestCodes.API.DEFAULT_UNIT_OF_MEASURE);
            }
        });
    }

    private void setOnItemSelectListeners() {
        binding.itemGroup.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("item_group", selected);
        });
        binding.defaultUnitOfMeasure.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("stock_uom", selected);
        });


        binding.itemCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty() && !s.toString().equalsIgnoreCase("0")) {
                    data.put("item_code", s.toString());
                }
            }
        });
        binding.itemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty() && !s.toString().equalsIgnoreCase("0")) {
                    data.put("item_name", s.toString());
                }
            }
        });
        binding.openingStock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("opening_stock", s.toString());
                }
            }
        });
        binding.standardSellimgRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("standard_rate", s.toString());
                }
            }
        });
    }

    private boolean fieldError() {
        if (!data.containsKey("item_code") || data.get("item_code") == null || data.get("item_code").isEmpty()) {
            Notify.Toast(getString(R.string.select_item_code));
            return true;
        } else if (!data.containsKey("item_group") || data.get("item_group") == null || data.get("item_group").isEmpty()) {
            Notify.Toast(getString(R.string.please_select_item_group));
            return true;
        } else if (!data.containsKey("stock_uom") || data.get("stock_uom") == null || data.get("stock_uom").isEmpty()) {
            Notify.Toast(getString(R.string.please_add_stock_uom));
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AddItemRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
    }
}