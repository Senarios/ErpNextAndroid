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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.erpnext.R;
import com.example.erpnext.adapters.ContactNoAdapter;
import com.example.erpnext.adapters.EmailAdapter;
import com.example.erpnext.adapters.ReferenceAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.ActivityAddContactBinding;
import com.example.erpnext.models.ContactNo;
import com.example.erpnext.models.Email;
import com.example.erpnext.models.Reference;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddContactRepo;
import com.example.erpnext.repositories.AddLeadRepo;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.AddContactViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {
    HashMap<String, String> data = new HashMap<>();
    ActivityAddContactBinding binding;
    String selectedDocType, selectedLinkName, selectedFieldName;
    AddContactViewModel viewModel;
    EmailAdapter emailAdapter;
    ContactNoAdapter contactNoAdapter;
    ReferenceAdapter referenceAdapter;
    AutoCompleteTextView linkDocType, linkName;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_contact);
        viewModel = new ViewModelProvider(this).get(AddContactViewModel.class);
        setClickListeners();
        setFocusListeners();
        setOnItemSelectListeners();
        setEmailsAdapter(new ArrayList<>());
        setContactNoAdapter(new ArrayList<>());
        setReferenceAdapter(new ArrayList<>());
        setObservers();

    }


    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.add.setOnClickListener(this);
        binding.removeAllEmails.setOnClickListener(this);
        binding.addNumber.setOnClickListener(this);
        binding.addRef.setOnClickListener(this);
        binding.removeAllNumbers.setOnClickListener(this);
        binding.removeAllRef.setOnClickListener(this);

        binding.statusEdit.setText("Passive");
        data.put("status", "Passive");

        binding.statusEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                SearchLinkResponse searchLinkResponse = new SearchLinkResponse();
                List<SearchResult> searchResults = new ArrayList<>();
                List<String> statusList = new ArrayList<>();
                statusList.add("Passive");
                statusList.add("Open");
                statusList.add("Replied");
                for (String staus : statusList) {
                    SearchResult searchResult = new SearchResult();
                    searchResult.setValue(staus);
                    searchResults.add(searchResult);
                }
                searchLinkResponse.setResults(searchResults);
                Utils.setSearchAdapter(this, binding.statusEdit, searchLinkResponse);
            }
        });

    }

    private void setEmailsAdapter(List<Email> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        emailAdapter = new EmailAdapter(this, lists, null);
        binding.contactsRv.setLayoutManager(linearLayoutManager);
        binding.contactsRv.setAdapter(emailAdapter);
    }

    private void setContactNoAdapter(List<ContactNo> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactNoAdapter = new ContactNoAdapter(this, lists, null);
        binding.numbersRv.setLayoutManager(linearLayoutManager);
        binding.numbersRv.setAdapter(contactNoAdapter);
    }

    private void setReferenceAdapter(List<Reference> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        referenceAdapter = new ReferenceAdapter(this, lists, null);
        binding.refRv.setLayoutManager(linearLayoutManager);
        binding.refRv.setAdapter(referenceAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.add:
                showAddEmailDialog();
                break;
            case R.id.addNumber:
                showAddNoDialog();
                break;
            case R.id.addRef:
                showAddRefDialog();
                break;
            case R.id.removeAllEmails:
                if (emailAdapter != null && emailAdapter.getAllItems() != null) {
                    emailAdapter.removeAll();
                }
                break;
            case R.id.removeAllNumbers:
                if (contactNoAdapter != null && contactNoAdapter.getAllItems() != null) {
                    contactNoAdapter.removeAll();
                }
                break;
            case R.id.removeAllRef:
                if (referenceAdapter != null && referenceAdapter.getAllItems() != null) {
                    referenceAdapter.removeAll();
                }
                break;
            case R.id.save:
                if (!fieldError()) {
                    viewModel.saveDocApi(emailAdapter, contactNoAdapter, referenceAdapter, data);
                    setSaveObserver();
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
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_EMAIL) {
                    Utils.setSearchAdapter(this, binding.userId, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.LINK_SEARCH_COMPANY_ADDRESS_NAME) {
                    Utils.setSearchAdapter(this, binding.address, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.LINK_DOC_TYPE) {
                    Utils.setSearchAdapter(this, linkDocType, searchLinkResponse);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.LINK_NAME) {
                    Utils.setSearchAdapter(this, linkName, searchLinkResponse);
                }
                AddContactRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
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
        binding.userId.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "User", "", null, RequestCodes.API.SEARCH_EMAIL);
            }
        });
        binding.address.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("", "Address", "", null, RequestCodes.API.LINK_SEARCH_COMPANY_ADDRESS_NAME);
            }
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
        binding.userId.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("user", selected);
        });
        binding.address.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("address", selected);
        });
        binding.statusEdit.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            data.put("status", selected);
        });
        binding.firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("first_name", s.toString());
                }
            }
        });
        binding.middleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("middle_name", s.toString());
                }
            }
        });

        binding.lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().isEmpty()) {
                    data.put("last_name", s.toString());
                }
            }
        });

        binding.companyName.addTextChangedListener(new TextWatcher() {
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


    }

    private boolean fieldError() {
        if (binding.firstName.getText().toString().isEmpty()) {
            Notify.Toast(getString(R.string.add_first_name));
            return true;
        } else {
            return false;
        }
    }

    private void showAddEmailDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_email_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText emailEdit = dialog.findViewById(R.id.email_edit);
        Button add = dialog.findViewById(R.id.add);
        Button cancel = dialog.findViewById(R.id.cancel);
        CheckBox isPrimary = dialog.findViewById(R.id.is_primary);

        add.setOnClickListener(v -> {
            String email = emailEdit.getText().toString();
            if (!email.isEmpty()) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Email email1 = new Email();
                    email1.setEmail(email);
                    email1.setPrimary(isPrimary.isChecked());
                    emailAdapter.addItem(email1);
                    dialog.dismiss();
                } else Notify.Toast(getString(R.string.add_valid_email));
            } else Notify.Toast(getString(R.string.please_add_email));
        });
        cancel.setOnClickListener(v -> {
            dialog.cancel();
        });
    }

    private void showAddNoDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_contact_no_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText contactNo = dialog.findViewById(R.id.contact_no_edit);
        Button add = dialog.findViewById(R.id.add);
        Button cancel = dialog.findViewById(R.id.cancel);
        CheckBox isPrimary = dialog.findViewById(R.id.is_primary);

        add.setOnClickListener(v -> {
            String number = contactNo.getText().toString();
            if (!number.isEmpty()) {
                ContactNo contactNo1 = new ContactNo();
                contactNo1.setNumber(number);
                contactNo1.setPrimary(isPrimary.isChecked());
                contactNoAdapter.addItem(contactNo1);
                dialog.dismiss();
            } else Notify.Toast(getString(R.string.please_add_number));
        });
        cancel.setOnClickListener(v -> {
            dialog.cancel();
        });
    }

    private void showAddRefDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_reference_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        linkDocType = dialog.findViewById(R.id.link_doc_type);
        linkName = dialog.findViewById(R.id.link_name);
        TextView linkTitle = dialog.findViewById(R.id.link_title);
        Button add = dialog.findViewById(R.id.add);
        Button cancel = dialog.findViewById(R.id.cancel);


        linkDocType.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                selectedLinkName = "";
                selectedFieldName = "";
                linkName.setText("");
                linkTitle.setText("");
                viewModel.getSearchLinkApi("",
                        "DocType",
                        "frappe.contacts.address_and_contact.filter_dynamic_link_doctypes",
                        "{\"fieldtype\":\"HTML\",\"fieldname\":\"contact_html\"}",
                        RequestCodes.API.LINK_DOC_TYPE);
            }
        });
        linkDocType.setOnItemClickListener((parent, view, position, id) -> {
            selectedDocType = (String) parent.getItemAtPosition(position);
            if (selectedDocType.equalsIgnoreCase("Shareholder") || selectedDocType.equalsIgnoreCase("Lead")) {
                selectedFieldName = "title";
            } else if (selectedDocType.equalsIgnoreCase("Customer")) {
                selectedFieldName = "customer_name";
            } else if (selectedDocType.equalsIgnoreCase("Supplier")) {
                selectedFieldName = "supplier_name";
            } else if (selectedDocType.equalsIgnoreCase("Manufacturer")) {
                selectedFieldName = "short_name";
            } else if (selectedDocType.equalsIgnoreCase("Warehouse")) {
                selectedFieldName = "warehouse_name";
            } else selectedFieldName = "name";

        });

        linkName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewModel.getSearchLinkApi("",
                        selectedDocType,
                        "",
                        null,
                        RequestCodes.API.LINK_NAME);
            }
        });
        linkName.setOnItemClickListener((parent, view, position, id) -> {
            selectedLinkName = (String) parent.getItemAtPosition(position);
            viewModel.geValue(selectedDocType, selectedFieldName, selectedLinkName);
        });
        viewModel.getValue().observe(this, value -> {
            if (value != null) {
                if (selectedDocType.equalsIgnoreCase("Shareholder") || selectedDocType.equalsIgnoreCase("Lead")) {
                    if (value.getValue().getTitle() != null)
                        linkTitle.setText(value.getValue().getTitle());
                } else if (selectedDocType.equalsIgnoreCase("Customer")) {
                    if (value.getValue().getCustomer_name() != null)
                        linkTitle.setText(value.getValue().getCustomer_name());
                } else if (selectedDocType.equalsIgnoreCase("Supplier")) {
                    if (value.getValue().getSupplier_name() != null)
                        linkTitle.setText(value.getValue().getSupplier_name());
                } else if (selectedDocType.equalsIgnoreCase("Manufacturer")) {
                    if (value.getValue().getShort_name() != null)
                        linkTitle.setText(value.getValue().getShort_name());
                } else if (selectedDocType.equalsIgnoreCase("Warehouse")) {
                    if (value.getValue().getWarehouse_name() != null)
                        linkTitle.setText(value.getValue().getWarehouse_name());
                } else {
                    if (value.getValue().getName() != null)
                        linkTitle.setText(value.getValue().getName());
                }
            }
        });

        add.setOnClickListener(v -> {
            if (!selectedDocType.isEmpty() && !selectedLinkName.isEmpty() && !linkTitle.getText().toString().isEmpty()) {
                Reference reference = new Reference(selectedDocType, selectedLinkName, linkTitle.getText().toString());
                referenceAdapter.addItem(reference);
                dialog.dismiss();
                selectedDocType = "";
                selectedFieldName = "";
                selectedLinkName = "";
            }
        });
    }

    @Override
    protected void onDestroy() {
        AddContactRepo.getInstance().value.setValue(null);
        AddContactRepo.getInstance().savedDoc.setValue(null);
        AddContactRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
        super.onDestroy();
    }
}