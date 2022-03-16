package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.SalesPersonsRepo;

import java.util.List;

public class SalesPersonsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    SalesPersonsRepo repo;

    public SalesPersonsViewModel() {
        repo = SalesPersonsRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getItemsApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }

}