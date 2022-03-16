package com.example.erpnext.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.POSOpeningFieldsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.BalanceDetail;
import com.example.erpnext.models.Doc;
import com.example.erpnext.models.Field;
import com.example.erpnext.models.PendingOPE;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.CreateOPERequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.SaveOpeningEntryResponse;
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

public class AddNewOpeningActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkResponse {
    public static HashMap<String, String> data = new HashMap<String, String>();
    private final List<Field> linkFieldList = new ArrayList<>();
    boolean isSectionToAdd = false;
    private Doc doc;
    private List<Field> fieldList = new ArrayList<>();
    private RecyclerView fieldsRv, sectionBreakRv, readOnlyRv;
    private POSOpeningFieldsAdapter fieldsAdapter;
    private ImageView back;
    private EditText opening_Amount_edit;
    private LinearLayout openingAmountLayout;
    private Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_opening);
        initViews();
        setClickisteners();
        data = new HashMap<>();
        if (getIntent().hasExtra("doc")) {
            String docJsonString = (String) getIntent().getExtras().get("doc");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            doc = gson.fromJson(docJsonString, Doc.class);
            if (doc != null && doc.getFields() != null) {
                fieldList = doc.getFields();
                if (!fieldList.isEmpty()) {
                    filterSectionAndLinkLists(fieldList);
                }
                if (!linkFieldList.isEmpty()) {
                    if (doc.getName().equalsIgnoreCase("POS Opening Entry")) {
                        setFieldsAdapter(linkFieldList);
                    }
                }
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
        opening_Amount_edit = findViewById(R.id.opening_amount_autoCompleteEdit);
        openingAmountLayout = findViewById(R.id.opening_amount_layout);
        openingAmountLayout = findViewById(R.id.opening_amount_layout);
        opening_Amount_edit.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (opening_Amount_edit.getText().toString() != null && !opening_Amount_edit.getText().toString().equalsIgnoreCase("")) {
                    data.put("opening_amount", opening_Amount_edit.getText().toString());
                }
            }
        });
    }

    private void setFieldsAdapter(List<Field> fieldList) {
        fieldList = filterFieldFromClosing(fieldList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        fieldsAdapter = new POSOpeningFieldsAdapter(this, fieldList, false);
        fieldsRv.setLayoutManager(linearLayoutManager);
        fieldsRv.setAdapter(fieldsAdapter);
    }

    private List<Field> filterFieldFromClosing(List<Field> fieldList) {
        List<Field> list = new ArrayList<>();
        for (Field field : fieldList) {
            if (!field.getFieldname().equalsIgnoreCase("pos_closing_entry") && !field.getFieldname().equalsIgnoreCase("amended_from")) {
                list.add(field);
            }
        }
        return list;
    }


    private void filterSectionAndLinkLists(List<Field> fieldList) {
        if (!fieldList.isEmpty()) {
            for (Field field : fieldList) {
                if (!field.getFieldtype().equalsIgnoreCase("Section Break") && !field.getFieldtype().equalsIgnoreCase("Check")
                        && !field.getFieldtype().equalsIgnoreCase("Select")
                        && !field.getFieldtype().equalsIgnoreCase("Column Break")) {
                    linkFieldList.add(field);
                }
            }
        }
    }

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
                    data.put("name", "new-pos-opening-entry");
                    data.put("__islocal", "1");
                    data.put("__unsaved", "1");
                    data.put("owner", AppSession.get("email"));
                    data.put("status", "Draft");
                    data.put("set_posting_date", "1");
                    JSONObject json = new JSONObject(data);
                    try {
                        List<BalanceDetail> paymentList = new ArrayList<>();
                        BalanceDetail payment = new BalanceDetail();
                        payment.set__islocal(1);
                        payment.set__unsaved(1);
                        payment.setName("new-pos-opening-entry-detail");
                        payment.setDocstatus(0);
                        payment.setDoctype("POS Opening Entry Detail");
                        payment.setOwner(doc.getOwner());
                        if (data.get("opening_amount") != null && !data.get("opening_amount").equalsIgnoreCase("")) {
                            payment.setOpeningAmount(Integer.parseInt(data.get("opening_amount")));
                        }
                        payment.setModeOfPayment(data.get("balance_details"));
                        payment.setParent("new-pos-opening-entry");
                        payment.setParentfield("balance_details");
                        payment.setParenttype("POS Opening Entry");
                        payment.setIdx(1);
                        paymentList.add(payment);


                        if (Utils.isNetworkAvailable()) {
                            Gson gson = new Gson();
                            JSONArray jsonObject = new JSONArray(gson.toJson(paymentList));
                            json.putOpt("balance_details", jsonObject);
                            CreateOPERequestBody createOPERequestBody = new CreateOPERequestBody();
                            createOPERequestBody.setDoc(json);
                            createOPERequestBody.setAction("Submit");
                            saveDoc(createOPERequestBody);
                        } else {
                            CreateOPERequestBody createOPERequestBody = new CreateOPERequestBody();
                            createOPERequestBody.setDoc(json);
                            createOPERequestBody.setBalanceDetailList(paymentList);
                            createOPERequestBody.setAction("Submit");
                            PendingOPE pendingOPE = new PendingOPE();
                            pendingOPE.setCreateOPERequestBody(createOPERequestBody);
                            MainApp.database.pendingOPEDao().insertOPE(pendingOPE);
                            Intent intent = new Intent();
                            intent.putExtra("localOPE", new Gson().toJson(createOPERequestBody.getDoc()));
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

    private boolean fieldError() {
        if (!data.containsKey("period_start_date") || data.get("period_start_date") == null || data.get("period_start_date").isEmpty()) {
            Notify.Toast(getString(R.string.select_period_start_date));
            return true;
        } else if (!data.containsKey("posting_date") || data.get("posting_date") == null || data.get("posting_date").isEmpty()) {
            Notify.Toast(getString(R.string.select_posting_date));
            return true;
        } else if (!data.containsKey("company") || data.get("company") == null || data.get("company").isEmpty()) {
            Notify.Toast(getString(R.string.select_company));
            return true;
        } else if (!data.containsKey("pos_profile") || data.get("pos_profile") == null || data.get("pos_profile").isEmpty()) {
            Notify.Toast(getString(R.string.select_pos_profile));
            return true;
        } else if (!data.containsKey("user") || data.get("user") == null || data.get("user").isEmpty()) {
            Notify.Toast(getString(R.string.select_cashier));
            return true;
        } else if (!data.containsKey("balance_details") || data.get("balance_details") == null || data.get("balance_details").isEmpty()) {
            Notify.Toast(getString(R.string.select_balance_details));
            return true;
        } else {
            return false;
        }
    }


    private void saveDoc(CreateOPERequestBody body) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SAVE_DOC)
                .autoLoadingCancel(Utils.getLoading(this, "Please wait..."))
                .enque(Network.apis().saveOpeningEntryDoc(body.getDoc(), body.getAction()))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.SAVE_DOC) {
            if (response.code() == 200) {
                SaveOpeningEntryResponse res = (SaveOpeningEntryResponse) response.body();
                Intent intent = new Intent();
                intent.putExtra("saveProfile", true);
                setResult(RESULT_OK, intent);
                Notify.Toast(getString(R.string.successfully_created));
                finish();

            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.Toast(response.getServerMessages());
    }

}