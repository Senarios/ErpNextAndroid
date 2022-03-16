package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.ContactsRepo;

import java.util.List;

public class ContactsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    ContactsRepo repo;

    public ContactsViewModel() {
        repo = ContactsRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getItemsApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getItemsApi(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getItems() {
        return repo.getItems();
    }
}