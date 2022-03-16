package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.WarehouseRepo;

import java.util.List;

public class WarehouseViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    WarehouseRepo repo;

    public WarehouseViewModel() {
        repo = WarehouseRepo.getInstance();
    }

    public void getWarehouseList(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getWarehouse(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getWarehouse() {
        return repo.getWarehouse();
    }
}