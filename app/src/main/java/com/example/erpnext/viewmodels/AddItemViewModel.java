package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddItemRepo;

import org.json.JSONObject;

public class AddItemViewModel extends ViewModel {
    AddItemRepo repo;

    public AddItemViewModel() {
        repo = AddItemRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void saveDocApi(JSONObject jsonObject) {
        repo.saveDoc(jsonObject);
    }


    public void getSearchLinkApi(String text, String doctype, String query, String filters, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters, text);
    }


    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }

}
