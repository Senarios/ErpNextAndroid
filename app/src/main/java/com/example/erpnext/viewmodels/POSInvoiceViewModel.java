package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.network.serializers.response.PosInvoiceResponse;
import com.example.erpnext.repositories.POSInvoicesRepo;

import java.util.List;

public class POSInvoiceViewModel extends ViewModel {
    private MutableLiveData<List<List<String>>> reportViewList;
    private POSInvoicesRepo repo;

    public void getInvoicesApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        repo = POSInvoicesRepo.getInstance();
        repo.getInvoices(docType, pageLength, isCommentCount, orderBy, limitStart);
    }

    public void getInvoiceDetails(String docType, String invoiceNo) {
        repo = POSInvoicesRepo.getInstance();
        repo.getInvoiceDetail(docType, invoiceNo);
    }

    public void changeStatus(String doctype, String name, String action) {
        repo = POSInvoicesRepo.getInstance();
        repo.changeInvoiceStatus(doctype, name, action);
    }

    public LiveData<List<List<String>>> getInvoices() {
        return repo.getInvoicesList();
    }

    public LiveData<PosInvoiceResponse> getInvoice() {
        return repo.getInvoice();
    }

    public LiveData<Boolean> isEnded() {
        return repo.getIsEnded();
    }

    public LiveData<Boolean> getInvoiceStatus() {
        return repo.getInvoiceStatus();
    }
}
