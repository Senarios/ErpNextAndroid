package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.app.MainApp;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.GetValuesResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.utils.FileUpload;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import org.json.JSONObject;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Response;

public class AddContactRepo implements OnNetworkResponse {
    private static AddContactRepo instance;

    public MutableLiveData<JSONObject> savedDoc = new MutableLiveData<>();
    public MutableLiveData<GetValuesResponse> value = new MutableLiveData<>();
    public MutableLiveData<SearchLinkResponse> searchLinkResponseMutableLiveData = new MutableLiveData<>();

    public static AddContactRepo getInstance() {
        if (instance == null) {
            instance = new AddContactRepo();
        }
        return instance;
    }


    public LiveData<SearchLinkResponse> getSearchResult() {
        return searchLinkResponseMutableLiveData;
    }

    public LiveData<JSONObject> getDoc() {
        return savedDoc;
    }

    public LiveData<GetValuesResponse> getValue() {
        return value;
    }

    public void getSearchLink(int requestCode, String searchDoctype, String query, String filters, String text) {
        String ref = "Contact";
        if (requestCode == RequestCodes.API.LINK_DOC_TYPE || requestCode == RequestCodes.API.LINK_NAME) {
            ref = "Dynamic Link";
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody(text, searchDoctype, "0", filters, ref, query)))
                .execute();
    }

    public void getValue(String selectedLinkName, String fieldname, String filters) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_VALUE)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                .enque(Network.apis().getValue(selectedLinkName, fieldname, filters))
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
        if ((int) tag == RequestCodes.API.SEARCH_SALUATION || (int) tag == RequestCodes.API.SEARCH_GENDER ||
                (int) tag == RequestCodes.API.SEARCH_DESIGNATION || (int) tag == RequestCodes.API.LINK_SEARCH_COMPANY_ADDRESS_NAME ||
                (int) tag == RequestCodes.API.LINK_DOC_TYPE || (int) tag == RequestCodes.API.SEARCH_SOURCH ||
                (int) tag == RequestCodes.API.SEARCH_EMAIL || (int) tag == RequestCodes.API.LINK_NAME) {
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
        } else if ((int) tag == RequestCodes.API.GET_VALUE) {
            GetValuesResponse res = (GetValuesResponse) response.body();
            if (res != null) {
                value.setValue(res);
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.ToastLong(response.getServerMessages());
    }


}
