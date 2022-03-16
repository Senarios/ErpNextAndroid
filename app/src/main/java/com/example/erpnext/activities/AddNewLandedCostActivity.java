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
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.erpnext.R;
import com.example.erpnext.adapters.ApplicableChargesAdapter;
import com.example.erpnext.adapters.LandedCostItemsAdapter;
import com.example.erpnext.adapters.LandedCostReceiptAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.AddLandedCostCallback;
import com.example.erpnext.databinding.ActivityAddNewLandedCostBinding;
import com.example.erpnext.models.LandedCostItem;
import com.example.erpnext.models.LandedCostReceipt;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.models.Tax;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddLandedCostRepo;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.AddLandedCostViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddNewLandedCostActivity extends AppCompatActivity implements View.OnClickListener, AddLandedCostCallback {
    public static float totalCharges = 0;
    static ActivityAddNewLandedCostBinding binding;
    AddLandedCostViewModel mViewModel;
    Dialog dialog;
    String docRecType, selectedReceipt, selectedAccount, chargesBaseOn;
    AutoCompleteTextView docReceiptType, docReceipt, expenseAccount;
    LandedCostReceiptAdapter landedCostReceiptAdapter;
    LandedCostItemsAdapter landedCostItemsAdapter;
    ApplicableChargesAdapter applicableChargesAdapter;

    public static void setTotal() {
        binding.totalTaxCharges.setText(String.valueOf(totalCharges));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_landed_cost);
        mViewModel = new ViewModelProvider(this).get(AddLandedCostViewModel.class);

        setClickListeners();
        setLandedCostReceiptAdapter(new ArrayList<>());
        setApplicableChargesAdapter(new ArrayList<>());
        setObservers();
    }

    private void setClickListeners() {
        binding.getItems.setOnClickListener(this);
        binding.addReceipt.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.removeAllItems.setOnClickListener(this);
        binding.removeAllReceipts.setOnClickListener(this);
        binding.addCharges.setOnClickListener(this);
        binding.chargesBasedOn.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                SearchLinkResponse searchLinkResponse = new SearchLinkResponse();
                List<SearchResult> searchResults = new ArrayList<>();
                SearchResult searchResult = new SearchResult();
                searchResult.setValue("Qty");
                SearchResult searchResult1 = new SearchResult();
                searchResult1.setValue("Amount");
                SearchResult searchResult2 = new SearchResult();
                searchResult2.setValue("Distribute Manually");
                searchResults.add(searchResult);
                searchResults.add(searchResult1);
                searchResults.add(searchResult2);
                searchLinkResponse.setResults(searchResults);
                Utils.setSearchAdapter(this, binding.chargesBasedOn, searchLinkResponse);
            }
        });
        binding.chargesBasedOn.setOnItemClickListener((parent, view, position, id) -> {
            chargesBaseOn = (String) parent.getItemAtPosition(position);

        });
    }

    private void setLandedCostReceiptAdapter(List<LandedCostReceipt> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        landedCostReceiptAdapter = new LandedCostReceiptAdapter(this, lists, this);
        binding.receiptsRv.setLayoutManager(linearLayoutManager);
        binding.receiptsRv.setAdapter(landedCostReceiptAdapter);
    }

    private void setLandedCostItemsAdapter(List<LandedCostItem> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        landedCostItemsAdapter = new LandedCostItemsAdapter(this, lists, this);
        binding.itemsRv.setLayoutManager(linearLayoutManager);
        binding.itemsRv.setAdapter(landedCostItemsAdapter);
    }

    private void setApplicableChargesAdapter(List<Tax> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        applicableChargesAdapter = new ApplicableChargesAdapter(this, lists, this);
        binding.changesRv.setLayoutManager(linearLayoutManager);
        binding.changesRv.setAdapter(applicableChargesAdapter);
    }

    @Override
    public void onDeletePurchaseReceiptClick(LandedCostReceipt costReceipt, int position) {
        landedCostReceiptAdapter.removeItem(costReceipt, position);
    }

    @Override
    public void onDeleteItemClick(LandedCostItem landedCostItem, int position) {
        landedCostItemsAdapter.removeItem(landedCostItem, position);
    }

    @Override
    public void onDeleteChargesClick(Tax tax, int position) {
        if (position == 0) {
            totalCharges = 0;
        } else {
            totalCharges = totalCharges - tax.getAmount().floatValue();
        }
        setTotal();
        applicableChargesAdapter.removeItem(tax, position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.addReceipt:
                showAddReceiptDialog();
                break;
            case R.id.addCharges:
                showAddChargesDialog();
                break;
            case R.id.getItems:
                if (landedCostReceiptAdapter != null && landedCostReceiptAdapter.getAllItems() != null && !landedCostReceiptAdapter.getAllItems().isEmpty()) {
                    mViewModel.getItemsApi(landedCostReceiptAdapter);
                }
                break;
            case R.id.removeAllReceipts:
                if (landedCostReceiptAdapter != null && landedCostReceiptAdapter.getAllItems() != null) {
                    landedCostReceiptAdapter.removeAll();
                } else Notify.Toast(getString(R.string.no_items));
                break;
            case R.id.removeAllItems:
                if (landedCostItemsAdapter != null && landedCostItemsAdapter.getAllItems() != null) {
                    landedCostItemsAdapter.removeAll();
                } else Notify.Toast(getString(R.string.no_items));
                break;
            case R.id.save:
                if (!fieldError()) {
                    mViewModel.saveDocApi(landedCostReceiptAdapter, landedCostItemsAdapter, applicableChargesAdapter, totalCharges);
                    setSaveObserver();
                }
                break;
        }
    }

    private void setObservers() {
        mViewModel.getItems().observe(this, items -> {
            if (items != null && !items.isEmpty()) {
                setLandedCostItemsAdapter(items);
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


    private void showAddReceiptDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_receipt_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        docReceiptType = dialog.findViewById(R.id.receipt_doc_Type);
        docReceipt = dialog.findViewById(R.id.receipt_document);
        Button add = dialog.findViewById(R.id.add);

        docReceiptType.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                SearchLinkResponse searchLinkResponse = new SearchLinkResponse();
                List<SearchResult> searchResults = new ArrayList<>();
                SearchResult searchResult = new SearchResult();
                searchResult.setValue("Purchase Invoice");
                SearchResult searchResult1 = new SearchResult();
                searchResult1.setValue("Purchase Receipt");
                searchResults.add(searchResult);
                searchResults.add(searchResult1);
                searchLinkResponse.setResults(searchResults);
                Utils.setSearchAdapter(this, docReceiptType, searchLinkResponse);
            }
        });
        docReceiptType.setOnItemClickListener((parent, view, position, id) -> {
            docRecType = (String) parent.getItemAtPosition(position);

        });

        docReceipt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (docRecType != null && !docRecType.isEmpty()) {
                    mViewModel.getSearchLinkApi(docRecType, "",
                            "[[\"" + docRecType + "\",\"docstatus\",\"=\",\"1\"],[\"" + docRecType + "\",\"company\",\"=\",\"Izat Afghan Limited\"]]",
                            "Landed Cost Purchase Receipt",
                            RequestCodes.API.DOC_RECEIPT);
                } else Notify.Toast(getString(R.string.select_receipt_document));
            }
        });
        docReceipt.setOnItemClickListener((parent, view, position, id) -> selectedReceipt = (String) parent.getItemAtPosition(position));
        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.DOC_RECEIPT) {
                    Utils.setSearchAdapter(this, docReceipt, searchLinkResponse);
                    AddLandedCostRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
                }
            }
        });
        add.setOnClickListener(v -> {
            if (selectedReceipt != null && !selectedReceipt.isEmpty()) {
                mViewModel.getFetchValuesApi(docRecType, selectedReceipt);
            }
        });
        mViewModel.fetchedValues().observe(this, fetchValuesResponse -> {
            if (fetchValuesResponse != null && fetchValuesResponse.getFetchValues() != null && !fetchValuesResponse.getFetchValues().isEmpty()) {
                LandedCostReceipt landedCostReceipt = new LandedCostReceipt(docRecType, selectedReceipt, fetchValuesResponse.getFetchValues().get(0), fetchValuesResponse.getFetchValues().get(2));
                this.landedCostReceiptAdapter.addItem(landedCostReceipt);
                dialog.dismiss();
                selectedReceipt = null;
                docRecType = null;
                AddLandedCostRepo.getInstance().fetchValue.setValue(null);
            }
        });
    }

    private void showAddChargesDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_charges_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        expenseAccount = dialog.findViewById(R.id.account);
        EditText descriptionEdit = dialog.findViewById(R.id.description);
        EditText amountEdit = dialog.findViewById(R.id.amount);
        Button add = dialog.findViewById(R.id.add);


        expenseAccount.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi("Account",
                        "",
                        "{\"account_type\":[\"in\",[\"Tax\",\"Chargeable\",\"Income Account\",\"Expenses Included In Valuation\",\"Expenses Included In Asset Valuation\"]],\"company\":\"Izat Afghan Limited\"}",
                        "Landed Cost Taxes and Charges",
                        RequestCodes.API.EXPENSE_ACCOUNT);
            }
        });
        expenseAccount.setOnItemClickListener((parent, view, position, id) -> {
            selectedAccount = (String) parent.getItemAtPosition(position);

        });

        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.EXPENSE_ACCOUNT) {
                    Utils.setSearchAdapter(this, expenseAccount, searchLinkResponse);
                    AddLandedCostRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
                }
            }
        });
        add.setOnClickListener(v -> {
            String description = descriptionEdit.getText().toString();
            String amount = amountEdit.getText().toString();
            if (selectedAccount != null && !selectedAccount.isEmpty()) {
                if (description != null && !description.isEmpty()) {
                    if (amount != null && !amount.isEmpty()) {
                        Tax tax = new Tax();
                        tax.setAmount(Double.parseDouble(amount));
                        tax.setExpenseAccount(selectedAccount);
                        tax.setBaseAmount(Double.parseDouble(amount));
                        tax.setDescription(description);
                        applicableChargesAdapter.addItem(tax);
                        dialog.dismiss();
                    } else Notify.Toast(getString(R.string.add_amount));
                } else Notify.Toast(getString(R.string.add_description));
            } else Notify.Toast(getString(R.string.select_expence));
        });
    }

    private boolean fieldError() {
        if (landedCostReceiptAdapter == null || landedCostReceiptAdapter.getAllItems() == null || landedCostReceiptAdapter.getAllItems().isEmpty()) {
            Notify.Toast(getString(R.string.add_receipt));
            return true;
        } else if (landedCostItemsAdapter == null || landedCostItemsAdapter.getAllItems() == null || landedCostItemsAdapter.getAllItems().isEmpty()) {
            Notify.Toast(getString(R.string.add_items));
            return true;
        } else if (applicableChargesAdapter == null || applicableChargesAdapter.getAllItems() == null || applicableChargesAdapter.getAllItems().isEmpty()) {
            Notify.Toast(getString(R.string.please_add_charges));
            return true;
        } else if (binding.chargesBasedOn.getText().toString() == null || binding.chargesBasedOn.getText().toString().isEmpty()) {
            Notify.Toast(getString(R.string.select_charges_based));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        AddLandedCostRepo.getInstance().items.setValue(null);
        AddLandedCostRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
        AddLandedCostRepo.getInstance().fetchValue.setValue(null);
        super.onDestroy();
    }


}