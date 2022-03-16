package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.CustomersRepo;

import java.util.List;

public class CustomersViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    CustomersRepo repo;

    public CustomersViewModel() {
        repo = CustomersRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getItemsApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }

}