package com.example.erpnext.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.databinding.StockSummaryFragmentBinding;
import com.example.erpnext.models.Column;
import com.example.erpnext.models.StockSummaryResult;
import com.example.erpnext.network.serializers.response.ReportResponse;
import com.example.erpnext.repositories.StockSummaryRepo;
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

public class StockSummaryViewModel extends ViewModel {
    StockSummaryRepo repo;

    public StockSummaryViewModel() {
        repo = StockSummaryRepo.getInstance();
    }

    public void generateReport(String filter) {

        JSONObject jsonObject = null;
        jsonObject = setFilter(filter, jsonObject);
        repo.generateReport(jsonObject);
    }

    @NonNull
    private JSONObject setFilter(String filter, JSONObject jsonObject) {
        if (filter.equalsIgnoreCase("Warehouse")) {
            try {
                jsonObject = new JSONObject();
                jsonObject.put("company", "Izat Afghan Limited");
                jsonObject.put("group_by", "Warehouse");
            } catch (JSONException err) {
                Log.d("Error", err.toString());
            }
        } else {
            try {
                jsonObject = new JSONObject();
                jsonObject.put("company", "Izat Afghan Limited");
            } catch (JSONException err) {
                Log.d("Error", err.toString());
            }
        }
        return jsonObject;
    }

    public LiveData<ReportResponse> getReport() {
        return repo.getReport();
    }

    public void initializeTable(ReportResponse reportResponse, StockSummaryFragmentBinding binding) {
        List<String> coloums = new ArrayList<>();
        List<String> rows = new ArrayList<>();
        for (Column column : reportResponse.getMessage().getColumns()) {

            coloums.add("" + column.getLabel() + "&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160");
        }

        LegacyTableView.insertLegacyTitle();
        //set table contents as string arrays
        binding.legacyTableView.setTitle(coloums.toArray(new String[0]));

        Object[] list = reportResponse.getMessage().getResult();
        List<String> totals = new ArrayList<>();
        List<StockSummaryResult> results = new ArrayList<>();
        List<?> list1 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            list1 = Arrays.asList(Arrays.stream(list).toArray());
        }
        List<?> linkedList = new LinkedList<String>((Collection<? extends String>) list1);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String string = gson.toJson(linkedList);
        Type type = new TypeToken<List<StockSummaryResult>>() {
        }.getType();
        results = gson.fromJson(string, type);

        List<String> resultsList = new ArrayList<>();
        for (StockSummaryResult result : results) {
            resultsList.add(result.getCompany());
            resultsList.add(result.getWarehouse());
            resultsList.add(result.getItem());
            resultsList.add(result.getDescription());
            resultsList.add(result.getCurrentQty().toString());
        }
        binding.legacyTableView.setContent(resultsList.toArray(new String[0]));
        //depending on the phone screen size default table scale is 100
        //you can change it using this method
        //binding.legacyTableView.setInitialScale(100);//default initialScale is zero (0)
//        binding.legacyTableView.setTheme(CUSTOM);
        //if you want a smaller table, change the padding setting
        binding.legacyTableView.setTablePadding(10);
        binding.legacyTableView.setBackgroundOddColor("#CABE58");
        binding.legacyTableView.setTableFooterTextColor("#CABE58");

        //to enable users to zoom in and out:
        binding.legacyTableView.setZoomEnabled(true);
        binding.legacyTableView.setShowZoomControls(true);
        //remember to build your table as the last step
        binding.legacyTableView.build();

    }

}