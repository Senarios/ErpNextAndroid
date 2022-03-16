package com.example.erpnext.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.databinding.StockLedgerFragmentBinding;
import com.example.erpnext.models.Column;
import com.example.erpnext.models.Result;
import com.example.erpnext.network.serializers.response.GetStokeDoctypeResponse;
import com.example.erpnext.network.serializers.response.ReportResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.StockLedgerRepo;
import com.example.erpnext.utils.DateTime;
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

public class StockLedgerViewModel extends ViewModel {
    StockLedgerRepo repo;

    public StockLedgerViewModel() {
        repo = StockLedgerRepo.getInstance();
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
        repo.getStokeReport("Stock Ledger", jsonObject);
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
        repo.generateReport("Stock Ledger", jsonObject);
    }

    public LiveData<GetStokeDoctypeResponse> getDoctype() {
        return repo.getDoctype();
    }

    public LiveData<ReportResponse> getReport() {
        return repo.getReport();
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public void initializeTable(ReportResponse reportResponse, StockLedgerFragmentBinding binding) {
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
        List<Result> results = new ArrayList<>();
        List<?> list1 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            list1 = Arrays.asList(Arrays.stream(list).toArray());
        }
        List<?> linkedList = new LinkedList<String>((Collection<? extends String>) list1);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String string = gson.toJson(linkedList);
        Type type = new TypeToken<List<Result>>() {
        }.getType();
        results = gson.fromJson(string, type);

        List<String> resultsList = new ArrayList<>();
        for (Result result : results) {
            if (result.getDate() != null)
                resultsList.add(DateTime.getFormatedDateTimeString(result.getDate()));
            else resultsList.add("");
            resultsList.add(result.getItemCode().toString());
            if (result.getItemName() != null) resultsList.add(result.getItemName().toString());
            else resultsList.add("");
            if (result.getStockUom() != null) resultsList.add(result.getStockUom().toString());
            else resultsList.add("");
            if (result.getInQty() != null) resultsList.add(result.getInQty().toString());
            else resultsList.add("");
            if (result.getOutQty() != null) resultsList.add(result.getOutQty().toString());
            else resultsList.add("");
            if (result.getQtyAfterTransaction() != null)
                resultsList.add(result.getQtyAfterTransaction().toString());
            else resultsList.add("");
            if (result.getVoucherNo() != null) resultsList.add(result.getVoucherNo().toString());
            else resultsList.add("");
            if (result.getWarehouse() != null) resultsList.add(result.getWarehouse().toString());
            else resultsList.add("");
            if (result.getItemGroup() != null) resultsList.add(result.getItemGroup().toString());
            else resultsList.add("");
            if (result.getBrand() != null) resultsList.add(result.getBrand().toString());
            else resultsList.add("");
            if (result.getDescription() != null)
                resultsList.add(result.getDescription().toString());
            else resultsList.add("");
            if (result.getIncomingRate() != null)
                resultsList.add("$" + result.getIncomingRate().toString());
            else resultsList.add("");
            if (result.getValuationRate() != null)
                resultsList.add("$" + result.getValuationRate().toString());
            else resultsList.add("");
            if (result.getStockValue() != null)
                resultsList.add("$" + result.getStockValue().toString());
            else resultsList.add("");
            if (result.getVoucherType() != null)
                resultsList.add(result.getVoucherType().toString());
            else resultsList.add("");
            if (result.getVoucherNo() != null) resultsList.add(result.getVoucherNo().toString());
            else resultsList.add("");
            if (result.getBatchNo() != null) resultsList.add(result.getBatchNo().toString());
            else resultsList.add("");
            if (result.getSerialNo() != null) resultsList.add(result.getSerialNo().toString());
            else resultsList.add("");
            resultsList.add("");//batch serial no

            if (result.getProject() != null) resultsList.add(result.getProject().toString());
            else resultsList.add("");
            if (result.getCompany() != null) resultsList.add(result.getCompany().toString());
            else resultsList.add("");
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