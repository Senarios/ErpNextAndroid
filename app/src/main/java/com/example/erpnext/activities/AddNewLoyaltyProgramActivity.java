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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.CollectionTierAdapter;
import com.example.erpnext.adapters.POSOpeningFieldsAdapter;
import com.example.erpnext.adapters.viewHolders.CollectionTierViewHolder;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.CollectionTierCallback;
import com.example.erpnext.models.CollectionTier;
import com.example.erpnext.models.Doc;
import com.example.erpnext.models.PendingLoyalty;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.CreateLoyaltyRequestBody;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.SaveLoyaltyProgramResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.roomDB.data.DBSearchLink;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AddNewLoyaltyProgramActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkResponse, DateTime.datePickerCallback, AdapterView.OnItemSelectedListener, CollectionTierCallback {
    public static HashMap<String, String> data = new HashMap<String, String>();
    private AutoCompleteTextView territories_edit, company_edit, expense_account_edit, customer_group, cost_centre_edit, selected_edit;
    private TextView modeOfPayment, counter, opening_amount, difference_amount, expected_amount;
    private TextView toDate_tv, fromDate_tv, addCollectionTier;
    private EditText conversion_factor_edit, expiration_days_edit, name_edit;
    private Doc doc;
    private Spinner programTypeSpinner;
    private ImageView back;
    private String selectedDateField = "";
    private AutoCompleteTextView posOpeningEntry;
    private RecyclerView tiersRV;
    private CollectionTierAdapter collectionTierAdapter;
    private LinearLayout openingAmountLayout;
    private Button save_btn;
    private Dialog dialog;
    private String doctype, query, filters, doctype_reference;

    private RecyclerView fieldsRv, sectionBreakRv, readOnlyRv;
    private POSOpeningFieldsAdapter fieldsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_loyalty_program);
        initViews();
        setClickisteners();
        setCollectionAdapter(new ArrayList<>());
        if (getIntent().hasExtra("doc")) {
            String docJsonString = (String) getIntent().getExtras().get("doc");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            doc = gson.fromJson(docJsonString, Doc.class);

        }
    }

    private void setClickisteners() {
        back.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        toDate_tv.setOnClickListener(this);
        fromDate_tv.setOnClickListener(this);
        addCollectionTier.setOnClickListener(this);
//        periodStartDate_tv.setOnClickListener(this);

    }

    private void initViews() {
        addCollectionTier = findViewById(R.id.add);
        back = findViewById(R.id.back);
        save_btn = findViewById(R.id.save);
        company_edit = findViewById(R.id.company_edit);
        territories_edit = findViewById(R.id.customer_territory_edit);
        expense_account_edit = findViewById(R.id.expense_account_edit);
        customer_group = findViewById(R.id.customer_group_edit);
        cost_centre_edit = findViewById(R.id.cost_centre_edit);
        toDate_tv = findViewById(R.id.to_date_edit);
        fromDate_tv = findViewById(R.id.from_date_edit);
        name_edit = findViewById(R.id.program_name_edit);
        tiersRV = findViewById(R.id.tier_rv);
        programTypeSpinner = findViewById(R.id.program_type_edit);
        conversion_factor_edit = findViewById(R.id.autoCompleteEditConFactor);

        company_edit.setOnFocusChangeListener((v, hasFocus) -> {
            selected_edit = company_edit;
            if (hasFocus) {
                if (Utils.isNetworkAvailable()) {
                    getLinkSearch("Company", "Loyalty Program", null);
                } else {
                    doctype = "Company";
                    doctype_reference = "Loyalty Program";
                    filters = null;
                    DBSearchLink.loadFromRef(this, doctype, filters, doctype_reference, company_edit);
                }
            }
        });
        company_edit.setOnItemClickListener((parent, view, position, id) -> {
            data.put("company", (String) parent.getItemAtPosition(position));

        });
        customer_group.setOnFocusChangeListener((v, hasFocus) -> {
            selected_edit = customer_group;
            if (hasFocus) {
                if (Utils.isNetworkAvailable()) {
                    getLinkSearch("Customer Group", "Loyalty Program", null);
                } else {
                    doctype = "Customer Group";
                    doctype_reference = "Loyalty Program";
                    filters = null;
                    DBSearchLink.loadFromRef(this, doctype, filters, doctype_reference, customer_group);
                }
            }
        });
        customer_group.setOnItemClickListener((parent, view, position, id) -> {
            data.put("customer_group", (String) parent.getItemAtPosition(position));

        });
        expense_account_edit.setOnFocusChangeListener((v, hasFocus) -> {
            selected_edit = expense_account_edit;
            if (hasFocus) {
                if (Utils.isNetworkAvailable()) {
                    getLinkSearch("Account", "Loyalty Program", "{\"root_type\":\"Expense\",\"is_group\":0}");
                } else {
                    doctype = "Account";
                    doctype_reference = "Loyalty Program";
                    filters = "{\"root_type\":\"Expense\",\"is_group\":0}";
                    DBSearchLink.loadFromRef(this, doctype, filters, doctype_reference, expense_account_edit);
                }
            }
        });
        expense_account_edit.setOnItemClickListener((parent, view, position, id) -> {
            data.put("expense_account", (String) parent.getItemAtPosition(position));
        });
        cost_centre_edit.setOnFocusChangeListener((v, hasFocus) -> {
            selected_edit = cost_centre_edit;
            if (hasFocus) {
                if (Utils.isNetworkAvailable()) {
                    getLinkSearch("Cost Center", "Loyalty Program", null);
                } else {
                    doctype = "Cost Center";
                    doctype_reference = "Loyalty Program";
                    filters = null;
                    DBSearchLink.loadFromRef(this, doctype, filters, doctype_reference, cost_centre_edit);
                }
            }
        });
        cost_centre_edit.setOnItemClickListener((parent, view, position, id) -> {
            data.put("cost_center", (String) parent.getItemAtPosition(position));
        });
        territories_edit.setOnFocusChangeListener((v, hasFocus) -> {
            selected_edit = territories_edit;
            if (hasFocus) {
                if (Utils.isNetworkAvailable()) {
                    getLinkSearch("Territory", "Loyalty Program", null);
                } else {
                    doctype = "Territory";
                    doctype_reference = "Loyalty Program";
                    filters = null;
                    DBSearchLink.loadFromRef(this, doctype, filters, doctype_reference, territories_edit);
                }
            }
        });
        territories_edit.setOnItemClickListener((parent, view, position, id) -> {
            data.put("customer_territory", (String) parent.getItemAtPosition(position));
        });
        name_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && !s.toString().equalsIgnoreCase("")) {
                    data.put("loyalty_program_name", name_edit.toString());
                }
            }
        });

        List<String> stringList = new ArrayList<>();
        stringList.add("Single Tier Program");
        stringList.add("Multiple Tier Program");
        String[] list = stringList.toArray(new String[0]);
        setSpinner(list, programTypeSpinner);
    }

    private void setCollectionAdapter(List<CollectionTier> collectionTiers) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        collectionTierAdapter = new CollectionTierAdapter(this, collectionTiers, this);
        tiersRV.setLayoutManager(linearLayoutManager);
        tiersRV.setAdapter(collectionTierAdapter);
    }

    //
    private void setSpinner(String[] list, Spinner spinner) {
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(this, R.layout.checkout_spinner_text, list);
        spinnerAdapter.setDropDownViewResource(R.layout.checkout_spinner_layout);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.add:
                if (programTypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Single Tier Program")) {
                    if (collectionTierAdapter.getAllItems().size() == 1) {
                        Notify.Toast(getString(R.string.add_morethanone_tier));
                    } else showCollectionTierDialog();
                } else {
                    showCollectionTierDialog();
                }
                break;
            case R.id.to_date_edit:
                selectedDateField = "to_date";
                DateTime.showDatePicker(this, this);
                break;
            case R.id.from_date_edit:
                selectedDateField = "from_date";
                DateTime.showDatePicker(this, this);
                break;
            case R.id.save:
                if (!fieldError()) {
                    data.put("doctype", doc.getName());
                    data.put("docstatus", doc.getDocstatus().toString());
                    data.put("name", "new-pos-opening-entry");
                    data.put("__islocal", "1");
                    data.put("__unsaved", "1");
                    data.put("auto_opt_in", "0");
                    data.put("owner", doc.getOwner());

                    List<CollectionTier> collectionTiers = getCollectionTiers();
                    JSONObject json = new JSONObject(data);
                    try {
                        if (Utils.isNetworkAvailable()) {
                            Gson gson = new Gson();
                            JSONArray jsonObject = new JSONArray(gson.toJson(collectionTiers));
                            json.putOpt("collection_rules", jsonObject);
                            CreateLoyaltyRequestBody loyaltyRequestBody = new CreateLoyaltyRequestBody();
                            loyaltyRequestBody.setDoc(json);
                            loyaltyRequestBody.setAction("Save               m");
                            saveDoc(loyaltyRequestBody);
                        } else {
                            CreateLoyaltyRequestBody loyaltyRequestBody = new CreateLoyaltyRequestBody();
                            loyaltyRequestBody.setDoc(json);
                            loyaltyRequestBody.setCollectionTierList(collectionTiers);
                            loyaltyRequestBody.setAction("Save");
                            PendingLoyalty pendingLoyalty = new PendingLoyalty();
                            pendingLoyalty.setCreateOPERequestBody(loyaltyRequestBody);
                            MainApp.database.pendingLoyaltyDao().insert(pendingLoyalty);
                            Notify.ToastLong(getString(R.string.loyal_prog_creation));
                            Intent intent = new Intent();
                            intent.putExtra("loyalty", new Gson().toJson(loyaltyRequestBody.getDoc()));
                            setResult(RESULT_OK, intent);
                            Notify.Toast(getString(R.string.successfully_created));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

                break;
        }

    }

    private List<CollectionTier> getCollectionTiers() {
        List<CollectionTier> collectionTiers = new ArrayList<>();
        for (CollectionTier collectionTier : collectionTierAdapter.getAllItems()) {
            CollectionTier collectionTier1 = new CollectionTier();
            collectionTier1.setTierName(collectionTier.getName());
            collectionTier1.setOwner(doc.getOwner());
            collectionTier1.setCollectionFactor(collectionTier.getCollectionFactor());
            collectionTier1.setDocstatus(1);
            collectionTier1.setMinSpent(collectionTier.getMinSpent());
            collectionTier1.setDoctype("Loyalty Program Collection");
            collectionTier1.setIslocal(1);
            collectionTier1.setUnsaved(1);
            collectionTier1.setIdx(1);
            collectionTier1.setName("new-loyalty-program-collection");
            collectionTier1.setParent("new-loyalty-program");
            collectionTier1.setParentfield("new-loyalty-program");
            collectionTier1.setParenttype("Loyalty Program");
            collectionTier1.setParentfield("collection_rules");
            collectionTier1.setUnedited(false);
            collectionTiers.add(collectionTier1);

        }
        return collectionTiers;
    }

    private void showCollectionTierDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_collection_tier_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText tier_name = dialog.findViewById(R.id.name_edit);
        EditText collection_factor = dialog.findViewById(R.id.collection_factor_edit);
        EditText min_spend = dialog.findViewById(R.id.min_spend);
        Button add = dialog.findViewById(R.id.add);


        add.setOnClickListener(v -> {
            CollectionTier collectionTier = new CollectionTier();
            if (tier_name.getText().toString() != null && !tier_name.getText().toString().equalsIgnoreCase("")) {
                if (collection_factor.getText().toString() != null && !collection_factor.getText().toString().equalsIgnoreCase("")) {

                    collectionTier.setName(tier_name.getText().toString());
                    collectionTier.setCollectionFactor(Integer.parseInt(collection_factor.getText().toString()));
                    if (min_spend.getText().toString() != null && !min_spend.getText().toString().equalsIgnoreCase("")) {
                        collectionTier.setMinSpent(Integer.parseInt(min_spend.getText().toString()));
                    }
                    collectionTierAdapter.addItem(collectionTier);
                    dialog.dismiss();
                } else Notify.Toast(getString(R.string.collection_tier));
            } else Notify.Toast(getString(R.string.enter_tier_name));

        });

    }

    private boolean fieldError() {
        data.put("loyalty_program_name", name_edit.getText().toString());
        if (!data.containsKey("loyalty_program_name") || data.get("loyalty_program_name") == null || data.get("loyalty_program_name").isEmpty()) {
            Notify.Toast(getString(R.string.write_name));
            return true;
        } else if (!data.containsKey("from_date") || data.get("from_date") == null || data.get("from_date").isEmpty()) {
            Notify.Toast(getString(R.string.select_from_date));
            return true;
        } else if (collectionTierAdapter.getAllItems() == null || collectionTierAdapter.getAllItems().isEmpty()) {
            Notify.Toast(getString(R.string.program_collection_tier));
            return true;
        } else {
            return false;
        }
    }

    private void getLinkSearch(String doctype, String reference, String filters) {
        this.doctype = doctype;
        this.doctype_reference = reference;
        this.filters = filters;
        int requestCode = RequestCodes.API.LINK_SEARCH;
        if (reference.equalsIgnoreCase("POS Invoice Reference")) {
            requestCode = RequestCodes.API.LINK_SEARCH_INVOICE;
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(this, "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", doctype, "0", filters, reference, "")))
                .execute();
    }

    private void saveDoc(CreateLoyaltyRequestBody body) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SAVE_LOYALTY)
                .autoLoadingCancel(Utils.getLoading(this, "Please wait..."))
                .enque(Network.apis().saveLoyaltyDoc(body.getDoc(), "Save"))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.SAVE_LOYALTY) {
            if (response.code() == 200) {
                SaveLoyaltyProgramResponse res = (SaveLoyaltyProgramResponse) response.body();
                if (res != null) {
                    Intent intent = new Intent();
                    intent.putExtra("saveProfile", true);
                    setResult(RESULT_OK, intent);
                    Notify.Toast(getString(R.string.successfully_created));
                    finish();
                }
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null) {
                DBSearchLink.saveWithRef(res, doctype, filters, doctype_reference);
                List<String> list = new ArrayList<>();
                if (res != null && !res.getResults().isEmpty()) {
                    for (SearchResult searchResult : res.getResults()) {
                        list.add(searchResult.getValue());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, list);
                    selected_edit.setAdapter(adapter);
                    selected_edit.showDropDown();
                }
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        if (response.getServerMessages() != null) {
            Notify.Toast(response.getServerMessages());
        } else Notify.Toast(response.getMessage());
    }

    @Override
    public void onSelected(String date) {
        data.put(selectedDateField, date);
        if (selectedDateField.equalsIgnoreCase("to_date")) {
            toDate_tv.setText(date);
        } else if (selectedDateField.equalsIgnoreCase("from_date")) {
            fromDate_tv.setText(date);
        }
    }

    @Override
    public void onDeleteTierClick(CollectionTier item, CollectionTierViewHolder viewHolder, int position) {
        collectionTierAdapter.removeItem(item, position);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String programType = parent.getItemAtPosition(position).toString();
        data.put("loyalty_program_type", programType);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}