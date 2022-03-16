package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.ItemPriceRepo;

import java.util.List;

public class ItemPriceViewModel extends ViewModel {
    ItemPriceRepo repo;

    public ItemPriceViewModel() {
        repo = ItemPriceRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getPurchaseList(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }

}