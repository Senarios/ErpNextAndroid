package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.network.serializers.response.GetPurchaseInvoiceResponse;
import com.example.erpnext.network.serializers.response.PurchaseInvoiceDetailResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddPurchaseReceiptRepo;

import org.json.JSONObject;

import java.util.List;

public class AddNewPurchaseReceiptViewModel extends ViewModel {
    AddPurchaseReceiptRepo repo;

    public AddNewPurchaseReceiptViewModel() {
        repo = AddPurchaseReceiptRepo.getInstance();
    }

    // TODO: Implement the ViewModel
    public void getSearchItemApi(String searchText, String supplier) {
        repo.getSearchedItem(searchText, supplier);
    }

    public void getPurchaseInvoiceApi(String searchText, String supplier) {
        repo.getPurchaseInvoice(searchText, supplier);
    }

    public void getInvoiceDetail(String invoiceName) {
        repo.getInvoiceDetail(invoiceName);
    }

    public void getItemDetailApi(String selectedItemCode, String supplier, String quantity) {
        repo.getItemDetail(selectedItemCode, supplier, quantity);
    }

    public void saveDocApi(JSONObject jsonObject) {
        repo.saveDoc(jsonObject);
    }

    public void getSearchLinkApi(String doctype, String query, String filters, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters);
    }

    public LiveData<List<List<String>>> getSearchedItems() {
        return repo.getSearchedItems();
    }

    public LiveData<GetPurchaseInvoiceResponse> getInvoice() {
        return repo.getInvoices();
    }

    public LiveData<SearchItemDetail> getItemDetail() {
        return repo.getItemDetail();
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public LiveData<PurchaseInvoiceDetailResponse> getInvoiceDetail() {
        return repo.getInvoiceDetail();
    }

    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }
}