package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.models.WarehouseItem;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddReconciliationRepo;

import org.json.JSONObject;

import java.util.List;

public class AddReconciliationViewModel extends ViewModel {
    AddReconciliationRepo repo;

    public AddReconciliationViewModel() {
        repo = AddReconciliationRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getSearchLinkApi(String doctype, String query, String filters, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters, "");
    }

    public void getDiffAccount(String purpose) {
        repo.getDiffAccountApi(purpose);
    }

    public void saveDocApi(JSONObject jsonObject) {
        repo.saveDoc(jsonObject);
    }

    public void getWarehouseItems(String warehouse) {
        repo.getWarehouseItems(warehouse);
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public LiveData<String> getDiffAccount() {
        return repo.getDiffAccount();
    }

    public LiveData<List<WarehouseItem>> getWarehouses() {
        return repo.getWarehouseList();
    }

    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }

}