package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.app.AppSession;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddLeadRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AddLeadViewModel extends ViewModel {
    private MutableLiveData<List<List<String>>> reportViewList;
    private AddLeadRepo repo;

    public AddLeadViewModel() {
        repo = AddLeadRepo.getInstance();
    }


    public void getSearchLinkApi(String text, String doctype, String query, String filters, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters, text);
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public void saveDocApi(HashMap<String, String> data) {
        try {
            JSONObject jsonObject = new JSONObject("{\"docstatus\":0,\"doctype\":\"Lead\",\"name\":\"new-lead-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"organization_lead\":0,\"naming_series\":\"CRM-LEAD-.YYYY.-\",\"status\":\"Lead\",\"address_type\":\"Billing\",\"country\":\"Afghanistan\",\"type\":\"\",\"request_type\":\"\",\"company\":\"Izat Afghan Limited\",\"territory\":\"All Territories\",\"language\":\"en\",\"unsubscribed\":0}");
            JSONObject jsonObject1 = new JSONObject(data);

            JSONObject json = new JSONObject();
            Iterator i1 = jsonObject1.keys();
            Iterator i2 = jsonObject.keys();
            String tmp_key;
            while (i1.hasNext()) {
                tmp_key = (String) i1.next();
                json.put(tmp_key, jsonObject1.get(tmp_key));
            }
            while (i2.hasNext()) {
                tmp_key = (String) i2.next();
                json.put(tmp_key, jsonObject.get(tmp_key));
            }

            repo.saveDoc(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }

}
