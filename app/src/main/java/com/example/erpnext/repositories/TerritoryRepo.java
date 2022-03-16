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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class TerritoryRepo implements OnNetworkResponse {
    private static TerritoryRepo instance;
    public MutableLiveData<List<List<String>>> territory = new MutableLiveData<>();
    int limitSet;

    public static TerritoryRepo getInstance() {
        if (instance == null) {
            instance = new TerritoryRepo();
        }
        return instance;
    }

    public LiveData<List<List<String>>> getItems() {
        return territory;
    }

    public void getItemsApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        limitSet = limitStart;
        String fields = "[\"`tabTerritory`.`name`\",\"`tabTerritory`.`owner`\",\"`tabTerritory`.`creation`\",\"`tabTerritory`.`modified`\",\"`tabTerritory`.`modified_by`\",\"`tabTerritory`.`_user_tags`\",\"`tabTerritory`.`_comments`\",\"`tabTerritory`.`_assign`\",\"`tabTerritory`.`_liked_by`\",\"`tabTerritory`.`docstatus`\",\"`tabTerritory`.`parent`\",\"`tabTerritory`.`parenttype`\",\"`tabTerritory`.`parentfield`\",\"`tabTerritory`.`idx`\",\"`tabTerritory`.`territory_name`\",\"`tabTerritory`.`parent_territory`\",\"`tabTerritory`.`is_group`\",\"`tabTerritory`.`territory_manager`\"]";
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, "[[\"Territory\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"]]", pageLength, isCommentCount, orderBy, limitSet))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    if (limitSet == 0) {
                        if (territory.getValue() != null && !territory.getValue().isEmpty()) {
                            territory.getValue().clear();
                            territory.setValue(res.getReportViewMessage().getValues());
                        } else territory.setValue(res.getReportViewMessage().getValues());
//                        Room.saveReportView(res, "POS Invoice");
                    } else {
                        List<List<String>> list = new ArrayList<>();
                        list = territory.getValue();
                        if (res.getReportViewMessage().getValues() != null && !res.getReportViewMessage().getValues().isEmpty()) {
                            list.addAll(res.getReportViewMessage().getValues());
                            territory.setValue(list);
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


}
