package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.app.MainApp;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.DepartmentResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.utils.FileUpload;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import org.json.JSONObject;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Response;

public class AddSalesPersonRepo implements OnNetworkResponse {
    private static AddSalesPersonRepo instance;
    public MutableLiveData<JSONObject> savedDoc = new MutableLiveData<>();
    public MutableLiveData<DepartmentResponse> departmentRes = new MutableLiveData<>();
    public MutableLiveData<SearchLinkResponse> searchLinkResponseMutableLiveData = new MutableLiveData<>();
    int limitSet;

    public static AddSalesPersonRepo getInstance() {
        if (instance == null) {
            instance = new AddSalesPersonRepo();
        }
        return instance;
    }


    public LiveData<SearchLinkResponse> getSearchResult() {
        return searchLinkResponseMutableLiveData;
    }


    public LiveData<JSONObject> getDoc() {
        return savedDoc;
    }

    public LiveData<DepartmentResponse> getDepartment() {
        return departmentRes;
    }

    public void getSearchLink(int requestCode, String searchDoctype, String query, String filters) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", searchDoctype, "1", filters, "Sales Person", query)))
                .execute();
    }

    public void getSearchLinkForDialog(int requestCode, String searchDoctype, String query, String filters, String reference) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", searchDoctype, "0", filters, reference, query)))
                .execute();
    }

    public void getDepartment(String value) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_DEPARTMENT)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                .enque(Network.apis().getDepartment(value, "Employee", "department"))
                .execute();
    }

    public void saveDoc(JSONObject jsonObject) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SAVE_DOC)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                .enque(Network.apis().saveDoc(FileUpload.saveDoc(jsonObject, "Save")))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.SEARCH_EMPLOYEE || (int) tag == RequestCodes.API.SEARCH_PARENT_SALES_PERSON
                || (int) tag == RequestCodes.API.TERRITORY_TARGETS_ITEM_GROUP || (int) tag == RequestCodes.API.TERRITORY_TARGETS_FISCAL_YEAR
                || (int) tag == RequestCodes.API.TERRITORY_TARGETS_TARGET_DISTRIBUTION) {
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
        } else if ((int) tag == RequestCodes.API.GET_DEPARTMENT) {
            DepartmentResponse res = (DepartmentResponse) response.body();
            if (res != null) {
                departmentRes.setValue(res);
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.ToastLong(response.getServerMessages());
    }

}
