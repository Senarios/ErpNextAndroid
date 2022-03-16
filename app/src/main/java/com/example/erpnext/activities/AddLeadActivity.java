package com.example.erpnext.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.ActivityAddLeadBinding;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddLeadRepo;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.AddLeadViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddLeadActivity extends AppCompatActivity implements View.OnClickListener, DateTime.datePickerCallback {

    HashMap<String, String> data = new HashMap<>();
    ActivityAddLeadBinding binding;
    String clickeDate;
    AddLeadViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_lead);
        viewModel = new ViewModelProvider(this).get(AddLeadViewModel.class);
        setClickListeners();
        setFocusListeners();
        setOnItemSelectListeners();
        setObservers();

    }


    private void setClickListeners() {
        binding.noteLayout.setOnClickListener(this);
        binding.addressAndContactLayout.setOnClickListener(this);
        binding.contactLayout.setOnClickListener(this);
        binding.moreInformationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.nextContactDateEdit.setOnClickListener(this);
        binding.endsOnEdit.setOnClickListener(this);
        binding.statusEdit.setText("Lead");
        data.put("status", "Lead");
        data.put("lead_owner", AppSession.get("email"));
        binding.leadOwnerEdit.setText(AppSession.get("email"));
        binding.isOrganisationCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.organizationNameLayout.setVisibility(View.VISIBLE);
            } else binding.organizationNameLayout.setVisibility(View.GONE);
        });
        binding.statusEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                SearchLinkResponse searchLinkResponse = new SearchLinkResponse();
                List<SearchResult> searchResults = new ArrayList<>();
                List<String> statusList = new ArrayList<>();
                statusList.add("Lead");
                statusList.add("Open");
                statusList.add("Replied");
                statusList.add("Opportunity");
                statusList.add("Quotation");
                statusList.add("Lost Quotation");
                statusList.add("Interested");
                statusList.add("Converted");
                statusList.add("Do Not Contact");
                for (String staus : statusList) {
                    SearchResult searchResult = new SearchResult();
                    searchResult.setValue(staus);
                    searchResults.add(searchResult);
                }
                searchLinkResponse.setResults(searchResults);
                Utils.setSearchAdapter(this, binding.statusEdit, searchLinkResponse);
            }
        });
        binding.addressTypeEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                SearchLinkResponse searchLinkResponse = new SearchLinkResponse();
                List<SearchResult> searchResults = new ArrayList<>();
                List<String> statusList = new ArrayList<>();
                statusList.add("Billing");
                statusList.add("Shipping");
                statusList.add("Office");
                statusList.add("Personal");
                statusList.add("Plant");
                statusList.add("Postal");
                statusList.add("Shop");
                statusList.add("Subsidiary");
                statusList.add("Warehouse");
                statusList.add("Current");
                statusList.add("Permanent");
                statusList.add("Other");
                for (String staus : statusList) {
                    SearchResult searchResult = new SearchResult();
                    searchResult.setValue(staus);
                    searchResults.add(searchResult);
                }
                searchLinkResponse.setResults(searchResults);
                Utils.setSearchAdapter(this, binding.addressTypeEdit, searchLinkResponse);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.contact_layout:
                if (binding.contactInlayout.getVisibility() == View.VISIBLE) {
                    binding.contactInlayout.setVisibility(View.GONE);
                    binding.contactArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_down_24));
                    ;
                } else {
                    binding.contactInlayout.setVisibility(View.VISIBLE);
                    binding.contactArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_up_24));
                }
                break;
            case R.id.address_andContact_layout:
                if (binding.addressAndContactInlayout.getVisibility() == View.VISIBLE) {
                    binding.addressAndContactInlayout.setVisibility(View.GONE);
                    binding.addressAndContactArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_down_24));
                } else {
                    binding.addressAndContactInlayout.setVisibility(View.VISIBLE);
                    binding.addressAndContactArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_up_24));
                }
                break;
            case R.id.noteLayout:
                if (binding.noteInLayout.getVisibility() == View.VISIBLE) {
                    binding.noteInLayout.setVisibility(View.GONE);
                    binding.noteArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_down_24));
                } else {
                    binding.noteArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_up_24));
                    binding.noteInLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.more_information_layout:
                if (binding.moreInformationInlayout.getVisibility() == View.VISIBLE) {
                    binding.moreInformationInlayout.setVisibility(View.GONE);
                    binding.moreInformationArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_down_24));
                } else {
                    binding.moreInformationArrow.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_arrow_up_24));
                    binding.moreInformationInlayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.next_contact_date_edit:
                DateTime.showDatePicker(this, this);
                clickeDate = "next";
                break;
            case R.id.ends_on_edit:
                DateTime.showDatePicker(this, this);
                clickeDate = "ends";
                break;
            case R.id.save:
                if (binding.isOrganisationCheck.isChecked()) {
                    if (!organizationFieldError()) {
                        viewModel.saveDocApi(data);
                        setSaveObserver();
                    }
                } else {
                    if (!fieldError()) {
                        viewModel.saveDocApi(data);
                        setSaveObserver();
                    }
                }
                break;
        }
    }

    private void setObservers() {
        viewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_SALUATION) {
                    Utils.setSearchAdapter(this, binding.salutationEdit, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_DESIGNATION) {
                    Utils.setSearchAdapter(this, binding.designationEdit, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_GENDER) {
                    Utils.setSearchAdapter(this, binding.genderEdit, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_SOURCH) {
                    Utils.setSearchAdapter(this, binding.sourceEdit, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_CAMPAIGN_NAME) {
                    Utils.setSearchAdapter(this, binding.campaignNameEdit, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.NEXT_CONTACT_BY) {
                    Utils.setSearchAdapter(this, binding.nextContactByEdit, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_COUNTRY) {
                    Utils.setSearchAdapter(this, binding.countryEdit, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.LEAD_OWNER) {
                    Utils.setSearchAdapter(this, binding.leadOwnerEdit, searchLinkResponse);
                }
                AddLeadRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
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
                AddLeadRepo.getInstance().savedDoc.setValue(null);
                finish();
            }
        });
    }

    private void setFocusListeners() {
        binding.salutationEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Salutation", "", null, RequestCodes.API.SEARCH_SALUATION);
            }
        });
        binding.designationEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Designation", "", null, RequestCodes.API.SEARCH_DESIGNATION);
            }
        });
        binding.genderEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Gender", "", null, RequestCodes.API.SEARCH_GENDER);
            }
        });
        binding.sourceEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Lead Source", "", null, RequestCodes.API.SEARCH_SOURCH);
            }
        });
        binding.campaignNameEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Campaign", "", null, RequestCodes.API.SEARCH_CAMPAIGN_NAME);
            }
        });
        binding.nextContactByEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "User", "frappe.core.doctype.user.user.user_query", null, RequestCodes.API.NEXT_CONTACT_BY);
            }
        });
        binding.countryEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Country", "", null, RequestCodes.API.SEARCH_COUNTRY);
            }
        });
        binding.leadOwnerEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "User", "frappe.core.doctype.user.user.user_query", null, RequestCodes.API.LEAD_OWNER);
            }
        });
        binding.industryEdit.setOnFocusChangeListener((v, hasFocus) -> {

        });
        binding.companyEdit.setOnFocusChangeListener((v, hasFocus) -> {

        });
        binding.territoryEdit.setOnFocusChangeListener((v, hasFocus) -> {

        });
        binding.printLanguageEdit.setOnFocusChangeListener((v, hasFocus) -> {

        });
    }

    private void setOnItemSelectListeners() {
        binding.salutationEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("salutation", selected);
        });
        binding.designationEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("designation", selected);
        });
        binding.genderEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("gender", selected);
        });
        binding.sourceEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("source", selected);
        });
        binding.campaignNameEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("campaign_name", selected);
        });
        binding.nextContactByEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("contact_by", selected);
        });
        binding.countryEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("country", selected);
        });
        binding.leadOwnerEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("lead_owner", selected);
        });
        binding.statusEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("status", selected);
        });
        binding.personName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("lead_name", s.toString());
                }
            }
        });
        binding.organizationNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("company_name", s.toString());
                }
            }
        });
        binding.emailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("email_id", s.toString());
                }
            }
        });
        binding.notesEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("notes", s.toString());
                }
            }
        });
        binding.addressTitleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("address_title", s.toString());
                }
            }
        });
        binding.addressLineEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("address_line1", s.toString());
                }
            }
        });
        binding.cityTownEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("city", s.toString());
                }
            }
        });
        binding.countyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("county", s.toString());
                }
            }
        });
        binding.stateEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("state", s.toString());
                }
            }
        });
        binding.postalCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("pincode", s.toString());
                }
            }
        });

    }

    private boolean fieldError() {
        if (!data.containsKey("lead_name") || data.get("lead_name") == null || data.get("lead_name").isEmpty()) {
            Notify.Toast(getString(R.string.add_person_name));
            return true;
        } else if (!data.containsKey("status") || data.get("status") == null || data.get("status").isEmpty()) {
            Notify.Toast(getString(R.string.select_status));
            return true;
        } else {
            return false;
        }
    }

    private boolean organizationFieldError() {
        if (!data.containsKey("company_name") || data.get("company_name") == null || data.get("company_name").isEmpty()) {
            Notify.Toast(getString(R.string.add_organization_name));
            return true;
        } else if (!data.containsKey("status") || data.get("status") == null || data.get("status").isEmpty()) {
            Notify.Toast(getString(R.string.sales_person));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSelected(String date) {
        if (date != null && !date.isEmpty()) {
            if (clickeDate.equalsIgnoreCase("next")) {
                binding.nextContactDateEdit.setText(date + " " + DateTime.getCurrentServerTimeOnly());
                binding.endsOnEdit.setText(DateTime.getNextMonthCurrentDate(date) + " " + DateTime.getCurrentServerTimeOnly());
                data.put("contact_date", date + " " + DateTime.getCurrentServerTimeOnly());
                data.put("ends_on", DateTime.getNextMonthCurrentDate(date) + " " + DateTime.getCurrentServerTimeOnly());
            } else if (clickeDate.equalsIgnoreCase("ends")) {
                binding.endsOnEdit.setText(date + " " + DateTime.getCurrentServerTimeOnly());
                data.put("ends_on", date + " " + DateTime.getCurrentServerTimeOnly());
            }
        }
    }
}