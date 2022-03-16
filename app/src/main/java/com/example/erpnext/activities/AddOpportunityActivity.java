package com.example.erpnext.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.erpnext.R;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.ActivityAddOpportunityBinding;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddOpportunityRepo;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.AddOpportunityViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddOpportunityActivity extends AppCompatActivity implements View.OnClickListener, DateTime.datePickerCallback {

    HashMap<String, String> data = new HashMap<>();
    ActivityAddOpportunityBinding binding;
    String clickeDate;
    AddOpportunityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_opportunity);
        viewModel = new ViewModelProvider(this).get(AddOpportunityViewModel.class);
        setClickListeners();
        setFocusListeners();
        setOnItemSelectListeners();
        setObservers();

    }


    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.closingDate.setOnClickListener(this);
        binding.status.setText("Open");
        data.put("status", "Open");

        binding.status.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                SearchLinkResponse searchLinkResponse = new SearchLinkResponse();
                List<SearchResult> searchResults = new ArrayList<>();
                List<String> statusList = new ArrayList<>();
                statusList.add("Open");
                statusList.add("Replied");
                statusList.add("Quotation");
                statusList.add("Lost");
                statusList.add("Converted");
                statusList.add("Closed");
                for (String staus : statusList) {
                    SearchResult searchResult = new SearchResult();
                    searchResult.setValue(staus);
                    searchResults.add(searchResult);
                }
                searchLinkResponse.setResults(searchResults);
                Utils.setSearchAdapter(this, binding.status, searchLinkResponse);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.closing_date:
                DateTime.showDatePicker(this, this);
                break;
            case R.id.save:
                if (!isFieldErrorExist()) {
                    viewModel.saveDocApi(data);
                    setSaveObserver();
                }
                break;
        }
    }

    private void setObservers() {
        viewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_LEAD) {
                    Utils.setSearchAdapter(this, binding.lead, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.LINK_SEARCH_CUSTOMER) {
                    Utils.setSearchAdapter(this, binding.customer, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.OPPORTUNITY_FROM) {
                    Utils.setSearchAdapter(this, binding.opportunityFrom, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.OPPORTUNITY_TYPE) {
                    Utils.setSearchAdapter(this, binding.opportunityType, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_SOURCH) {
                    Utils.setSearchAdapter(this, binding.sourceEdit, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.CONVERTED_BY) {
                    Utils.setSearchAdapter(this, binding.convertedBy, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SALES_STAGE) {
                    Utils.setSearchAdapter(this, binding.salesStage, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.CURRENCY_LINK_SEARCH) {
                    Utils.setSearchAdapter(this, binding.currency, searchLinkResponse);
                }

                AddOpportunityRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
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
                AddOpportunityRepo.getInstance().savedDoc.setValue(null);
                finish();
            }
        });
    }

    private void setFocusListeners() {
        binding.opportunityFrom.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "DocType", "", "{\"name\":[\"in\",[\"Customer\",\"Lead\"]]}", RequestCodes.API.OPPORTUNITY_FROM);
            }
        });
        binding.opportunityType.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Opportunity Type", "", null, RequestCodes.API.OPPORTUNITY_TYPE);
            }
        });
        binding.lead.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Lead", "erpnext.controllers.queries.lead_query", null, RequestCodes.API.SEARCH_LEAD);
            }
        });
        binding.customer.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Customer", "erpnext.controllers.queries.customer_query", null, RequestCodes.API.LINK_SEARCH_CUSTOMER);
            }
        });
        binding.sourceEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Lead Source", "", null, RequestCodes.API.SEARCH_SOURCH);
            }
        });
        binding.salesStage.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Sales Stage", "", null, RequestCodes.API.SALES_STAGE);
            }
        });
        binding.convertedBy.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "User", "", null, RequestCodes.API.CONVERTED_BY);
            }
        });
        binding.currency.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Currency", "", null, RequestCodes.API.CURRENCY_LINK_SEARCH);
            }
        });
    }

    private void setOnItemSelectListeners() {
        binding.opportunityFrom.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("opportunity_from", selected);
            if (selected.equalsIgnoreCase("Lead")) {
                binding.leadLayout.setVisibility(View.VISIBLE);
                binding.customLayout.setVisibility(View.GONE);
            } else {
                binding.customLayout.setVisibility(View.VISIBLE);
                binding.leadLayout.setVisibility(View.GONE);
            }
        });
        binding.lead.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("party_name", selected);
        });
        binding.customer.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("party_name", selected);
        });

        binding.opportunityType.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("opportunity_type", selected);
        });
        binding.sourceEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("source", selected);
        });
        binding.salesStage.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("sales_stage", selected);
        });
        binding.currency.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("currency", selected);
        });
        binding.convertedBy.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("contact_by", selected);
        });
        binding.status.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("status", selected);
        });

        binding.opportunityAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("opportunity_amount", s.toString());
                }
            }
        });
        binding.probability.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("probability", s.toString());
                }
            }
        });

    }


    private boolean isFieldErrorExist() {
        if (!data.containsKey("opportunity_from") || data.get("opportunity_from") == null || data.get("opportunity_from").isEmpty()) {
            Notify.Toast(getString(R.string.select_opportunity));
            return true;
        } else if (!data.containsKey("party_name") || data.get("party_name") == null || data.get("party_name").isEmpty()) {
            Notify.Toast(getString(R.string.add_lead_or_cusname));
            return true;
        } else if (!data.containsKey("status") || data.get("status") == null || data.get("status").isEmpty()) {
            Notify.Toast(getString(R.string.select_status));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        AddOpportunityRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
        AddOpportunityRepo.getInstance().savedDoc.setValue(null);
        super.onDestroy();
    }

    @Override
    public void onSelected(String date) {
        binding.closingDate.setText(date);
        data.put("expected_closing", date);
    }
}