package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.LandedCostRepo;

import java.util.List;

public class LandedCostViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    LandedCostRepo repo;

    public LandedCostViewModel() {
        repo = LandedCostRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getLandedCostList(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public void searchLandedCostList(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart, String date) {
        repo.searchItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart, date);
    }

    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }

    public LiveData<List<List<String>>> getSearchedItems() {
        return repo.getSearchedItems();
    }
}