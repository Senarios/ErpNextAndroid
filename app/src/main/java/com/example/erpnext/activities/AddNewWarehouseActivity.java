package com.example.erpnext.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.ActivityAddNewWarehouseBinding;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddWarehouseRepo;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.viewmodels.AddNewWarehouseViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddNewWarehouseActivity extends AppCompatActivity implements View.OnClickListener {

    public static HashMap<String, String> data = new HashMap<String, String>();
    public static ActivityAddNewWarehouseBinding binding;
    ImageView back;
    Button save_btn;
    AddNewWarehouseViewModel mViewModel;
    private AutoCompleteTextView warehouse_name, warehouse_type, parent_warehouse, default_intrans_warehouse, account, company;
    private String doctype, query, filters, doctype_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_warehouse);
        mViewModel = new ViewModelProvider(this).get(AddNewWarehouseViewModel.class);


        setClickListeners();
        setFocusListeners();
        setOnItemSelectListeners();
        setObservers();
    }

    private void setOnItemSelectListeners() {
        binding.warehouseType.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("warehouse_type", selected);
        });
        binding.parentWarehouse.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("parent_warehouse", selected);
        });
        binding.defaultInTransitWarehouse.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("default_in_transit_warehouse", selected);
        });
        binding.account.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("account", selected);
        });
        binding.company.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("company", selected);
        });
        binding.warehouseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("warehouse_name", s.toString());
                }
            }
        });
    }

    private void setClickListeners() {
        back = findViewById(R.id.back);
        save_btn = findViewById(R.id.save);
        warehouse_type = findViewById(R.id.warehouse_type);
        parent_warehouse = findViewById(R.id.parent_warehouse);
        default_intrans_warehouse = findViewById(R.id.default_in_transit_warehouse);
        account = findViewById(R.id.account);
        company = findViewById(R.id.company);
        back.setOnClickListener(this);
        save_btn.setOnClickListener(this);
    }

    private void setFocusListeners() {
//        warehouse_name = findViewById(R.id.warehouse_name);
//        warehouse_type = findViewById(R.id.warehouse_type);
//        parent_warehouse = findViewById(R.id.parent_warehouse);
//        default_intrans_warehouse = findViewById(R.id.default_in_transit_warehouse);
//        account = findViewById(R.id.account);
//        company = findViewById(R.id.company);

        warehouse_type.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi(
                        "Warehouse Type",
                        null,
                        null,
                        RequestCodes.API.WAREHOUSE_TYPE);
            }
        });
        parent_warehouse.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi(
                        "Warehouse",
                        null,
                        "{\"is_group\":1}",
                        RequestCodes.API.PARENT_WAREHOUSE);
            }
        });
        default_intrans_warehouse.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi(
                        "Warehouse",
                        null,
                        "{\"warehouse_type\":\"Transit\",\"is_group\":0,\"company\":\"Izat Afghan Limited\"}",
                        RequestCodes.API.DEFAULT_IN_TRANSIT_WAREHOUSE);
            }
        });
        account.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi(
                        "Account",
                        "",
                        "{\"is_group\":0,\"account_type\":\"Stock\",\"company\":\"Izat Afghan Limited\"}",
                        RequestCodes.API.ACCOUNT_SEARCH);
            }
        });
        company.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi(
                        "Company",
                        null,
                        null,
                        RequestCodes.API.COMPANY);
            }
        });

    }


    private void setObservers() {
        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null
                    && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.WAREHOUSE_TYPE) {
                    setSearchAdapter(binding.warehouseType, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.PARENT_WAREHOUSE) {
                    setSearchAdapter(binding.parentWarehouse, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.DEFAULT_IN_TRANSIT_WAREHOUSE) {
                    setSearchAdapter(binding.defaultInTransitWarehouse, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.ACCOUNT_SEARCH) {
                    setSearchAdapter(binding.account, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.COMPANY) {
                    setSearchAdapter(binding.company, searchLinkResponse);
                } else {
                    Notify.Toast(getString(R.string.not_found));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.save:
                if (!fieldError()) {
                    JSONObject jsonObject = new JSONObject(data);
                    String jsonString = "{\"docstatus\":0,\"doctype\":\"Warehouse\",\"name\":\"new-warehouse-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"is_group\":0,\"company\":\"Izat Afghan Limited\",\"disabled\":0,\"warehouse_name\":\"Group\",\"warehouse_type\":\"Transit\",\"parent_warehouse\":\"All Warehouses - IAL\",\"account\":\"pasta wh - IAL\"}";
                    try {
                        jsonObject = new JSONObject(jsonString);
                        jsonObject.put("warehouse_type", data.get("warehouse_type"));
                        jsonObject.put("warehouse_name", data.get("warehouse_name"));
                        jsonObject.put("parent_warehouse", data.get("parent_warehouse"));
                        jsonObject.put("default_in_transit_warehouse", data.get("default_in_transit_warehouse"));
                        jsonObject.put("account", data.get("account"));
                        jsonObject.put("company", data.get("company"));
                        mViewModel.saveDocApi(jsonObject);
                        setSaveObserver();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
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
        textView.setAdapter(adapter);
        textView.showDropDown();
    }

    private boolean fieldError() {
        if (!data.containsKey("warehouse_name") || data.get("warehouse_name") == null || data.get("warehouse_name").isEmpty()) {
            Notify.Toast(getString(R.string.select_warehouse_name));
            return true;
        } else if (!data.containsKey("company") || data.get("company") == null || data.get("company").isEmpty()) {
            Notify.Toast(getString(R.string.select_company));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AddWarehouseRepo.getInstance().searchedItems.setValue(null);
        AddWarehouseRepo.getInstance().itemDetails.setValue(null);
        AddWarehouseRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
    }
}