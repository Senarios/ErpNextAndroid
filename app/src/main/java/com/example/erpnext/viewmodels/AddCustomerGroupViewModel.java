package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.adapters.CreditLimitsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.models.CreditLimit;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddCustomerGroupRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AddCustomerGroupViewModel extends ViewModel {
    AddCustomerGroupRepo repo;

    public AddCustomerGroupViewModel() {
        repo = AddCustomerGroupRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getSearchLinkApi(String doctype, String query, String filters, String ignore_user_permissions, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters, "", ignore_user_permissions);
    }


    public void saveDocApi(HashMap<String, String> data, CreditLimitsAdapter creditLimitsAdapter) {
        try {
            JSONObject jsonDoc = new JSONObject("{\"docstatus\":0,\"doctype\":\"Customer Group\",\"name\":\"new-customer-group-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"is_group\":1,\"credit_limits\":[]}");
            JSONObject jsonObject1 = new JSONObject(data);
            List<CreditLimit> creditLimitList = creditLimitsAdapter.getAllItems();
            JSONArray creditsArray = new JSONArray();
            for (CreditLimit creditLimit : creditLimitList) {
                JSONObject jsonObject = new JSONObject("{\"docstatus\":0,\"doctype\":\"Customer Credit Limit\",\"name\":\"new-customer-credit-limit-3\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"company\":\"Izat Afghan Limited\",\"bypass_credit_limit_check\":0,\"parent\":\"new-customer-group-1\",\"parentfield\":\"credit_limits\",\"parenttype\":\"Customer Group\",\"idx\":1,\"__unedited\":false,\"credit_limit\":0}");
                jsonObject.put("credit_limit", creditLimit.getCreditLimit());
                if (creditLimit.isByPass()) {
                    jsonObject.put("bypass_credit_limit_check", 1);
                } else {
                    jsonObject.put("bypass_credit_limit_check", 0);
                }
                creditsArray.put(jsonObject);
            }
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
            json.putOpt("credit_limits", creditsArray);
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