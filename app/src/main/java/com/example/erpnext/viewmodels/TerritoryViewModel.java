package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.TerritoryRepo;

import java.util.List;


public class TerritoryViewModel extends ViewModel {
    TerritoryRepo repo;

    public TerritoryViewModel() {
        repo = TerritoryRepo.getInstance();
    }

    // TODO: Implement the ViewModel
    public void getTerritoryList(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }
}