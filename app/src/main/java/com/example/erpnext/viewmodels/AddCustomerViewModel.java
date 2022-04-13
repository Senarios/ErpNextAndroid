package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.adapters.CreditLimitsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.models.CreditLimit;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddCustomerGroupRepo;
import com.example.erpnext.repositories.AddCustomerRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AddCustomerViewModel extends ViewModel {
    AddCustomerRepo repo;

    public AddCustomerViewModel() {
        repo = AddCustomerRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getSearchLinkApi(String doctype, String query, String filters, String ignore_user_permissions, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters, "", ignore_user_permissions);
    }


    public void saveDocApi(HashMap<String, String> data) {
        try {
            JSONObject jsonDoc = new JSONObject("{\"docstatus\":0,\"doctype\":\"Customer\",\"name\":\"new-customer-12\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\""+AppSession.get("email")+"\",\"naming_series\":\"CUST-.YYYY.-\",\"customer_type\":\"Company\",\"customer_group\":\"All Customer Groups\",\"territory\":\"All Territories\",\"so_required\":0,\"dn_required\":0,\"disabled\":0,\"is_internal_customer\":0,\"language\":\"en\",\"is_frozen\":0,\"customer_name\":\"March Rec\",\"image\":\"/files/mytask.png\",\"accounts\":[{\"docstatus\":0,\"doctype\":\"Party Account\",\"name\":\"new-party-account-3\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"rvlirshad@gmail.com\",\"company\":\"Izat Afghan Limited\",\"parent\":\"new-customer-12\",\"parentfield\":\"accounts\",\"parenttype\":\"Customer\",\"idx\":1,\"__unedited\":false,\"account\":\"Debtors - IAL\"}],\"phone\":\"12345678\",\"reference\":\"near march\"}");
            JSONObject jsonObject1 = new JSONObject(data);

            JSONObject json = new JSONObject();
            Iterator i1 = jsonObject1.keys();
            Iterator i2 = jsonDoc.keys();
            String tmp_key;
            while (i1.hasNext()) {
                tmp_key = (String) i1.next();
                json.put(tmp_key, jsonObject1.get(tmp_key));
            }
            while (i2.hasNext()) {
                tmp_key = (String) i2.next();
                json.put(tmp_key, jsonDoc.get(tmp_key));
            }
            json.put("image", data.get("image"));
            json.put("customer_name", data.get("customer_name"));
            repo.saveDoc(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }


    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }


}