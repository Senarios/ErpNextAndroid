package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
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

public class WarehouseRepo implements OnNetworkResponse {
    private static WarehouseRepo instance;
    public MutableLiveData<List<List<String>>> warehouse = new MutableLiveData<>();
    int limitSet;

    public static WarehouseRepo getInstance() {
        if (instance == null) {
            instance = new WarehouseRepo();
        }
        return instance;
    }


    public LiveData<List<List<String>>> getWarehouse() {
        return warehouse;
    }

    public void getWarehouse(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        limitSet = limitStart;
        String fields = new Gson().toJson(getFields(docType));
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, "[[\"Warehouse\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"],[\"Warehouse\",\"company\",\"=\",\"Izat Afghan Limited\"]]", pageLength, isCommentCount, orderBy, limitSet))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage().getValues() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    if (limitSet == 0) {
                        warehouse.setValue(res.getReportViewMessage().getValues());
                        Room.saveReportView(res, "Warehouse");
                    } else {
                        List<List<String>> list = new ArrayList<>();
                        list = warehouse.getValue();
                        if (res.getReportViewMessage().getValues() != null && !res.getReportViewMessage().getValues().isEmpty()) {
                            list.addAll(res.getReportViewMessage().getValues());
                            warehouse.setValue(list);
                            Room.saveMoreReportView(res, "Warehouse");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }

    private List<String> getFields(String docType) {
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
        fieldsList.add("`tab" + docType + "`.`" + "is_group" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "company" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "disabled" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "city" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "warehouse_name" + "`");
        return fieldsList;
    }

}
