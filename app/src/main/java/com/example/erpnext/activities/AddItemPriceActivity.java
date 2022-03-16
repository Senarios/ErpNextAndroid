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
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.ActivityAddItemPriceBinding;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddItemPriceRepo;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.viewmodels.AddItemPriceViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddItemPriceActivity extends AppCompatActivity implements View.OnClickListener {

    HashMap<String, String> data = new HashMap<>();
    ActivityAddItemPriceBinding binding;
    AddItemPriceViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_item_price);
        mViewModel = new ViewModelProvider(this).get(AddItemPriceViewModel.class);

        setClickListeners();
        setFocusListeners();
        setOnItemSelectListeners();
        setObservers();

    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.search.setOnClickListener(this);

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
                        JSONObject jsonObject = new JSONObject("{\"docstatus\":0,\"doctype\":\"Item Price\",\"name\":\"new-item-price-4\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"rvlirshad@gmail.com\",\"packing_unit\":0,\"buying\":0,\"selling\":0,\"currency\":\"USD\",\"valid_from\":\"2021-09-24\",\"lead_time_days\":0,\"__run_link_triggers\":1,\"item_code\":\"09-AN\",\"price_list\":\"Standard Buying\",\"price_list_rate\":20}");
                        jsonObject.put("item_code", data.get("item_code"));
                        jsonObject.put("price_list", data.get("price_list"));
                        jsonObject.put("price_list_rate", data.get("price_list_rate"));
                        setSaveObserver();
                        mViewModel.saveDocApi(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.search:
                String itemCode = binding.itemCode.getText().toString();
                if (itemCode != null && !itemCode.isEmpty()) {
                    mViewModel.getSearchLinkApi(itemCode, "Item", null, null, RequestCodes.API.ITEM_CODE_SEARCH);
                } else Notify.Toast(getString(R.string.add_item_code));
                break;
        }
    }

    private void setObservers() {
        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.ITEM_CODE_SEARCH) {
                    setSearchAdapter(binding.itemCode, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.PRICE_LIST_LINK_SEARCH) {
                    setSearchAdapter(binding.priceList, searchLinkResponse);
                }
            }
        });
    }


    private void setSaveObserver() {
        mViewModel.docSaved().observe(this, doc -> {
            Intent intent = new Intent();
            intent.putExtra("saved", true);
            setResult(RESULT_OK, intent);
            Notify.Toast(String.valueOf(R.string.successfully_created));
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


    private void setFocusListeners() {
        binding.itemCode.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus)
//                        mViewModel.getSearchLinkApi("Price List", null, null, RequestCodes.API.SUPPLIER_ADDRESS_LINK_SEARCH);
        });
        binding.priceList.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi("", "Price List", null, null, RequestCodes.API.PRICE_LIST_LINK_SEARCH);
            }
        });
    }

    private void setOnItemSelectListeners() {
        binding.itemCode.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("item_code", selected);
        });

        binding.priceList.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("price_list", selected);
        });

        binding.rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty() && !s.toString().equalsIgnoreCase("0")) {
                    data.put("price_list_rate", s.toString());
                }
            }
        });
    }

    private boolean fieldError() {
        if (!data.containsKey("item_code") || data.get("item_code") == null || data.get("item_code").isEmpty()) {
            Notify.Toast(getString(R.string.select_item_code));
            return true;
        } else if (!data.containsKey("price_list") || data.get("price_list") == null || data.get("price_list").isEmpty()) {
            Notify.Toast(getString(R.string.price_list));
            return true;
        } else if (!data.containsKey("price_list_rate") || data.get("price_list_rate") == null || data.get("price_list_rate").isEmpty()) {
            Notify.Toast(getString(R.string.add_rate));
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AddItemPriceRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
    }
}