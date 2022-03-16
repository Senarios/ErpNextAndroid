package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.PurchaseReceiptRepo;

import java.util.List;

public class PurchaseReceiptViewModel extends ViewModel {
    PurchaseReceiptRepo repo;

    public PurchaseReceiptViewModel() {
        repo = PurchaseReceiptRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getPurchaseList(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo.getPurchaseReceipts(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getPurchaseReceipts() {
        return repo.getPurchaseReceipts();
    }
}