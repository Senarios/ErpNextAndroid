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

public class ItemPriceRepo implements OnNetworkResponse {

    private static ItemPriceRepo instance;
    public MutableLiveData<List<List<String>>> items = new MutableLiveData<>();
    int limitSet;

    public static ItemPriceRepo getInstance() {
        if (instance == null) {
            instance = new ItemPriceRepo();
        }
        return instance;
    }


    public LiveData<List<List<String>>> getItems() {
        return items;
    }

    public void getItemsApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        limitSet = limitStart;
        String fields = "[\"`tabItem Price`.`name`\",\"`tabItem Price`.`owner`\",\"`tabItem Price`.`creation`\",\"`tabItem Price`.`modified`\",\"`tabItem Price`.`modified_by`\",\"`tabItem Price`.`_user_tags`\",\"`tabItem Price`.`_comments`\",\"`tabItem Price`.`_assign`\",\"`tabItem Price`.`_liked_by`\",\"`tabItem Price`.`docstatus`\",\"`tabItem Price`.`parent`\",\"`tabItem Price`.`parenttype`\",\"`tabItem Price`.`parentfield`\",\"`tabItem Price`.`idx`\",\"`tabItem Price`.`item_code`\",\"`tabItem Price`.`item_name`\",\"`tabItem Price`.`brand`\",\"`tabItem Price`.`price_list_rate`\",\"`tabItem Price`.`reference`\",\"`tabItem Price`.`currency`\"]";
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, "[[\"Item Price\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"]]", pageLength, isCommentCount, orderBy, limitSet))
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
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
//        Notify.ToastLong(response.getServerMessages());
    }
}
