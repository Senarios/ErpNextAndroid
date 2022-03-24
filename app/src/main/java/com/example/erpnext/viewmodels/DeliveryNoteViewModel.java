package com.example.erpnext.viewmodels;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.DeliveryNoteRepo;

import java.util.List;

public class DeliveryNoteViewModel extends ViewModel {

    private DeliveryNoteRepo deliveryNoteRepo;

    public DeliveryNoteViewModel() {
        deliveryNoteRepo = DeliveryNoteRepo.getInstance();
    }

    public void getDeliveryNotesApi(Activity activity, String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        deliveryNoteRepo.getDeliveryNotes(activity, docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public void searchDeliveryNotesApi(Activity activity, String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart, String date) {
        deliveryNoteRepo.searchDeliveryNotes(activity, docType, pageLength, isCommentCount, orderBy, limitStart,date);
    }

    public LiveData<List<List<String>>> getDeliveryNotes() {
        return deliveryNoteRepo.getStockEntriesList();
    }

    public LiveData<List<List<String>>> searchDeliveryNotes() {
        return deliveryNoteRepo.getSearchedStockEntriesList();
    }

    public void clearList() {
        deliveryNoteRepo.clearList();
    }

}