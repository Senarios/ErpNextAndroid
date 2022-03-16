package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.LandedCostItem;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.FetchValuesResponse;
import com.example.erpnext.network.serializers.response.LandedCostPurchaseItemsResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
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

public class AddLandedCostRepo implements OnNetworkResponse {

    private static AddLandedCostRepo instance;
    public MutableLiveData<JSONObject> savedDoc = new MutableLiveData<>();
    public MutableLiveData<List<LandedCostItem>> items = new MutableLiveData<>();
    public MutableLiveData<FetchValuesResponse> fetchValue = new MutableLiveData<>();
    public MutableLiveData<String> diffAccount = new MutableLiveData<>();
    public MutableLiveData<SearchLinkResponse> searchLinkResponseMutableLiveData = new MutableLiveData<>();
    int limitSet;

    public static AddLandedCostRepo getInstance() {
        if (instance == null) {
            instance = new AddLandedCostRepo();
        }
        return instance;
    }


    public LiveData<SearchLinkResponse> getSearchResult() {
        return searchLinkResponseMutableLiveData;
    }

    public LiveData<JSONObject> getDoc() {
        return savedDoc;
    }

    public LiveData<List<LandedCostItem>> getItemsList() {
        return items;
    }

    public LiveData<String> getDiffAccount() {
        return diffAccount;
    }

    public LiveData<FetchValuesResponse> getFetchValue() {
        return fetchValue;
    }

    public void getSearchLink(int requestCode, String searchDoctype, String query, String filters, String text, String reference) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody(text, searchDoctype, "0", filters, reference, query)))
                .execute();
    }

    public void fetchValues(String docRecType, String selectedReceipt, String fetch) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.FETCH_VALUES)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                .enque(Network.apis().fetchValues(selectedReceipt, docRecType, fetch))
                .execute();
    }

    public void getItems(JSONObject jsonObject, String method) {
        JSONObject jsonObject1 = new JSONObject();
        try {
//            jsonObject1.put("docs",jsonObject1);
            jsonObject1.put("method", method);

            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCodes.API.LANDED_COST_ITEMS)
                    .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                    .enque(Network.apis().getLandedCostItems(FileUpload.runDoc(jsonObject, method)))
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
        if ((int) tag == RequestCodes.API.DOC_RECEIPT) {
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
        } else if ((int) tag == RequestCodes.API.LANDED_COST_ITEMS) {
            LandedCostPurchaseItemsResponse res = (LandedCostPurchaseItemsResponse) response.body();
            if (res != null && res.getDocs() != null && !res.getDocs().isEmpty() && res.getDocs().get(0).getItems() != null) {
                items.setValue(res.getDocs().get(0).getItems());
            }
        } else if ((int) tag == RequestCodes.API.EXPENSE_ACCOUNT) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.FETCH_VALUES) {
            FetchValuesResponse res = (FetchValuesResponse) response.body();
            if (res != null && res.getFetchValues() != null) {
                fetchValue.setValue(res);
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.Toast(response.getServerMessages());
    }


}
