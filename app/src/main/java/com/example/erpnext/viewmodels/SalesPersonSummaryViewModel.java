package com.example.erpnext.viewmodels;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.databinding.SalesPersonSummaryFragmentBinding;
import com.example.erpnext.models.Column;
import com.example.erpnext.models.ReportSummaryRes;
import com.example.erpnext.models.ResultSummary;
import com.example.erpnext.network.serializers.response.GetStokeDoctypeResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.SalesPersonSummaryRepo;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.levitnudi.legacytableview.LegacyTableView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SalesPersonSummaryViewModel extends ViewModel {
    SalesPersonSummaryRepo repo;

    public SalesPersonSummaryViewModel() {
        repo = SalesPersonSummaryRepo.getInstance();
    }

    public void getDocTypeApi(String report_name, String filter) {
//        repo = StockBalanceRepo.getInstance();
        repo.getDocType(report_name, filter);

    }

    public void getSearchLinkApi(String text, String doctype, String query, String filters, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters, text);
    }

    public void getStokeReportApi(String report,String filter) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("company", "Izat Afghan Limited");
            jsonObject.put("from_date", DateTime.getPrevMonthCurrentDate());
            jsonObject.put("to_date", DateTime.getCurrentDate());
        } catch (JSONException err) {
            Log.d("Error", err.toString());
        }
        repo.getStokeReport(report, filter);
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

    public LiveData<ReportSummaryRes> getReport() {
        return repo.getReport();
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public void initializeTable(ReportSummaryRes reportResponse, SalesPersonSummaryFragmentBinding binding, Activity activity) {
        Utils.showLoading(activity);
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
        List<ResultSummary> results = new ArrayList<>();
        List<?> list1 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            list1 = Arrays.asList(Arrays.stream(list).toArray());
        }
        List<?> linkedList = new LinkedList<String>((Collection<? extends String>) list1);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String string = gson.toJson(linkedList);
        Type type = new TypeToken<List<ResultSummary>>() {
        }.getType();
        try {
            JSONArray jsonArray = new JSONArray(string);
            jsonArray.remove(jsonArray.length()-1);
            results = gson.fromJson(jsonArray.toString(), type);
            Log.wtf("afterload", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<String> resultsList = new ArrayList<>();
        for (ResultSummary result : results) {
            if (result.getDeliveryNote() != null)
                resultsList.add(result.getDeliveryNote().toString());
            else if (result.getSalesOrder() != null)
                resultsList.add(result.getSalesOrder().toString());
            else if (result.getSalesInvoice() != null)
                resultsList.add(result.getSalesInvoice().toString());
            else resultsList.add("");
//            resultsList.add(result.getItemCode().toString());
            if (result.getCustomer() != null) resultsList.add(result.getCustomer().toString());
            else resultsList.add("");
            if (result.getTerritory() != null) resultsList.add(result.getTerritory().toString());
            else resultsList.add("");
            if (result.getPostingDate() != null)
                resultsList.add(result.getPostingDate().toString());
            else resultsList.add("");
            if (result.getAmount() != null) resultsList.add(result.getAmount().toString());
            else resultsList.add("");
            if (result.getSalesPerson() != null)
                resultsList.add(result.getSalesPerson().toString());
            else resultsList.add("");
            if (result.getContributionPercentage() != null)
                resultsList.add(result.getContributionPercentage().toString());
            else resultsList.add("");
            if (result.getCommissionRate() != null)
                resultsList.add(result.getCommissionRate().toString());
            else resultsList.add("");
            if (result.getContributionAmount() != null)
                resultsList.add(result.getContributionAmount().toString());
            else resultsList.add("");
            if (result.getIncentives() != null) resultsList.add(result.getIncentives().toString());
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
        Utils.dismiss();

    }
}

