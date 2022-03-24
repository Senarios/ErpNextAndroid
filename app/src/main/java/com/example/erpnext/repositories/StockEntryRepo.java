package com.example.erpnext.repositories;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class StockEntryRepo implements OnNetworkResponse {

    private static StockEntryRepo instance;
    public MutableLiveData<List<List<String>>> stockEntriesList = new MutableLiveData<>();
    private int limitSet;

    public static StockEntryRepo getInstance() {
        if (instance == null) {
            instance = new StockEntryRepo();
        }
        return instance;
    }

    public void getStockEntries(Activity activity, String docType, String filter, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        this.limitSet = limitStart;
        getReportView(activity, docType, new Gson().toJson(getFieldsLeads(docType)),filter, pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getStockEntriesList() {
        return stockEntriesList;
    }


    public void clearList() {
        stockEntriesList.setValue(new ArrayList<>());
    }

    private void getReportView(Activity activity, String docType, String fields, String filter,int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(activity, "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, filter, pageLength, isCommentCount, orderBy, limitSet))
                .execute();
    }

    private List<String> getFieldsLeads(String docType) {
        List<String> fieldsList = new ArrayList<>();
        fieldsList.add("`tab" + docType + "`.`" + "name" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "owner" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "creation" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "modified" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "modified_by" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_user_tags" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_comments" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_assign" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_liked_by" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "docstatus" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "parent" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "parenttype" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "parentfield" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "stock_entry_type" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "purpose" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "from_warehouse" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "to_warehouse" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "per_transferred" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "title" + "`");
        return fieldsList;
    }


    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    if (limitSet == 0) {
                        stockEntriesList.setValue(res.getReportViewMessage().getValues());
                        // TODO offline
                    } else {
                        List<List<String>> list = new ArrayList<>();
                        list = stockEntriesList.getValue();
                        if (res.getReportViewMessage().getValues() != null && !res.getReportViewMessage().getValues().isEmpty()) {
                            list.addAll(res.getReportViewMessage().getValues());
                            stockEntriesList.setValue(list);
                            // TODO offline
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }
}
