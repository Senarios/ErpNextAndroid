package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.OpportunitiesRepo;

import java.util.List;

public class OpportunitiesViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    OpportunitiesRepo repo;

    public OpportunitiesViewModel() {
        repo = OpportunitiesRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getLandedCostList(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public void getSearchedLandedCostList(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart, String date) {
        repo.searchItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart, date);
    }

    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }

    public LiveData<List<List<String>>> getSearchedItems() {
        return repo.getSearchedItems();
    }
}