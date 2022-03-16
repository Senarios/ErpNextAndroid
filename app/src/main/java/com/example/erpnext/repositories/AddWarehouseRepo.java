package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.GetPurchaseInvoiceResponse;
import com.example.erpnext.network.serializers.response.PurchaseInvoiceDetailResponse;
import com.example.erpnext.network.serializers.response.SearchItemDetailResponse;
import com.example.erpnext.network.serializers.response.SearchItemResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.FileUpload;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AddWarehouseRepo implements OnNetworkResponse {
    private static AddWarehouseRepo instance;
    public MutableLiveData<List<List<String>>> searchedItems = new MutableLiveData<>();
    public MutableLiveData<SearchItemDetail> itemDetails = new MutableLiveData<>();
    public MutableLiveData<GetPurchaseInvoiceResponse> purchaseInvoices = new MutableLiveData<>();
    public MutableLiveData<JSONObject> savedDoc = new MutableLiveData<>();
    public MutableLiveData<PurchaseInvoiceDetailResponse> invoiceDetail = new MutableLiveData<>();
    public MutableLiveData<SearchLinkResponse> searchLinkResponseMutableLiveData = new MutableLiveData<>();
    int limitSet;

    public static AddWarehouseRepo getInstance() {
        if (instance == null) {
            instance = new AddWarehouseRepo();
        }
        return instance;
    }


    public LiveData<List<List<String>>> getSearchedItems() {
        return searchedItems;
    }

    public LiveData<GetPurchaseInvoiceResponse> getInvoices() {
        return purchaseInvoices;
    }

    public LiveData<SearchItemDetail> getItemDetail() {
        return itemDetails;
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return searchLinkResponseMutableLiveData;
    }

    public LiveData<PurchaseInvoiceDetailResponse> getInvoiceDetail() {
        return invoiceDetail;
    }

    public LiveData<JSONObject> getDoc() {
        return savedDoc;
    }

    public void getSearchedItem(String searchText, String supplier) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SEARCH_ITEM)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait.."))
                .enque(Network.apis().searchItem("Item", searchText, "name", "erpnext.controllers.queries.item_query", "{\"supplier\":\"" + supplier + "\",\"is_purchase_item\":1}"))
                .execute();
    }

    public void getPurchaseInvoice(String searchText, String supplier) {

        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.PURCHASE_INVOICE)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait.."))
                .enque(Network.apis().getPurchaseInvoice("Purchase Invoice", searchText, "[\"supplier\"]", 21, 0, "{\"docstatus\":1,\"per_received\":[\"<\",100],\"company\":\"Izat Afghan Limited\",\"supplier\":\"" + supplier + "\"}"))
                .execute();
    }

    public void getSearchLink(int requestCode, String searchDoctype, String query, String filters) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", searchDoctype, "0", filters, "Warehouse", query)))
                .execute();
    }

    public void getInvoiceDetail(String invoiceName) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(0, invoiceName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = null;
        try {
            jsonObject1 = new JSONObject("{\"docstatus\":0,\"doctype\":\"Purchase Receipt\",\"name\":\"new-purchase-receipt-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"rvlirshad@gmail.com\",\"naming_series\":\"MAT-PRE-.YYYY.-\",\"company\":\"Izat Afghan Limited\",\"posting_date\":\"2021-09-28\",\"set_posting_time\":0,\"apply_putaway_rule\":0,\"is_return\":0,\"currency\":\"USD\",\"price_list_currency\":\"PKR\",\"disable_rounded_total\":0,\"status\":\"Draft\",\"is_internal_supplier\":0,\"letter_head\":\"Izat Afghan\",\"group_same_items\":0,\"__run_link_triggers\":1,\"supplier_name\":\"ABC\",\"supplier\":\"ABC\",\"idx\":0,\"conversion_rate\":1,\"per_billed\":0,\"per_returned\":0,\"pricing_rules\":[],\"supplied_items\":[],\"taxes\":[],\"__onload\":{\"load_after_mapping\":true}}");
            jsonObject1.put("owner", AppSession.get("email"));
            jsonObject1.put("posting_date", DateTime.getCurrentDate());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.INVOICE_DETAIL)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching"))
                .enque(Network.apis().purchaseInvoiceDetail("erpnext.accounts.doctype.purchase_invoice.purchase_invoice.make_purchase_receipt", jsonArray, jsonObject1))
                .execute();
    }

    public void getItemDetail(String selectedItemCode, String supplier, String qty) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject("{\"item_code\":\"" + selectedItemCode + "\",\"supplier\":\"" + supplier + "\",\"currency\":\"USD\",\"update_stock\":0,\"conversion_rate\":1,\"price_list\":\"Standard Buying\",\"price_list_currency\":\"USD\",\"plc_conversion_rate\":1,\"company\":\"Izat Afghan Limited\",\"is_pos\":0,\"is_return\":0,\"is_subcontracted\":\"No\",\"" + DateTime.getCurrentDate() + "\":\"2021-09-16\",\"ignore_pricing_rule\":0,\"doctype\":\"Purchase Receipt\",\"name\":\"new-purchase-receipt-1\",\"qty\":" + qty + "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_ITEM_DETAILS)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Please wait.."))
                .enque(Network.apis().getSearchItemDetail(new JSONObject(), jsonObject))
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
        if ((int) tag == RequestCodes.API.SEARCH_ITEM) {
            SearchItemResponse res = (SearchItemResponse) response.body();
            if (res != null) {
                searchedItems.setValue(res.getItems());
            }
        } else if ((int) tag == RequestCodes.API.WAREHOUSE_TYPE) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.PARENT_WAREHOUSE) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.DEFAULT_IN_TRANSIT_WAREHOUSE) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.COMPANY) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.ACCOUNT_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.GET_ITEM_DETAILS) {
            SearchItemDetailResponse res = (SearchItemDetailResponse) response.body();
            if (res != null && res.getItemDetail() != null) {
                itemDetails.setValue(res.getItemDetail());
            }
        } else if ((int) tag == RequestCodes.API.SUPPLIER_LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.SUPPLIER_ADDRESS_LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.SUPPLIER_DELEIVERY_LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.SUPPLIER_BILLING_ADDRESS_LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.postValue(res);
            }
        } else if ((int) tag == RequestCodes.API.SUPPLIER_SHIPPING_ADDRESS_LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.SUPPLIER_CONTACT_PERSON_LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.REJECTED_WAREHOUSE_LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.ACCEPTED_WAREHOUSE_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode(0);
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.PRICE_LIST_LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.CURRENCY_LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null && res.getResults() != null) {
                res.setRequestCode((int) tag);
                searchLinkResponseMutableLiveData.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.SAVE_DOC) {
            JSONObject res = (JSONObject) response.body();
            if (res != null) {
                savedDoc.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.PURCHASE_INVOICE) {
            GetPurchaseInvoiceResponse res = (GetPurchaseInvoiceResponse) response.body();
            if (res != null) {
                purchaseInvoices.setValue(res);
            }
        } else if ((int) tag == RequestCodes.API.INVOICE_DETAIL) {
            PurchaseInvoiceDetailResponse res = (PurchaseInvoiceDetailResponse) response.body();
            if (res != null) {
                invoiceDetail.setValue(res);
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.ToastLong(response.getServerMessages());
    }


}
