package com.example.erpnext.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.FieldsAdapter;
import com.example.erpnext.adapters.ReadOnlyFieldsAdapter;
import com.example.erpnext.adapters.SectionsBreaksFieldsAdapter;
import com.example.erpnext.models.Doc;
import com.example.erpnext.models.Field;
import com.example.erpnext.models.Payment;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.CheckNameResponse;
import com.example.erpnext.network.serializers.response.SaveDocResponse;
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

public class AddNewPOSProfileActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkResponse {
    public static HashMap<String, String> data = new HashMap<String, String>();
    private final List<Field> linkFieldList = new ArrayList<>();
    private final List<Field> currentFieldList = new ArrayList<>();
    private final List<Field> fieldsListRv = new ArrayList<>();
    ArrayList<List<Field>> filteredSectionFieldLists = new ArrayList<>();
    ArrayList<List<Field>> filteredReadOnlyFieldLists = new ArrayList<>();
    boolean isSectionToAdd = false;
    boolean isReadOnlyToAdd = false;
    boolean isValidName = false;
    private Doc doc;
    private List<Field> fieldList = new ArrayList<>();
    private SectionsBreaksFieldsAdapter sectionsBreaksFieldsAdapter;
    private RecyclerView fieldsRv, sectionBreakRv, readOnlyRv;
    private FieldsAdapter fieldsAdapter;
    private ReadOnlyFieldsAdapter readOnlyFieldsAdapter;
    private ImageView back;
    private EditText name_edit;
    private RelativeLayout name_field;
    private Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_posprofile);
        initViews();
        setClickisteners();
        data = new HashMap<>();
        if (getIntent().hasExtra("doc")) {
            String docJsonString = (String) getIntent().getExtras().get("doc");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
//            Type type = new TypeToken<List<Field>>() {}.getType();
//            fieldList = gson.fromJson(projectJsonString, type);
            doc = gson.fromJson(docJsonString, Doc.class);
            if (doc.getName().equalsIgnoreCase("POS Profile"))
                name_field.setVisibility(View.VISIBLE);

            if (doc != null && doc.getFields() != null) {
                fieldList = doc.getFields();
                if (!fieldList.isEmpty()) {
                    filterSectionAndLinkLists(fieldList);
                    if (!currentFieldList.isEmpty()) {
                        List<Field> fields = new ArrayList<>();
                        fields.addAll(currentFieldList);
                        filteredSectionFieldLists.add(fields);
                        currentFieldList.clear();
                    }
                    filteredReadOnlyFieldLists(fieldList);
                }
                if (!linkFieldList.isEmpty()) setFieldsAdapter(linkFieldList);
                if (!filteredReadOnlyFieldLists.isEmpty())
                    setReadOnlyFieldsAdapter(filteredReadOnlyFieldLists);
                if (!filteredSectionFieldLists.isEmpty())
                    setSectionFieldsAdapter(filteredSectionFieldLists);

            }
        }


    }

    private void setClickisteners() {
        back.setOnClickListener(this);
        save_btn.setOnClickListener(this);

    }

    private void initViews() {
        fieldsRv = findViewById(R.id.editFieldsRV);
        sectionBreakRv = findViewById(R.id.sectionBreakFieldsRV);
        readOnlyRv = findViewById(R.id.readOnlyFieldsRV);
        back = findViewById(R.id.back);
        save_btn = findViewById(R.id.save);
        name_edit = findViewById(R.id.name_edit);
        name_field = findViewById(R.id.edit_field);
        name_edit.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (name_edit.getText().toString() == null || name_edit.getText().toString().isEmpty()) {
                    name_edit.setError("Name field can't be empty");
                } else checkName(name_edit.getText().toString());
            }
        });

    }

    private void setFieldsAdapter(List<Field> fieldList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        fieldsAdapter = new FieldsAdapter(this, fieldList, false);
        fieldsRv.setLayoutManager(linearLayoutManager);
        fieldsRv.setAdapter(fieldsAdapter);
    }

    private void setSectionFieldsAdapter(List<List<Field>> fieldList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        sectionsBreaksFieldsAdapter = new SectionsBreaksFieldsAdapter(this, fieldList, false);
        sectionBreakRv.setLayoutManager(linearLayoutManager);
        sectionBreakRv.setAdapter(sectionsBreaksFieldsAdapter);
    }

    private void setReadOnlyFieldsAdapter(List<List<Field>> fieldList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        readOnlyFieldsAdapter = new ReadOnlyFieldsAdapter(this, fieldList, false);
        readOnlyRv.setLayoutManager(linearLayoutManager);
        readOnlyRv.setAdapter(readOnlyFieldsAdapter);
    }

    private void filterSectionAndLinkLists(List<Field> fieldList) {
        if (!fieldList.isEmpty()) {

            for (Field field : fieldList) {
                if (field.getFieldtype().equalsIgnoreCase("Section Break")) {
                    isSectionToAdd = true;
                    createList(field, true);

                } else if (isSectionToAdd) {
                    createList(field, false);

                } else if (field.getFieldtype().equalsIgnoreCase("Link")) {
                    linkFieldList.add(field);
                } else if (field.getParent().equalsIgnoreCase("POS Opening Entry") &&
                        field.getFieldtype().equalsIgnoreCase("DateTime") || field.getFieldtype().equalsIgnoreCase("Date")) {
                    linkFieldList.add(field);
                }
            }

        }
    }

    private void filteredReadOnlyFieldLists(List<Field> fieldList) {
        if (!fieldList.isEmpty()) {
            if (!currentFieldList.isEmpty()) currentFieldList.clear();
            for (Field field : fieldList) {
                if (field.getFieldtype().equalsIgnoreCase("Read only")) {
                    isReadOnlyToAdd = true;
                    createRealOnlyList(field, true);

                } else if (isReadOnlyToAdd) {
                    boolean ended = false;
                    if (field.getFieldtype().equalsIgnoreCase("Column Break")) {
                        ended = true;
                    }

                    if (!ended) {
                        createRealOnlyList(field, false);
                    } else {
                        List<Field> list = new ArrayList<>();
                        for (Field field1 : currentFieldList) {
                            list.add(field1);
                        }
                        filteredReadOnlyFieldLists.add(list);
                        isReadOnlyToAdd = false;
                        currentFieldList.clear();
                    }
                }
            }

        }

    }

    private void createRealOnlyList(Field field, boolean isNewList) {
        if (isNewList) {
            if (!currentFieldList.isEmpty()) {
                List<Field> list = new ArrayList<>();
                for (Field field1 : currentFieldList) {
                    list.add(field1);
                }
                filteredReadOnlyFieldLists.add(list);
                currentFieldList.clear();

            }
            currentFieldList.add(field);

        } else currentFieldList.add(field);
    }

    private void createList(Field field, boolean isNewList) {
        if (isNewList) {
            if (!currentFieldList.isEmpty()) {
                List<Field> list = new ArrayList<>();
                for (Field field1 : currentFieldList) {
                    list.add(field1);
                }
                filteredSectionFieldLists.add(list);
                currentFieldList.clear();

            }
            currentFieldList.add(field);

        } else {
            if (field.getFieldname().equalsIgnoreCase("payments")) {
                field.setLabel("Mode of Payment");
            }
            currentFieldList.add(field);
        }
    }


//    @Override
//    public void onProfileClick(Profile project) {
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        String projectToJsonString = gson.toJson(project);
//        Bundle bundle = new Bundle();
//        bundle.putString("profile", projectToJsonString);
////        ((MainActivity) getActivity()).fragmentTrx(ProjectDetailsFragment.newInstance(), bundle);
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.save:
                if (!fieldError()) {
                    data.put("doctype", doc.getName());
                    data.put("docstatus", doc.getDocstatus().toString());
                    data.put("name", "new-pos-profile-2");
                    data.put("__islocal", "1");
                    data.put("__unsaved", "1");
                    data.put("owner", doc.getOwner());
                    JSONObject json = new JSONObject(data);
                    try {
                        List<Payment> paymentList = new ArrayList<>();
                        Payment payment = new Payment();
                        payment.setDefault(1);
                        payment.setModeOfPayment(json.getString("payments"));
                        paymentList.add(payment);

                        Gson gson = new Gson();
                        JSONArray jsonObject = new JSONArray(gson.toJson(paymentList));
                        json.putOpt("payments", jsonObject);
                        saveDoc(json);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

                break;
        }

    }

    private boolean fieldError() {
        if (name_edit.getText().toString() == null || name_edit.getText().toString().isEmpty()) {
            Notify.Toast(getString(R.string.type_name));
            return true;
        } else if (!isValidName) {
            Notify.Toast(getString(R.string.type_another_name));
            return true;
        } else if (!data.containsKey("company") || data.get("company") == null || data.get("company").isEmpty()) {
            Notify.Toast(getString(R.string.select_company));
            return true;
        }
//        else if (!data.containsKey("customer") || data.get("customer") == null || data.get("customer").isEmpty()) {
//            Notify.Toast("Please select customer to continue");
//            return true;
//        }
        else if (!data.containsKey("warehouse") || data.get("warehouse") == null || data.get("warehouse").isEmpty()) {
            Notify.Toast(getString(R.string.select_warehouse));
            return true;
        } else if (!data.containsKey("payments") || data.get("payments") == null || data.get("payments").isEmpty()) {
            Notify.Toast(getString(R.string.select_payment_method));
            return true;
        } else if (!data.containsKey("currency") || data.get("currency") == null || data.get("currency").isEmpty()) {
            Notify.Toast(getString(R.string.select_currency));
            return true;
        } else if (!data.containsKey("write_off_account") || data.get("write_off_account") == null || data.get("write_off_account").isEmpty()) {
            Notify.Toast(getString(R.string.select_writeoff_account));
            return true;
        } else if (!data.containsKey("write_off_cost_center") || data.get("write_off_cost_center") == null || data.get("write_off_cost_center").isEmpty()) {
            Notify.Toast(getString(R.string.select_writeoff_account));
            return true;
        } else {
            data.put("__newname", name_edit.getText().toString());
            return false;
        }
    }

    private void checkName(String name) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.CHECK_OPENING_ENTRY)
                .autoLoadingCancel(Utils.getLoading(this, "Please wait..."))
                .enque(Network.apis().checkName(doc.getName(), "name", name))
                .execute();
    }

    private void saveDoc(JSONObject jsonObject) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SAVE_DOC)
                .autoLoadingCancel(Utils.getLoading(this, "Please wait..."))
                .enque(Network.apis().saveDoc(jsonObject, "Save"))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.SAVE_DOC) {
            if (response.code() == 200) {
                SaveDocResponse res = (SaveDocResponse) response.body();
                Intent intent = new Intent();
                intent.putExtra("saveProfile", true);
                setResult(RESULT_OK, intent);
                Notify.Toast(getString(R.string.successfully_created));
                finish();

            }
        } else if ((int) tag == RequestCodes.API.CHECK_OPENING_ENTRY) {
            CheckNameResponse res = (CheckNameResponse) response.body();
            if (res != null) {
                if (res.getMessage().getName() != null) {
                    isValidName = false;
                    name_edit.setError("This name is already exist.Please try again");

                } else {
                    isValidName = true;
                }
            }
        }

    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.Toast(response.getServerMessages().replace("[\\\"{\\\\\\\"message\\\\\\\": \\\\\\\"", ""));
    }

}