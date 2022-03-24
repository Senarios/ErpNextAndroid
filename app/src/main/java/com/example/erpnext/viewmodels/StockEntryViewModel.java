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

    public void getStockEntriesApi(Activity activity, String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        stockEntryRepo.getStockEntries(activity, docType,pageLength, isCommentCount, orderBy, limitStart);
    }
    public void getSearchStockEntriesApi(Activity activity, String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart,String date) {
        stockEntryRepo.getSearchStockEntries(activity, docType,pageLength, isCommentCount, orderBy, limitStart,date);
    }

    public LiveData<List<List<String>>> getStockEntries() {
        return stockEntryRepo.getStockEntriesList();
    }
    public LiveData<List<List<String>>> getSearchStockEntries() {
        return stockEntryRepo.getSearchStockEntriesList();
    }

    public void clearList() {
        stockEntryRepo.clearList();
    }


}