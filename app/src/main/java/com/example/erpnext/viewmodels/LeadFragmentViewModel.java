package com.example.erpnext.viewmodels;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.repositories.LeadsRepo;

import java.util.List;

public class LeadFragmentViewModel extends ViewModel {
    private MutableLiveData<List<List<String>>> reportViewList;
    private LeadsRepo repo;

    public void getLeadsApi(Activity activity, String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo = LeadsRepo.getInstance();
        repo.getLeads(activity, docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getLeads() {
        return repo.getInvoicesList();
    }

    public LiveData<Boolean> isEnded() {
        return repo.getIsEnded();
    }


}
