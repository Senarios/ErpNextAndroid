package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddItemPriceRepo;

import org.json.JSONObject;

public class AddItemPriceViewModel extends ViewModel {
    AddItemPriceRepo repo;

    public AddItemPriceViewModel() {
        repo = AddItemPriceRepo.getInstance();
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