package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.app.MainApp;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.GenerateReportResponse;
import com.example.erpnext.network.serializers.response.GetStokeDoctypeResponse;
import com.example.erpnext.network.serializers.response.ReportResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Response;

public class StockSummRepo implements OnNetworkResponse {

    private static StockSummRepo instance;
    public MutableLiveData<Boolean> invoiceStatus = new MutableLiveData<>();
    public MutableLiveData<GetStokeDoctypeResponse> doctypeResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ReportResponse> reportResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<SearchLinkResponse> searchLinkResponseMutableLiveData = new MutableLiveData<>();
    String toDate, fromDate, warehouse, item_code;

    public static StockSummRepo getInstance() {
        if (instance == null) {
            instance = new StockSummRepo();
        }
        return instance;
    }


    public LiveData<GetStokeDoctypeResponse> getDoctype() {
        return doctypeResponseMutableLiveData;
    }

    public LiveData<ReportResponse> getReport() {
        return reportResponseMutableLiveData;
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return searchLinkResponseMutableLiveData;
    }

    public void getDocType(String docType, String name) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.DOCTYPE)
                .enque(Network.apis().getDocStoke(docType, name))
                .execute();
    }

    public void generateReport(String stock_balance, JSONObject filters) {
        try {
            toDate = filters.getString("to_date");
            fromDate = filters.getString("from_date");
            if (filters.has("warehouse")) {
                warehouse = filters.getString("warehouse");
            } else warehouse = "";
            if (filters.has("item_code")) {
                item_code = filters.getString("item_code");
            } else item_code = "";
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GENERATE_STOKE_REPORT)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity()))
                .enque(Network.apis().generateReport(stock_balance, filters))
                .execute();
    }

    public void getStokeReport(String stock_balance, JSONObject filters) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.STOKE_BALANCE_REPORT)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity()))
                .enque(Network.apis().getReport(stock_balance, filters))
                .execute();
    }

    public void getSearchLink(int requestCode, String searchDoctype, String query, String filters, String text) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching"))
                .enque(Network.apis().getSearchLink(new SearchLinkRequestBody(text, searchDoctype, "0", "")))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.DOCTYPE) {
            GetStokeDoctypeResponse res = (GetStokeDoctypeResponse) response.body();
            if (res != null && res.getDocs() != null) {
                doctypeResponseMutableLiveData.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.GENERATE_STOKE_REPORT) {
            GenerateReportResponse res = (GenerateReportResponse) response.body();
            if (res != null && res.getReport() != null && res.getReport().getName() != null) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("company", "Izat Afghan Limited");
                    jsonObject.put("from_date", fromDate);
                    jsonObject.put("to_date", toDate);
                    if (!warehouse.isEmpty()) jsonObject.put("warehouse", warehouse);
                    if (!item_code.isEmpty()) jsonObject.put("item_code", item_code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getStokeReport("Stock Balance", jsonObject);
            } else Notify.Toast("Can't generate report");
        } else if ((int) tag == RequestCodes.API.STOKE_BALANCE_REPORT) {
            ReportResponse res = (ReportResponse) response.body();
            if (res != null) {
                reportResponseMutableLiveData.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.SEARCH_WAREHOUSE || (int) tag == RequestCodes.API.SEARCH_ITEM) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }

}
