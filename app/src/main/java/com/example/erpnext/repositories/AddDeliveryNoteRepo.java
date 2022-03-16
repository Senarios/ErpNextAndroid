package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.PartyDetail;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.PartyDetailRequestBody;
import com.example.erpnext.network.serializers.requestbody.SearchLinkRequestBody;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.PartyDetailResponse;
import com.example.erpnext.network.serializers.response.SearchItemDetailResponse;
import com.example.erpnext.network.serializers.response.SearchItemResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.FileUpload;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

public class AddDeliveryNoteRepo implements OnNetworkResponse {
    private static AddDeliveryNoteRepo instance;
    public MutableLiveData<List<List<String>>> searchedItems = new MutableLiveData<>();
    public MutableLiveData<SearchItemDetail> itemDetails = new MutableLiveData<>();
    public MutableLiveData<JSONObject> savedDoc = new MutableLiveData<>();
    public MutableLiveData<SearchLinkResponse> searchLinkResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<PartyDetailResponse> partyDetailResponseMutableLiveData = new MutableLiveData<>();
    String postingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


    public static AddDeliveryNoteRepo getInstance() {
        if (instance == null) {
            instance = new AddDeliveryNoteRepo();
        }
        return instance;
    }

    public LiveData<List<List<String>>> getSearchedItems() {
        return searchedItems;
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return searchLinkResponseMutableLiveData;
    }

    public LiveData<PartyDetailResponse> getPartyDetail() {
        return partyDetailResponseMutableLiveData;
    }

    public LiveData<SearchItemDetail> getItemDetail() {
        return itemDetails;
    }

    public LiveData<JSONObject> getDoc() {
        return savedDoc;
    }

    public void clearData() {
        itemDetails = new MutableLiveData<>();
    }


    public void getSearchLink(int requestCode, String searchDoctype) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching"))
                .enque(Network.apis().getSearchLink(new SearchLinkRequestBody("", searchDoctype, "0", "Delivery Note")))
                .execute();
    }

    public void getSearchLinkWithFilter(int requestCode, String searchDoctype, String query, String filters) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", searchDoctype, "0", filters, "Delivery Note", query)))
                .execute();
    }

    public void getPartyDetails(String party) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.PARTY_DETAILS)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "loading"))
                .enque(Network.apis().getPartyDetails(new PartyDetailRequestBody(party, "Customer", "Standard Selling", postingDate, "USD", "Izat Afghan Limited", "Delivery Note")))
                .execute();
    }

    public void getSearchedItem(String searchText) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SEARCH_ITEM)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait.."))
                .enque(Network.apis().searchItem("Item", searchText, "name", "erpnext.controllers.queries.item_query", "{\"is_sales_item\":1}"))
                .execute();
    }

    public void saveDoc(JSONObject jsonObject) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SAVE_DOC)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait..."))
                .enque(Network.apis().saveDoc(FileUpload.saveDoc(jsonObject, "Submit")))
                .execute();
    }

    public void getItemDetail(String selectedItemCode, String qty, PartyDetail partyDetail) {
        JSONObject baseObject = null;
        baseObject = getItemDetailJson(selectedItemCode, qty, partyDetail, baseObject);

        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_ITEM_DETAILS)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait.."))
                .enque(Network.apis().getSearchItemDetail(new JSONObject(), baseObject))
                .execute();
    }

    private JSONObject getItemDetailJson(String selectedItemCode, String qty, PartyDetail partyDetail, JSONObject baseObject) {
        try {
            baseObject = new JSONObject("{\"item_code\":\"09-AN\",\"barcode\":null,\"customer\":\"0772128832\",\"currency\":\"USD\",\"update_stock\":0,\"conversion_rate\":1,\"price_list\":\"Standard Selling\",\"price_list_currency\":\"USD\",\"plc_conversion_rate\":1,\"company\":\"Izat Afghan Limited\",\"is_pos\":0,\"is_return\":0,\"transaction_date\":\"2021-09-24\",\"ignore_pricing_rule\":0,\"doctype\":\"Delivery Note\",\"name\":\"new-delivery-note-2\",\"qty\":1,\"stock_uom\":\"Carton\",\"pos_profile\":\"\",\"cost_center\":\"Main - IAL\",\"tax_category\":\"\",\"child_docname\":\"new-delivery-note-item-4\"}");
            baseObject.put("item_code", selectedItemCode);
            baseObject.put("customer", partyDetail.getCustomer());
            baseObject.put("currency", partyDetail.getCurrency());
            baseObject.put("price_list", partyDetail.getSellingPriceList());
            baseObject.put("transaction_date", DateTime.getCurrentDate());
            baseObject.put("qty", qty);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return baseObject;
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.LINK_SEARCH_CUSTOMER) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.PARTY_DETAILS) {
            PartyDetailResponse res = (PartyDetailResponse) response.body();
            if (res != null) {
                partyDetailResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.SEARCH_ITEM) {
            SearchItemResponse res = (SearchItemResponse) response.body();
            if (res != null) {
                searchedItems.setValue(res.getItems());
            }
        } else if ((int) tag == RequestCodes.API.GET_ITEM_DETAILS) {
            SearchItemDetailResponse res = (SearchItemDetailResponse) response.body();
            if (res != null && res.getItemDetail() != null) {
                itemDetails.setValue(res.getItemDetail());
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_SHIPPING_ADDRESS) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_COMPANY_ADDRESS_NAME) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_SOURCE_WAREHOUSE) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.SAVE_DOC) {
            JSONObject res = (JSONObject) response.body();
            if (res != null) {
                savedDoc.setValue(res);
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.ToastLong(response.getServerMessages());
    }
}
