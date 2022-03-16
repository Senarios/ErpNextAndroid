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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.erpnext.R;
import com.example.erpnext.adapters.TerritoryTargetsAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.AddNewTerritoryCallBack;
import com.example.erpnext.databinding.ActivityAddTerritoryBinding;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.models.Target;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddTerritoryRepo;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.AddTerritoryViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddTerritoryActivity extends AppCompatActivity implements View.OnClickListener, AddNewTerritoryCallBack {

    public static HashMap<String, String> data = new HashMap<String, String>();
    AddTerritoryViewModel mViewModel;
    ActivityAddTerritoryBinding binding;
    ImageView back;
    Button save;
    TextView add_territory_target_btn;
    String selectedItemGroup, selectedFiscalYear, selectedTargetDistribution;
    Dialog dialog;
    TerritoryTargetsAdapter territoryTargetsAdapter;
    private AutoCompleteTextView territory_name, parent_territory, territory_manager, item_group, fiscal_year, target_qty, target_amount, target_distribution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_territory);
        mViewModel = new ViewModelProvider(this).get(AddTerritoryViewModel.class);

        setClickListeners();
        setFocusListeners();
        setOnItemSelectListeners();
        setTerritoryTargetsAdapter(new ArrayList<>());
        setObservers();
    }

    private void setOnItemSelectListeners() {
        binding.parentTerritory.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("parent_territory", selected);
        });
        binding.territoryManager.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("territory_manager", selected);
        });
        binding.territoryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("territory_name", s.toString());
                }
            }
        });
    }

    private void setClickListeners() {
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        add_territory_target_btn = findViewById(R.id.add_territory_target_btn);
        territory_name = findViewById(R.id.territory_name);
        parent_territory = findViewById(R.id.parent_territory);
        territory_manager = findViewById(R.id.territory_manager);
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        add_territory_target_btn.setOnClickListener(this);
    }

    private void setTerritoryTargetsAdapter(List<Target> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        territoryTargetsAdapter = new TerritoryTargetsAdapter(this, lists, this);
        binding.territoryTargetsRV.setLayoutManager(linearLayoutManager);
        binding.territoryTargetsRV.setAdapter(territoryTargetsAdapter);
    }

    private void setFocusListeners() {
        parent_territory.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi(
                        "Territory",
                        null,
                        "[[\"Territory\",\"is_group\",\"=\",1],[\"Territory\",\"name\",\"!=\",null]]",
                        RequestCodes.API.LINK_SEARCH_PARENT_TERRITORY);
            }
        });
        territory_manager.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi(
                        "Sales Person",
                        null,
                        null,
                        RequestCodes.API.LINK_SEARCH_TERRITORY_MANAGER);
            }
        });
    }

    private void setObservers() {
        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null
                    && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.LINK_SEARCH_PARENT_TERRITORY) {
                    setSearchAdapter(binding.parentTerritory, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.LINK_SEARCH_TERRITORY_MANAGER) {
                    setSearchAdapter(binding.territoryManager, searchLinkResponse);
                }
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

    private void showAddTerritoryTargetsDialog() {
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

        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.TERRITORY_TARGETS_ITEM_GROUP) {
                    Utils.setSearchAdapter(this, item_group, searchLinkResponse);
                    AddTerritoryRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
                }
            }
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

        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.TERRITORY_TARGETS_FISCAL_YEAR) {
                    Utils.setSearchAdapter(this, fiscal_year, searchLinkResponse);
                    AddTerritoryRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
                }
            }
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

        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.TERRITORY_TARGETS_TARGET_DISTRIBUTION) {
                    Utils.setSearchAdapter(this, target_distribution, searchLinkResponse);
                    AddTerritoryRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
                }
            }
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
                            territoryTargetsAdapter.addItem(target);
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
            case R.id.add_territory_target_btn:
                showAddTerritoryTargetsDialog();
                break;
            case R.id.save:
                if (!fieldError()) {
                    mViewModel.saveDocApi(territoryTargetsAdapter);
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
        if (!data.containsKey("territory_name") || data.get("territory_name") == null || data.get("territory_name").isEmpty()) {
            Notify.Toast(getString(R.string.select_territory_name));
            return true;
        } else if (territoryTargetsAdapter == null || territoryTargetsAdapter.getAllItems() == null || territoryTargetsAdapter.getAllItems().isEmpty()) {
            Notify.Toast(getString(R.string.please_add_territory_targets));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        AddTerritoryRepo.getInstance().items.setValue(null);
        AddTerritoryRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
        AddTerritoryRepo.getInstance().fetchValue.setValue(null);
        super.onDestroy();
    }


    @Override
    public void onDeleteTarget(Target target, int position) {
        territoryTargetsAdapter.removeItem(target, position);
    }
}