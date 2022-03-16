package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.app.AppSession;
import com.example.erpnext.repositories.CustomersRepo;
import com.example.erpnext.repositories.LeadSourcesRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LeadSourcesViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    LeadSourcesRepo repo;

    public LeadSourcesViewModel() {
        repo = LeadSourcesRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getItemsApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }
    public void saveDocApi(String sourceName) {
        try {
            JSONObject jsonDoc = new JSONObject("{\"docstatus\":0,\"doctype\":\"Lead Source\",\"name\":\"new-lead-source\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\""+AppSession.get("email")+"\",\"__run_link_triggers\":1}");
            jsonDoc.put("source_name",sourceName);
            repo.saveDoc(jsonDoc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }
    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }

}