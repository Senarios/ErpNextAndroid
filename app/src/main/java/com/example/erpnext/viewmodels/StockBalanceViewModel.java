package com.example.erpnext.viewmodels;

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
import com.example.erpnext.repositories.StockBalanceRepo;
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

public class StockBalanceViewModel extends ViewModel {
    StockBalanceRepo repo;

    public StockBalanceViewModel() {
        repo = StockBalanceRepo.getInstance();
    }

    public void getDocTypeApi(String doctype, String name) {
//        repo = StockBalanceRepo.getInstance();
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

    public void initializeTable(ReportResponse reportResponse, StockBalanceFragmentBinding binding) {
        List<String> coloums = new ArrayList<>();
        List<String> rows = new ArrayList<>();
        for (Column column : reportResponse.getMessage().getColumns()) {
            coloums.add("" + column.getLabel() + "&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160");
        }
        LegacyTableView.insertLegacyTitle();
        //set table contents as string arrays
//        LegacyTableView.insertLegacyContent("2999010", "John Deer", "50", "john@example.com",
//                "332312", "Kennedy F", "33", "ken@example.com"
//                , "42343243", "Java Lover", "28", "Jlover@example.com"
//                , "4288383", "Mike Tee", "22", "miket@example.com");
        binding.legacyTableView.setTitle(coloums.toArray(new String[0]));

        Object[] list = reportResponse.getMessage().getResult();
        List<String> totals = new ArrayList<>();
        List<Result> results = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<?> list1 = Arrays.asList(Arrays.stream(list).toArray());
            List<?> linkedList = new LinkedList<String>((Collection<? extends String>) list1);
            int last = linkedList.size() - 1;
            totals = (List<String>) linkedList.get(last);
            linkedList.remove(last);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String string = gson.toJson(linkedList);
            Type type = new TypeToken<List<Result>>() {
            }.getType();
            results = gson.fromJson(string, type);
            String strins = gson.toJson(totals);
            List<String> stringList = new ArrayList<>();
            Type type1 = new TypeToken<List<String>>() {
            }.getType();
            totals = gson.fromJson(strins, type1);
        }
        List<String> resultsList = new ArrayList<>();
        for (Result result : results) {
            resultsList.add(result.getItemCode().toString());
            resultsList.add(result.getItemName().toString());
            resultsList.add(result.getItemGroup().toString());
            resultsList.add(result.getWarehouse().toString());
            resultsList.add(result.getStockUom().toString());
            resultsList.add(result.getBalQty().toString());
            resultsList.add("$" + result.getBalVal().toString());
            resultsList.add("$" + result.getOpeningQty().toString());
            resultsList.add(result.getOpeningVal().toString());
            resultsList.add(result.getInQty().toString());
            resultsList.add(result.getInVal().toString());
            resultsList.add(result.getOutQty().toString());
            resultsList.add(result.getOutVal().toString());
            resultsList.add("");
            resultsList.add(result.getReorderLevel().toString());
            resultsList.add(result.getReorderQty().toString());
            resultsList.add(result.getCompany().toString());
        }
        String string = new Gson().toJson(totals);
        List<String> stringList = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            stringList = totals.stream().map(Object::toString)
                    .collect(Collectors.toList());
        }
        for (int i = 0; i < totals.size(); i++) {
            if (!totals.get(i).equals(totals.get(0)) && totals.get(i) != null && !totals.get(i).isEmpty()) {
                if (totals.get(i).equals(totals.get(6)) || totals.get(i).equals(totals.get(8)) || totals.get(i).equals(totals.get(13))) {
                    String total = "$" + String.valueOf(Utils.round(Float.parseFloat(totals.get(i)), 2));
                    resultsList.add(total);
                } else {
                    String total = String.valueOf(Utils.round(Float.parseFloat(totals.get(i)), 2));
                    resultsList.add(total);
                }
            } else {
                resultsList.add(totals.get(i));
            }
        }
        binding.legacyTableView.setContent(resultsList.toArray(new String[0]));
        //depending on the phone screen size default table scale is 100
        //you can change it using this method
        //binding.legacyTableView.setInitialScale(100);//default initialScale is zero (0)

        //if you want a smaller table, change the padding setting
        binding.legacyTableView.setTablePadding(20);
        binding.legacyTableView.setBackgroundOddColor("#CABE58");
        binding.legacyTableView.setTableFooterTextColor("#CABE58");

        //to enable users to zoom in and out:
        binding.legacyTableView.setZoomEnabled(true);
        binding.legacyTableView.setShowZoomControls(true);
        //remember to build your table as the last step
        binding.legacyTableView.build();

    }

}