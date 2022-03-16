package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.CustomerGroupsRepo;

import java.util.List;

public class CustomerGroupsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    CustomerGroupsRepo repo;

    public CustomerGroupsViewModel() {
        repo = CustomerGroupsRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getLandedCostList(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }
}