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

public class DeliveryNoteRepo implements OnNetworkResponse {

    private static DeliveryNoteRepo instance;
    public MutableLiveData<List<List<String>>> deliveryNotesList = new MutableLiveData<>();
    MutableLiveData<Integer> limitSet = new MutableLiveData<>();

    public static DeliveryNoteRepo getInstance() {
        if (instance == null) {
            instance = new DeliveryNoteRepo();
        }
        return instance;
    }

    public void getDeliveryNotes(Activity activity, String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        this.limitSet.setValue(limitStart);
        getReportView(activity, docType, new Gson().toJson(getFieldsDeliveryNotes(docType)), pageLength, isCommentCount, orderBy, limitStart);
    }

    private void getReportView(Activity activity, String docType, String fields, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(activity, "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, "[[\"Delivery Note\",\"company\",\"=\",\"Izat Afghan Limited\"]]", pageLength, isCommentCount, orderBy, limitSet.getValue()))
                .execute();
    }

    public LiveData<List<List<String>>> getStockEntriesList() {
        return deliveryNotesList;
    }

    public void clearList() {
        deliveryNotesList.setValue(new ArrayList<>());
    }

    private List<String> getFieldsDeliveryNotes(String docType) {
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
        fieldsList.add("`tab" + docType + "`.`" + "idx" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "net_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "total_taxes_and_charges" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "discount_amount" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "grand_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "rounding_adjustment" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "rounded_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "status" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "per_installed" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "title" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "customer" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "customer_name" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "base_grand_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "transporter_name" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "per_billed" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "is_return" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "currency" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_seen" + "`");
        return fieldsList;
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    if (limitSet.getValue() == 0) {
                        deliveryNotesList.setValue(res.getReportViewMessage().getValues());
                        // TODO offline
                    } else {
                        List<List<String>> list = new ArrayList<>();
                        list = deliveryNotesList.getValue();
                        if (res.getReportViewMessage().getValues() != null && !res.getReportViewMessage().getValues().isEmpty()) {
                            list.addAll(res.getReportViewMessage().getValues());
                            deliveryNotesList.setValue(list);
                            // TODO offline
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
//        if ((int) tag == RequestCodes.API.REPORT_VIEW) {
//           Notify.ToastLong(response.getServerMessages());
//        }
    }
}
