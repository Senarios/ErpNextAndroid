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
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LandedCostRepo implements OnNetworkResponse {

    private static LandedCostRepo instance;
    public MutableLiveData<List<List<String>>> items = new MutableLiveData<>();
    public MutableLiveData<List<List<String>>> searchedItems = new MutableLiveData<>();
    int limitSet;
    int searchLimitSet;

    public static LandedCostRepo getInstance() {
        if (instance == null) {
            instance = new LandedCostRepo();
        }
        return instance;
    }


    public LiveData<List<List<String>>> getItems() {
        return items;
    }

    public LiveData<List<List<String>>> getSearchedItems() {
        return searchedItems;
    }

    public void getItemsApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        limitSet = limitStart;
        String fields = "[\"`tabLanded Cost Voucher`.`name`\",\"`tabLanded Cost Voucher`.`owner`\",\"`tabLanded Cost Voucher`.`creation`\",\"`tabLanded Cost Voucher`.`modified`\",\"`tabLanded Cost Voucher`.`modified_by`\",\"`tabLanded Cost Voucher`.`_user_tags`\",\"`tabLanded Cost Voucher`.`_comments`\",\"`tabLanded Cost Voucher`.`_assign`\",\"`tabLanded Cost Voucher`.`_liked_by`\",\"`tabLanded Cost Voucher`.`docstatus`\",\"`tabLanded Cost Voucher`.`parent`\",\"`tabLanded Cost Voucher`.`parenttype`\",\"`tabLanded Cost Voucher`.`parentfield`\",\"`tabLanded Cost Voucher`.`idx`\",\"`tabLanded Cost Voucher`.`company`\"]";
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, "[[\"Landed Cost Voucher\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"]]", pageLength, isCommentCount, orderBy, limitSet))
                .execute();
    }

    public void searchItemsApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart, String date) {
        searchLimitSet = limitStart;
        String fields = "[\"`tabLanded Cost Voucher`.`name`\",\"`tabLanded Cost Voucher`.`owner`\",\"`tabLanded Cost Voucher`.`creation`\",\"`tabLanded Cost Voucher`.`modified`\",\"`tabLanded Cost Voucher`.`modified_by`\",\"`tabLanded Cost Voucher`.`_user_tags`\",\"`tabLanded Cost Voucher`.`_comments`\",\"`tabLanded Cost Voucher`.`_assign`\",\"`tabLanded Cost Voucher`.`_liked_by`\",\"`tabLanded Cost Voucher`.`docstatus`\",\"`tabLanded Cost Voucher`.`parent`\",\"`tabLanded Cost Voucher`.`parenttype`\",\"`tabLanded Cost Voucher`.`parentfield`\",\"`tabLanded Cost Voucher`.`idx`\",\"`tabLanded Cost Voucher`.`company`\"]";
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SEARCH_REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, "[[\"Landed Cost Voucher\",\"posting_date\",\"=\",\"" + date + "\"],[\"Landed Cost Voucher\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"]]", pageLength, isCommentCount, orderBy, searchLimitSet))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    if (limitSet == 0) {
                        if (items.getValue() != null && !items.getValue().isEmpty()) {
                            items.getValue().clear();
                            items.setValue(res.getReportViewMessage().getValues());
                        } else items.setValue(res.getReportViewMessage().getValues());
//                        Room.saveReportView(res, "POS Invoice");
                    } else {
                        List<List<String>> list = new ArrayList<>();
                        list = items.getValue();
                        if (res.getReportViewMessage().getValues() != null && !res.getReportViewMessage().getValues().isEmpty()) {
                            list.addAll(res.getReportViewMessage().getValues());
                            items.setValue(list);
//                            Room.saveMoreReportView(res, "POS Invoice");
                        }
                    }
                }
            }
        } else if ((int) tag == RequestCodes.API.SEARCH_REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    if (searchLimitSet == 0) {
                        if (searchedItems.getValue() != null && !searchedItems.getValue().isEmpty()) {
                            searchedItems.getValue().clear();
                            searchedItems.setValue(res.getReportViewMessage().getValues());
                        } else searchedItems.setValue(res.getReportViewMessage().getValues());
//                        searchedItems.setValue(res.getReportViewMessage().getValues());
//                        Room.saveReportView(res, "POS Invoice");
                    } else {
                        List<List<String>> list = new ArrayList<>();
                        list = searchedItems.getValue();
                        if (res.getReportViewMessage().getValues() != null && !res.getReportViewMessage().getValues().isEmpty()) {
                            list.addAll(res.getReportViewMessage().getValues());
                            searchedItems.setValue(list);
//                            Room.saveMoreReportView(res, "POS Invoice");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.ToastLong(response.getServerMessages());
    }
}
