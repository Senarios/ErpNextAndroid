package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.app.AppSession;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddOpportunityRepo;
import com.example.erpnext.utils.DateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AddOpportunityViewModel extends ViewModel {
    private MutableLiveData<List<List<String>>> reportViewList;
    private AddOpportunityRepo repo;

    public AddOpportunityViewModel() {
        repo = AddOpportunityRepo.getInstance();
    }


    public void getSearchLinkApi(String text, String doctype, String query, String filters, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters, text);
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public void saveDocApi(HashMap<String, String> data) {
        try {
            JSONObject jsonObject = new JSONObject("{\"docstatus\":0,\"doctype\":\"Opportunity\",\"name\":\"new-opportunity-3\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"naming_series\":\"CRM-OPP-.YYYY.-\",\"currency\":\"USD\",\"with_items\":0,\"territory\":\"All Territories\",\"customer_group\":\"All Customer Groups\",\"company\":\"Izat Afghan Limited\",\"transaction_date\":\"" + DateTime.getCurrentDate() + "\",\"language\":\"en\",\"idx\":0,\"contact_by\":\"" + AppSession.get("email") + "\",\"items\":[],\"lost_reasons\":[],\"__onload\":{\"load_after_mapping\":true}}");
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
