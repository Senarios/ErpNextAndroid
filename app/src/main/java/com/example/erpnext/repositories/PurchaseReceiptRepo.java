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
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PurchaseReceiptRepo implements OnNetworkResponse {

    private static PurchaseReceiptRepo instance;
    public MutableLiveData<List<List<String>>> purchaseReceipts = new MutableLiveData<>();
    int limitSet;

    public static PurchaseReceiptRepo getInstance() {
        if (instance == null) {
            instance = new PurchaseReceiptRepo();
        }
        return instance;
    }


    public LiveData<List<List<String>>> getPurchaseReceipts() {
        return purchaseReceipts;
    }

    public void getPurchaseReceipts(String docType, String filter,int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        limitSet = limitStart;
        String fields = new Gson().toJson(getFields(docType));
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, filter, pageLength, isCommentCount, orderBy, limitSet))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    if (limitSet == 0) {
                        if (purchaseReceipts.getValue() != null && !purchaseReceipts.getValue().isEmpty()) {
                            purchaseReceipts.getValue().clear();
                            purchaseReceipts.setValue(res.getReportViewMessage().getValues());
                        } else purchaseReceipts.setValue(res.getReportViewMessage().getValues());
//                        Room.saveReportView(res, "POS Invoice");
                    } else {
                        List<List<String>> list = new ArrayList<>();
                        list = purchaseReceipts.getValue();
                        if (res.getReportViewMessage().getValues() != null && !res.getReportViewMessage().getValues().isEmpty()) {
                            list.addAll(res.getReportViewMessage().getValues());
                            purchaseReceipts.setValue(list);
//                            Room.saveMoreReportView(res, "POS Invoice");
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
        fieldsList.add("`tab" + docType + "`.`" + "idx" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "posting_date" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "net_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "taxes_and_charges_added" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "taxes_and_charges_deducted" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "total_taxes_and_charges" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "discount_amount" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "grand_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "rounding_adjustment" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "rounded_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "status" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "title" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "supplier" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "supplier_name" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "base_grand_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "is_subcontracted" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "transporter_name" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "is_return" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "per_billed" + "`");

        fieldsList.add("`tab" + docType + "`.`" + "currency" + "`");
        return fieldsList;
    }

}
