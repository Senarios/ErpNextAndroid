package com.example.erpnext.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.erpnext.R;
import com.example.erpnext.adapters.WarehouseItemsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ReconciliationItemCallback;
import com.example.erpnext.databinding.ActivityAddReconciliationBinding;
import com.example.erpnext.models.ReconciliationItem;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.models.WarehouseItem;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddReconciliationRepo;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.AddReconciliationViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddReconciliationActivity extends AppCompatActivity implements View.OnClickListener, ReconciliationItemCallback {
    ActivityAddReconciliationBinding binding;
    AddReconciliationViewModel mViewModel;
    WarehouseItemsAdapter warehouseItemsAdapter;
    Dialog dialog;
    String selectedWarehouse;
    AutoCompleteTextView warehouseEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_reconciliation);
        mViewModel = new ViewModelProvider(this).get(AddReconciliationViewModel.class);

        setClickListeners();
//        setFocusListeners();
//        setOnItemSelectListeners();
        setObservers();
//        setSelectedItemsAdapter();

    }

    private void setClickListeners() {
        binding.add.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.removeAll.setOnClickListener(this);
        binding.purpose.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                SearchLinkResponse searchLinkResponse = new SearchLinkResponse();
                List<SearchResult> searchResults = new ArrayList<>();
                SearchResult searchResult = new SearchResult();
                searchResult.setValue("Opening Stock");
                SearchResult searchResult1 = new SearchResult();
                searchResult1.setValue("Stock Reconciliation");
                searchResults.add(searchResult);
                searchResults.add(searchResult1);
                searchLinkResponse.setResults(searchResults);
                Utils.setSearchAdapter(this, binding.purpose, searchLinkResponse);
            }
        });
        binding.purpose.setOnItemClickListener((parent, view, position, id) -> {
            String purpose = (String) parent.getItemAtPosition(position);
            mViewModel.getDiffAccount(purpose);
        });

    }

    private void setWarehouseItemsAdapter(List<WarehouseItem> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        warehouseItemsAdapter = new WarehouseItemsAdapter(this, lists, this);
        binding.itemsRv.setLayoutManager(linearLayoutManager);
        binding.itemsRv.setAdapter(warehouseItemsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.add:
                showGetItemDialog();
                break;
            case R.id.removeAll:
                if (warehouseItemsAdapter != null && warehouseItemsAdapter.getAllItems() != null) {
                    warehouseItemsAdapter.removeAll();
                } else Notify.Toast(getString(R.string.no_items));
                break;
            case R.id.save:
                String purpose = binding.purpose.getText().toString();
                if (purpose != null && !purpose.isEmpty()) {
                    if (warehouseItemsAdapter != null && warehouseItemsAdapter.getAllItems() != null && !warehouseItemsAdapter.getAllItems().isEmpty()) {
                        try {
                            JSONObject jsonObject = new JSONObject("{\"docstatus\":0,\"doctype\":\"Stock Reconciliation\",\"name\":\"new-stock-reconciliation-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"naming_series\":\"MAT-RECO-.YYYY.-\",\"company\":\"Izat Afghan Limited\",\"purpose\":\"" + purpose + "\",\"posting_date\":\"" + DateTime.getCurrentDate() + "\",\"set_posting_time\":0,\"items\":[{\"docstatus\":0,\"doctype\":\"Stock Reconciliation Item\",\"name\":\"new-stock-reconciliation-item-2\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"rvlirshad@gmail.com\",\"allow_zero_valuation_rate\":0,\"current_qty\":100,\"parent\":\"new-stock-reconciliation-1\",\"parentfield\":\"items\",\"parenttype\":\"Stock Reconciliation\",\"idx\":1,\"item_code\":\"1221\",\"warehouse\":\"Testing Ravail Warehouse - IAL\",\"qty\":200,\"item_name\":\"test-rav-2\",\"valuation_rate\":2,\"current_valuation_rate\":2,\"current_serial_no\":\"\",\"serial_no\":\"\"}],\"cost_center\":\"Main - IAL\"}");
                            List<WarehouseItem> items = warehouseItemsAdapter.getAllItems();
                            List<ReconciliationItem> reconciliationItems = new ArrayList<>();
                            for (WarehouseItem warehouseItem : items) {
                                ReconciliationItem reconciliationItem = new ReconciliationItem();
                                reconciliationItem.setDocstatus(0);
                                reconciliationItem.setDoctype("Stock Reconciliation Item");
                                reconciliationItem.setName("new-stock-reconciliation-item");
                                reconciliationItem.setIslocal(1);
                                reconciliationItem.setUnsaved(1);
                                reconciliationItem.setAllowZeroValuationRate(0);
                                reconciliationItem.setOwner(AppSession.get("email"));
                                reconciliationItem.setParent("new-stock-reconciliation");
                                reconciliationItem.setParentfield("Items");
                                reconciliationItem.setParenttype("Stock Reconciliation");
                                reconciliationItem.setIdx(1);
                                reconciliationItem.setItemCode(warehouseItem.getItemCode());
                                reconciliationItem.setQty(warehouseItem.getQty().intValue());
                                reconciliationItem.setValuationRate(warehouseItem.getValuationRate());
                                reconciliationItem.setItemName(warehouseItem.getItemName());
                                reconciliationItem.setWarehouse(warehouseItem.getWarehouse());
                                reconciliationItems.add(reconciliationItem);
                            }
                            Gson gson = new Gson();
                            JSONArray jsonArray = new JSONArray(gson.toJson(reconciliationItems));
                            jsonObject.put("posting_time", DateTime.getCurrentServerTimeOnly());
                            jsonObject.putOpt("expense_account", binding.differenceAccount.getText().toString());
                            jsonObject.putOpt("items", jsonArray);
                            mViewModel.saveDocApi(jsonObject);
                            setSaveObserver();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else Notify.Toast(getString(R.string.please_add_items));
                } else Notify.Toast(getString(R.string.select_purpose));
                break;
        }
    }

    private void setObservers() {
        mViewModel.getDiffAccount().observe(this, diffAccount -> {
            if (diffAccount != null) {
                binding.differenceAccount.setText(diffAccount);
            }
        });
    }

    private void setSaveObserver() {
        mViewModel.docSaved().observe(this, doc -> {
            if (doc != null) {
                Intent intent = new Intent();
                intent.putExtra("saved", true);
                setResult(RESULT_OK, intent);
                Notify.Toast(getString(R.string.successfully_created));
                finish();
            }
        });
    }

    private void showGetItemDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_items_from_warehouse_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        warehouseEdit = dialog.findViewById(R.id.warehouse);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button add = dialog.findViewById(R.id.add);
        warehouseEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi("Warehouse", null, "{\"company\":\"Izat Afghan Limited\"}", RequestCodes.API.SEARCH_WAREHOUSE);
            }
        });
        warehouseEdit.setOnItemClickListener((parent, view, position, id) -> selectedWarehouse = (String) parent.getItemAtPosition(position));

        add.setOnClickListener(v -> {
            if (selectedWarehouse != null && !selectedWarehouse.isEmpty()) {
                mViewModel.getWarehouseItems(selectedWarehouse);
            } else Notify.Toast(getString(R.string.select_warehouse));
        });

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_WAREHOUSE) {
                    Utils.setSearchAdapter(this, warehouseEdit, searchLinkResponse);
                    AddReconciliationRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
                }
            }
        });
        mViewModel.getWarehouses().observe(this, warehouses -> {
            if (warehouses != null && !warehouses.isEmpty()) {
                List<WarehouseItem> warehouseItems = new ArrayList<>();
                warehouseItems.addAll(warehouses);
                setWarehouseItemsAdapter(warehouseItems);
                AddReconciliationRepo.getInstance().warehouseItems.setValue(null);
                dialog.dismiss();

            }
        });
    }

    @Override
    public void onDeleteClick(WarehouseItem warehouseItem, int position) {
        warehouseItemsAdapter.removeItem(warehouseItem, position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AddReconciliationRepo.getInstance().savedDoc.setValue(null);
        AddReconciliationRepo.getInstance().diffAccount.setValue(null);
        AddReconciliationRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
    }
}