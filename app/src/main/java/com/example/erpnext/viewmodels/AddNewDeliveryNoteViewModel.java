package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.models.PartyDetail;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.network.serializers.response.PartyDetailResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddDeliveryNoteRepo;

import org.json.JSONObject;

import java.util.List;

public class AddNewDeliveryNoteViewModel extends ViewModel {

    AddDeliveryNoteRepo repo;

    public AddNewDeliveryNoteViewModel() {
        repo = AddDeliveryNoteRepo.getInstance();
    }

    public void getSearchLinkApi(String doctype, int requestCode) {
        repo.getSearchLink(requestCode, doctype);
    }

    public void getSearchLinkWithFilterApi(String doctype, int requestCode, String query, String filter) {
        repo.getSearchLinkWithFilter(requestCode, doctype, query, filter);
    }

    public void saveDocApi(JSONObject jsonObject) {
        repo.saveDoc(jsonObject);
    }

    public void getPartyDetailsApi(String party) {
        repo.getPartyDetails(party);
    }

    public void getSearchItemApi(String searchText) {
        repo.getSearchedItem(searchText);
    }

    public void getItemDetailApi(String selectedItemCode, String quantity, PartyDetail partyDetail) {
        repo.getItemDetail(selectedItemCode, quantity, partyDetail);
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public LiveData<PartyDetailResponse> getPartyDetail() {
        return repo.getPartyDetail();
    }

    public LiveData<SearchItemDetail> getItemDetail() {
        return repo.getItemDetail();
    }

    public LiveData<List<List<String>>> getSearchedItems() {
        return repo.getSearchedItems();
    }

    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }

    public void clearData() {
        repo.clearData();
    }

}
