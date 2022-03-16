package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.WarehouseItem;
import com.example.erpnext.models.WarehouseItemRequestBody;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.DiffAccountResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.network.serializers.response.WarehouseItemsResponse;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.FileUpload;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AddReconciliationRepo implements OnNetworkResponse {

    private static AddReconciliationRepo instance;
    public MutableLiveData<JSONObject> savedDoc = new MutableLiveData<>();
    public MutableLiveData<List<WarehouseItem>> warehouseItems = new MutableLiveData<>();
    public MutableLiveData<String> diffAccount = new MutableLiveData<>();
    public MutableLiveData<SearchLinkResponse> searchLinkResponseMutableLiveData = new MutableLiveData<>();
    int limitSet;

    public static AddReconciliationRepo getInstance() {
        if (instance == null) {
            instance = new AddReconciliationRepo();
        }
        return instance;
    }


    public LiveData<SearchLinkResponse> getSearchResult() {
        return searchLinkResponseMutableLiveData;
    }

    public LiveData<JSONObject> getDoc() {
        return savedDoc;
    }

    public LiveData<List<WarehouseItem>> getWarehouseList() {
        return warehouseItems;
    }

    public LiveData<String> getDiffAccount() {
        return diffAccount;
    }

    public void getSearchLink(int requestCode, String searchDoctype, String query, String filters, String text) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody(text, searchDoctype, "0", filters, "Landed Cost Purchase Receipt", query)))
                .execute();
    }

    public void getWarehouseItems(String warehouse) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("warehouse", warehouse);
            jsonObject.put("posting_date", DateTime.getCurrentDate());
            jsonObject.put("posting_time", DateTime.getCurrentServerTimeOnly());
            jsonObject.put("company", "Izat Afghan Limited");

            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCodes.API.WAREHOUSE_ITEMS)
                    .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                    .enque(Network.apis().getWarehouseItems(new WarehouseItemRequestBody(warehouse, DateTime.getCurrentDate(), DateTime.getCurrentServerTimeOnly(), "Izat Afghan Limited")))
                    .execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void saveDoc(JSONObject jsonObject) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SAVE_DOC)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                .enque(Network.apis().saveDoc(FileUpload.saveDoc(jsonObject, "Submit")))
                .execute();
    }

    public void getDiffAccountApi(String purpose) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.DIFF_ACCOUNT)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                .enque(Network.apis().getDiffAccount(purpose, "Izat Afghan Limited"))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.SEARCH_WAREHOUSE) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.SAVE_DOC) {
            JSONObject res = (JSONObject) response.body();
            if (res != null) {
                savedDoc.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.WAREHOUSE_ITEMS) {
            WarehouseItemsResponse res = (WarehouseItemsResponse) response.body();
            if (res != null && res.getWarehouseList() != null) {
                warehouseItems.setValue(res.getWarehouseList());
            }
        } else if ((int) tag == RequestCodes.API.DIFF_ACCOUNT) {
            DiffAccountResponse res = (DiffAccountResponse) response.body();
            if (res != null && res.getAccount() != null) {
                diffAccount.setValue(res.getAccount());
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.Toast(response.getServerMessages());
    }


}
