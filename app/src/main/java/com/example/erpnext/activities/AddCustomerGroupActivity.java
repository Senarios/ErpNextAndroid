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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.erpnext.R;
import com.example.erpnext.adapters.CreditLimitsAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.CreditLimitCallback;
import com.example.erpnext.databinding.ActivityAddCustomerGroupBinding;
import com.example.erpnext.models.CreditLimit;
import com.example.erpnext.repositories.AddCustomerGroupRepo;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.AddCustomerGroupViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCustomerGroupActivity extends AppCompatActivity implements View.OnClickListener, CreditLimitCallback {
    ActivityAddCustomerGroupBinding binding;
    AddCustomerGroupViewModel viewModel;
    Dialog dialog;
    HashMap<String, String> data = new HashMap<>();
    private CreditLimitsAdapter creditLimitsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer_group);
        viewModel = new ViewModelProvider(this).get(AddCustomerGroupViewModel.class);

        setClickListeners();
        setCreditLimitsAdapter(new ArrayList<>());
        setFocusListeners();
        setOnItemSelectListeners();
        setObservers();
    }

    private void setClickListeners() {
        binding.addcreditLimit.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.removeAll.setOnClickListener(this);
        binding.isGroup.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                data.put("is_group", "1");
            } else {
                data.put("is_group", "0");
            }
        });
    }

    private void setCreditLimitsAdapter(List<CreditLimit> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        creditLimitsAdapter = new CreditLimitsAdapter(this, lists, this);
        binding.creditLimitRv.setLayoutManager(linearLayoutManager);
        binding.creditLimitRv.setAdapter(creditLimitsAdapter);
    }

    @Override
    public void onDeleteCredit(CreditLimit creditLimit, int position) {
        creditLimitsAdapter.removeItem(creditLimit, position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.addcreditLimit:
                showAddReceiptDialog();
                break;

            case R.id.removeAll:
                if (creditLimitsAdapter != null && creditLimitsAdapter.getAllItems() != null) {
                    creditLimitsAdapter.removeAll();
                } else Notify.Toast(getString(R.string.no_items));
                break;
            case R.id.save:
                if (!fieldErrorExist()) {
                    viewModel.saveDocApi(data, creditLimitsAdapter);
                    setSaveObserver();
                }
                break;
        }
    }

    private void setObservers() {
        viewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.PARENT_CUSTOMER_GROUP) {
                    Utils.setSearchAdapter(this, binding.parentCustomerGroup, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.DEFAULT_PRICE_LIST) {
                    Utils.setSearchAdapter(this, binding.defaultPriceList, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.DEFAULT_PAYMENT_TERMS) {
                    Utils.setSearchAdapter(this, binding.defaultPaymentTerms, searchLinkResponse);
                }
                AddCustomerGroupRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
            }
        });

    }

    private void setFocusListeners() {
        binding.parentCustomerGroup.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("Customer Group", "", "{\"is_group\":1,\"name\":[\"!=\",null]}", "1", RequestCodes.API.PARENT_CUSTOMER_GROUP);
            }
        });
        binding.defaultPaymentTerms.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("Payment Terms Template", "", null, "0", RequestCodes.API.DEFAULT_PAYMENT_TERMS);
            }
        });
        binding.defaultPriceList.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("Price List", "", null, "1", RequestCodes.API.DEFAULT_PRICE_LIST);
            }
        });
    }

    private void setSaveObserver() {
        viewModel.docSaved().observe(this, doc -> {
            if (doc != null) {
                Intent intent = new Intent();
                intent.putExtra("saved", true);
                setResult(RESULT_OK, intent);
                Notify.Toast(getString(R.string.successfully_created));
                AddCustomerGroupRepo.getInstance().savedDoc.setValue(null);
                finish();
            }
        });
    }

    private void setOnItemSelectListeners() {
        binding.parentCustomerGroup.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("parent_customer_group", selected);
        });
        binding.defaultPriceList.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("default_price_list", selected);
        });

        binding.defaultPaymentTerms.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("payment_terms", selected);
        });

        binding.customerGroupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("customer_group_name", s.toString());
                }
            }
        });

    }


    private void showAddReceiptDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_credit_limits_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText creditLimit = dialog.findViewById(R.id.credit_limit);
        CheckBox isBtyPass = dialog.findViewById(R.id.bypass);
        Button add = dialog.findViewById(R.id.add);
        add.setOnClickListener(v -> {
            if (creditLimit.getText().toString() != null && !creditLimit.getText().toString().isEmpty()) {
                creditLimitsAdapter.addItem(new CreditLimit("Izat Afghan Limited", creditLimit.getText().toString(), isBtyPass.isChecked()));
                dialog.dismiss();
            } else Notify.Toast(getString(R.string.please_add_credit_limit));
        });


    }

    private boolean fieldErrorExist() {
        if (binding.customerGroupName.getText().toString() == null || binding.customerGroupName.getText().toString().isEmpty()) {
            Notify.Toast(getString(R.string.cus_group_name));
            return true;
        } else if (creditLimitsAdapter == null || creditLimitsAdapter.getAllItems() == null || creditLimitsAdapter.getAllItems().isEmpty()) {
            Notify.Toast(getString(R.string.cred_limit));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}