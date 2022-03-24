package com.example.erpnext.viewmodels;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.StockEntryRepo;

import java.util.List;

public class StockEntryViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private StockEntryRepo stockEntryRepo;

    public StockEntryViewModel() {
        stockEntryRepo = StockEntryRepo.getInstance();
    }

    public void getStockEntriesApi(Activity activity, String docType,String filter, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        stockEntryRepo.getStockEntries(activity, docType, filter,pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getStockEntries() {
        return stockEntryRepo.getStockEntriesList();
    }

    public void clearList() {
        stockEntryRepo.clearList();
    }


}