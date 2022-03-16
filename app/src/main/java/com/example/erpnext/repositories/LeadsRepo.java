package com.example.erpnext.repositories;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LeadsRepo implements OnNetworkResponse {
    public static MutableLiveData<Boolean> isEnded = new MutableLiveData<>();
    private static LeadsRepo instance;
    public MutableLiveData<List<List<String>>> leadsList = new MutableLiveData<>();
    MutableLiveData<Integer> limitSet = new MutableLiveData<>();

    public static LeadsRepo getInstance() {
        if (instance == null) {
            instance = new LeadsRepo();
        }
        return instance;
    }


    public void getLeads(Activity activity, String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        this.limitSet.setValue(limitStart);
        getReportView(activity, docType, new Gson().toJson(getFieldsLeads(docType)), pageLength, isCommentCount, orderBy, limitStart);
    }

    public LiveData<List<List<String>>> getInvoicesList() {
        return leadsList;
    }

    private void getReportView(Activity activity, String docType, String fields, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(activity, "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, "[[\"Lead\",\"status\",\"=\",\"Lead\"]]", pageLength, isCommentCount, orderBy, limitSet.getValue()))
                .execute();
    }


    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    if (limitSet.getValue() == 0) {
                        leadsList.setValue(res.getReportViewMessage().getValues());
                        Room.saveReportView(res, "Lead");
                    } else {
                        List<List<String>> list = new ArrayList<>();
                        list = leadsList.getValue();
                        if (res.getReportViewMessage().getValues() != null && !res.getReportViewMessage().getValues().isEmpty()) {
                            list.addAll(res.getReportViewMessage().getValues());
                            leadsList.postValue(list);
                            Room.saveMoreReportView(res, "Lead");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

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
        fieldsList.add("`tab" + docType + "`.`" + "idx" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "company_name" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "lead_owner" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "status" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "title" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "image" + "`");
        return fieldsList;
    }

    public LiveData<Boolean> getIsEnded() {
        return isEnded;
    }
}
