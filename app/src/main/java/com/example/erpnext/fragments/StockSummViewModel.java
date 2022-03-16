package com.example.erpnext.fragments;

import android.os.Build;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.databinding.StockBalanceFragmentBinding;
import com.example.erpnext.models.Column;
import com.example.erpnext.models.Result;
import com.example.erpnext.network.serializers.response.GetStokeDoctypeResponse;
import com.example.erpnext.network.serializers.response.ReportResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.StockSummRepo;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.levitnudi.legacytableview.LegacyTableView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StockSummViewModel extends ViewModel {
    StockSummRepo repo;

    public StockSummViewModel() {
        repo = StockSummRepo.getInstance();
    }

    public void getDocTypeApi(String doctype, String name) {
//        repo = StockSummRepo.getInstance();
        repo.getDocType(doctype, name);

    }

    public void getSearchLinkApi(String text, String doctype, String query, String filters, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters, text);
    }

    public void getStokeReportApi() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("company", "Izat Afghan Limited");
            jsonObject.put("from_date", DateTime.getPrevMonthCurrentDate());
            jsonObject.put("to_date", DateTime.getCurrentDate());
        } catch (JSONException err) {
            Log.d("Error", err.toString());
        }
        repo.getStokeReport("Stock Balance", jsonObject);
    }

    public void generateReport(String toDate, String fromDate, String warehouse, String itemCode) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("company", "Izat Afghan Limited");
            jsonObject.put("from_date", fromDate);
            jsonObject.put("to_date", toDate);
            if (warehouse != null && !warehouse.isEmpty()) {
                jsonObject.put("warehouse", warehouse);
            }
            if (itemCode != null && !itemCode.isEmpty()) {
                jsonObject.put("item_code", itemCode);
            }
        } catch (JSONException err) {
            Log.d("Error", err.toString());
        }
        repo.generateReport("Stock Balance", jsonObject);
    }

    public LiveData<GetStokeDoctypeResponse> getDoctype() {
        return repo.getDoctype();
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public LiveData<ReportResponse> getReport() {
        return repo.getReport();
    }

}