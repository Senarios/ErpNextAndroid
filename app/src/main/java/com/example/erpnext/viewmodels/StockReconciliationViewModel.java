package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.StockReconciliationRepo;

import java.util.List;

public class StockReconciliationViewModel extends ViewModel {
    StockReconciliationRepo repo;

    public StockReconciliationViewModel() {
        repo = StockReconciliationRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getReconciliationItems(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }
}