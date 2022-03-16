package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.app.AppSession;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.repositories.CommunicationsRepo;
import com.example.erpnext.repositories.LeadSourcesRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class CommunicationsViewModel extends ViewModel {
    CommunicationsRepo repo;

    public CommunicationsViewModel() {
        repo = CommunicationsRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getItemsApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }
    public void getContacts(String text) {
        repo.getContact(text);
    }
    public void sendMail(HashMap<String, String> data) {
repo.sendMail(data);
    }
//    public void saveDocApi(String sourceName) {
//        try {
//            JSONObject jsonDoc = new JSONObject("{\"docstatus\":0,\"doctype\":\"Lead Source\",\"name\":\"new-lead-source\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\""+ AppSession.get("email")+"\",\"__run_link_triggers\":1}");
//            jsonDoc.put("source_name",sourceName);
//            repo.saveDoc(jsonDoc);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }
    public LiveData<List<SearchResult>> getContacts() {
        return repo.getContacts();
    }


    public LiveData<JSONObject> emailSent() {
        return repo.emailSent();
    }

}