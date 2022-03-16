package com.example.erpnext.activities;

import android.app.Dialog;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.erpnext.R;
import com.example.erpnext.adapters.TerritoryTargetsAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.AddNewTerritoryCallBack;
import com.example.erpnext.databinding.ActivityAddSalesPersonBinding;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.models.Target;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddSalesPersonRepo;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.viewmodels.AddSalesPersonViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddSalesPersonActivity extends AppCompatActivity implements View.OnClickListener, AddNewTerritoryCallBack {
    HashMap<String, String> data = new HashMap<String, String>();
    ActivityAddSalesPersonBinding binding;
    AddSalesPersonViewModel mViewModel;
    TerritoryTargetsAdapter salesPersonTargetsAdapter;
    Dialog dialog;
    String selectedItemGroup, selectedFiscalYear, selectedTargetDistribution;
    private AutoCompleteTextView territory_name, parent_territory, territory_manager, item_group, fiscal_year, target_qty, target_amount, target_distribution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_sales_person);
        mViewModel = new ViewModelProvider(this).get(AddSalesPersonViewModel.class);


        setClickListeners();
        setFocusListeners();
        setOnItemSelectListeners();
        setSalesPersonTargetsAdapter(new ArrayList<>());
        setObservers();
    }

    private void setOnItemSelectListeners() {
        binding.parentSalesPerson.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("parent_sales_person", selected);
        });
        binding.employee.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("employee", selected);
            mViewModel.getDepartment(selected);
        });

        binding.salesPersonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("sales_person_name", s.toString());
                }
            }
        });
        binding.commissionRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("commission_rate", s.toString());
                }
            }
        });
        binding.employee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("employee", s.toString());
                } else {
                    data.put("department", "");
                    binding.department.setText("");
                }
            }
        });


    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.add.setOnClickListener(this);
    }

    private void setSalesPersonTargetsAdapter(List<Target> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        salesPersonTargetsAdapter = new TerritoryTargetsAdapter(this, lists, this);
        binding.territoryTargetsRV.setLayoutManager(linearLayoutManager);
        binding.territoryTargetsRV.setAdapter(salesPersonTargetsAdapter);
    }

    private void setFocusListeners() {
        binding.parentSalesPerson.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi(
                        "Sales Person",
                        "",
                        "[[\"Sales Person\",\"is_group\",\"=\",1],[\"Sales Person\",\"name\",\"!=\",\"\"]]",
                        RequestCodes.API.SEARCH_PARENT_SALES_PERSON);
            }
        });
        binding.employee.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi(
                        "Employee",
                        "erpnext.controllers.queries.employee_query",
                        null,
                        RequestCodes.API.SEARCH_EMPLOYEE);
            }
        });
    }

    private void setObservers() {
        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null
                    && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_PARENT_SALES_PERSON) {
                    setSearchAdapter(binding.parentSalesPerson, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_EMPLOYEE) {
                    setSearchAdapter(binding.employee, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.TERRITORY_TARGETS_FISCAL_YEAR) {
                    setSearchAdapter(fiscal_year, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.TERRITORY_TARGETS_ITEM_GROUP) {
                    setSearchAdapter(item_group, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.TERRITORY_TARGETS_TARGET_DISTRIBUTION) {
                    setSearchAdapter(target_distribution, searchLinkResponse);
                }

                AddSalesPersonRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
            }
        });
        mViewModel.getDepartment().observe(this, departmentResponse -> {
            if (departmentResponse != null && departmentResponse.getFetchValues() != null && !departmentResponse.getFetchValues().isEmpty()) {
                binding.department.setText(departmentResponse.getFetchValues().get(0));
                data.put("department", binding.department.getText().toString());
            }
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

    private void showAddTargetDialoog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_territory_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        item_group = dialog.findViewById(R.id.tr_item_group);
        fiscal_year = dialog.findViewById(R.id.fiscal_year);
        target_distribution = dialog.findViewById(R.id.target_distribution);
        EditText target_Qty = dialog.findViewById(R.id.target_qty);
        EditText target_amount = dialog.findViewById(R.id.target_amount);
        Button add = dialog.findViewById(R.id.add);

        item_group.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApiForDialog("Item Group",
                        null,
                        null,
                        RequestCodes.API.TERRITORY_TARGETS_ITEM_GROUP, "Target Detail");
            }
        });
        item_group.setOnItemClickListener((parent, view, position, id) -> {
            selectedItemGroup = (String) parent.getItemAtPosition(position);

        });

        fiscal_year.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApiForDialog("Fiscal Year",
                        null,
                        null,
                        RequestCodes.API.TERRITORY_TARGETS_FISCAL_YEAR, "Target Detail");
            }
        });
        fiscal_year.setOnItemClickListener((parent, view, position, id) -> {
            selectedFiscalYear = (String) parent.getItemAtPosition(position);

        });

        target_distribution.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApiForDialog("Monthly Distribution",
                        null,
                        null,
                        RequestCodes.API.TERRITORY_TARGETS_TARGET_DISTRIBUTION, "Target Detail");
            }
        });
        target_distribution.setOnItemClickListener((parent, view, position, id) -> {
            selectedTargetDistribution = (String) parent.getItemAtPosition(position);
        });

        add.setOnClickListener(v -> {
            String targetQty = target_Qty.getText().toString();
            String targetAmount = target_amount.getText().toString();
            if (selectedFiscalYear != null && !selectedFiscalYear.isEmpty()) {
                if (selectedTargetDistribution != null && !selectedTargetDistribution.isEmpty()) {
                    if (targetQty != null && !targetQty.isEmpty()) {
                        if (targetAmount != null && !targetAmount.isEmpty()) {
                            Target target = new Target();
                            target.setItemGroup(selectedItemGroup);
                            target.setFiscalYear(selectedFiscalYear);
                            target.setTargetQty(Double.parseDouble(targetQty));
                            target.setTargetAmount(Double.parseDouble(targetAmount));
                            target.setDistributionId(selectedTargetDistribution);
                            salesPersonTargetsAdapter.addItem(target);
                            selectedFiscalYear = "";
                            selectedTargetDistribution = "";
                            selectedItemGroup = "";
                            dialog.dismiss();
                        } else Notify.Toast(getString(R.string.add_target_amount));
                    } else Notify.Toast(getString(R.string.add_target_quantity));
                } else Notify.Toast(getString(R.string.add_target_distribution));
            } else Notify.Toast(getString(R.string.select_fiscal_year));
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.add:
                showAddTargetDialoog();
                break;
            case R.id.save:
                if (!fieldError()) {
                    mViewModel.saveDocApi(salesPersonTargetsAdapter, data);
                    setSaveObserver();
                }
                break;
        }
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

    private boolean fieldError() {
        if (binding.salesPersonName.getText().toString().isEmpty()) {
            Notify.Toast(getString(R.string.add_person_name));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        AddSalesPersonRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
        AddSalesPersonRepo.getInstance().departmentRes.setValue(null);
        AddSalesPersonRepo.getInstance().savedDoc.setValue(null);
        super.onDestroy();
    }


    @Override
    public void onDeleteTarget(Target target, int position) {
        salesPersonTargetsAdapter.removeItem(target, position);
    }
}