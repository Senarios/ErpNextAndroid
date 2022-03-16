package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.PendingInvoiceAction;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.InvoiceActionRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.PosInvoiceResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class POSInvoicesRepo implements OnNetworkResponse {
    private static POSInvoicesRepo instance;
    public MutableLiveData<Boolean> invoiceStatus = new MutableLiveData<>();
    public MutableLiveData<List<List<String>>> posInvoices = new MutableLiveData<>();
    public MutableLiveData<PosInvoiceResponse> posInvoiceResponseMutableLiveData = new MutableLiveData<>();
    int limitSet;

    public static POSInvoicesRepo getInstance() {
        if (instance == null) {
            instance = new POSInvoicesRepo();
        }
        return instance;
    }
//    public static POSInvoicesRepo getInstance() {
//        POSInvoicesRepo instance = new POSInvoicesRepo();
//        return instance;
//    }


    public void getInvoices(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        this.limitSet = (limitStart);
        getReportView(docType, new Gson().toJson(getFieldsPOSInvoices(docType)), pageLength, isCommentCount, orderBy, limitStart);
    }

    public void changeInvoiceStatus(String doctype, String names, String action) {
        if (Utils.isNetworkAvailable()) changeStatus(doctype, names, action);
        else {
            InvoiceActionRequestBody body = new InvoiceActionRequestBody();
            body.setAction(action);
            body.setDoctype(doctype);
            body.setName(names);
            PendingInvoiceAction pending = new PendingInvoiceAction();
            pending.setCancelInvoiceRequestBody(body);
            MainApp.database.pendingCancelInvoiceDao().insert(pending);
            Notify.Toast(R.string.your_invoice + body.getAction() + R.string.internet_available);
        }
    }

    public LiveData<List<List<String>>> getInvoicesList() {
        return posInvoices;
    }

    public LiveData<PosInvoiceResponse> getInvoice() {
        return posInvoiceResponseMutableLiveData;
    }

    private void getReportView(String docType, String fields, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, "[[\"POS Invoice\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"]]", pageLength, isCommentCount, orderBy, limitSet))
                .execute();
    }

    public void getInvoiceDetail(String docType, String invoiceNo) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_DOC)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Loading..."))
                .enque(Network.apis().getInvoice(docType, invoiceNo))
                .execute();
    }

    private void changeStatus(String doctype, String names, String action) {
        List<String> list = new ArrayList<>();
        list.add(names);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(new Gson().toJson(list));
            if (action.equalsIgnoreCase("cancel")) {
                NetworkCall.make()
                        .setCallback(this)
                        .setTag(RequestCodes.API.CHANGE_STATUS)
                        .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                        .enque(Network.apis().changeInvoiceStatus(doctype, action, jsonArray))
                        .execute();
            } else if (action.equalsIgnoreCase("delete")) {
                NetworkCall.make()
                        .setCallback(this)
                        .setTag(RequestCodes.API.DELETE_INVOICE)
                        .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                        .enque(Network.apis().deleteInvoice(doctype, jsonArray))
                        .execute();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    if (limitSet == 0) {
                        posInvoices.setValue(res.getReportViewMessage().getValues());
                        Room.saveReportView(res, "POS Invoice");
                    } else {
                        List<List<String>> list = new ArrayList<>();
                        list = posInvoices.getValue();
                        if (res.getReportViewMessage().getValues() != null && !res.getReportViewMessage().getValues().isEmpty()) {
                            list.addAll(res.getReportViewMessage().getValues());
                            posInvoices.setValue(list);
                            Room.saveMoreReportView(res, "POS Invoice");
                        }
                    }
                }
            }
        } else if ((int) tag == RequestCodes.API.CHANGE_STATUS) {
            JSONObject res = (JSONObject) response.body();
            if (res != null) {
                invoiceStatus.setValue(true);
            }
        } else if ((int) tag == RequestCodes.API.DELETE_INVOICE) {
            JSONObject res = (JSONObject) response.body();
            if (res != null) {
                invoiceStatus.setValue(true);
            }
        } else if ((int) tag == RequestCodes.API.GET_DOC) {
            PosInvoiceResponse res = (PosInvoiceResponse) response.body();
            if (res != null) {
                posInvoiceResponseMutableLiveData.setValue(res);
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        if ((int) tag == RequestCodes.API.CHANGE_STATUS) {
            invoiceStatus.setValue(false);
        }
        if ((int) tag == RequestCodes.API.DELETE_INVOICE) {
            invoiceStatus.setValue(false);
        }
        Notify.Toast(response.getServerMessages());
    }


    private List<String> getFieldsPOSInvoices(String docType) {
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
        fieldsList.add("`tab" + docType + "`.`" + "net_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "total_taxes_and_charges" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "discount_amount" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "grand_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "rounding_adjustment" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "rounded_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "total_advance" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "outstanding_amount" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "paid_amount" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "change_amount" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "write_off_amount" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "status" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "title" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "customer" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "customer_name" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "base_grand_total" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "due_date" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "company" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "currency" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "is_return" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_seen" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "party_account_currency" + "`");
        return fieldsList;
    }

    public LiveData<Boolean> getIsEnded() {
        return invoiceStatus;
    }

    public LiveData<Boolean> getInvoiceStatus() {
        return invoiceStatus;
    }
}
