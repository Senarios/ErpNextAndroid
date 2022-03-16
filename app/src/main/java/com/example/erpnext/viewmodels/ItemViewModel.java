package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.ItemRepo;

import java.util.List;

public class ItemViewModel extends ViewModel {
    ItemRepo repo;

    public ItemViewModel() {
        repo = ItemRepo.getInstance();
    }

    // TODO: Implement the ViewModel
    public void getItemList(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }
}