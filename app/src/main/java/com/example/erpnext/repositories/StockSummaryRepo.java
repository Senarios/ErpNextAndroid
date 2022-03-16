package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.app.MainApp;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.ReportResponse;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import org.json.JSONObject;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Response;

public class StockSummaryRepo implements OnNetworkResponse {

    private static StockSummaryRepo instance;
    public MutableLiveData<ReportResponse> reportResponseMutableLiveData = new MutableLiveData<>();

    public static StockSummaryRepo getInstance() {
        if (instance == null) {
            instance = new StockSummaryRepo();
        }
        return instance;
    }

    public LiveData<ReportResponse> getReport() {
        return reportResponseMutableLiveData;
    }

    public void generateReport(JSONObject filters) {

        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GENERATE_STOCK_SUMMARY_REPORT)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity()))
                .enque(Network.apis().getReport("Total Stock Summary", filters))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.GENERATE_STOCK_SUMMARY_REPORT) {
            ReportResponse res = (ReportResponse) response.body();
            if (res != null) {
                reportResponseMutableLiveData.setValue(res);
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }
}
