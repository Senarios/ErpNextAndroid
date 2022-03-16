package com.example.erpnext.viewmodels;

import static com.example.erpnext.activities.AddTerritoryActivity.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.adapters.TerritoryTargetsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.models.Target;
import com.example.erpnext.network.serializers.response.GetPurchaseInvoiceResponse;
import com.example.erpnext.network.serializers.response.PurchaseInvoiceDetailResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddTerritoryRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AddTerritoryViewModel extends ViewModel {
    AddTerritoryRepo repo;

    public AddTerritoryViewModel() {
        repo = AddTerritoryRepo.getInstance();
    }

    // TODO: Implement the ViewModel
    public void getSearchItemApi(String searchText, String supplier) {
        repo.getSearchedItem(searchText, supplier);
    }

    public void getPurchaseInvoiceApi(String searchText, String supplier) {
        repo.getPurchaseInvoice(searchText, supplier);
    }

    public void getInvoiceDetail(String invoiceName) {
        repo.getInvoiceDetail(invoiceName);
    }

    public void getItemDetailApi(String selectedItemCode, String supplier, String quantity) {
        repo.getItemDetail(selectedItemCode, supplier, quantity);
    }

    public void saveDocApi(TerritoryTargetsAdapter territoryTargetsAdapter) {
        try {
            JSONObject jsonDoc = new JSONObject("{\"docstatus\":0,\"doctype\":\"Territory\",\"name\":\"new-territory-4\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"is_group\":0,\"territory_name\":\"dfad\",\"targets\":[],\"territory_manager\":\"Mohammad Shafiq\"}");
            JSONObject jsonObject = new JSONObject(String.valueOf(jsonDoc));
            jsonObject.put("territory_name", data.get("territory_name"));
            jsonObject.put("parent_territory", data.get("parent_territory"));
            jsonObject.put("territory_manager", data.get("territory_manager"));
            List<Target> targets = territoryTargetsAdapter.getAllItems();
            JSONArray targetArray = new JSONArray();
            for (Target target : targets) {
                JSONObject jsonObject1 = new JSONObject("{\"docstatus\":0,\"doctype\":\"Target Detail\",\"name\":\"new-target-detail-5\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"rvlirshad@gmail.com\",\"fiscal_year\":\"2021-2022\",\"parent\":\"new-territory-4\",\"parentfield\":\"targets\",\"parenttype\":\"Territory\",\"idx\":1,\"__unedited\":false,\"target_amount\":45,\"distribution_id\":\"Test Distribution\"}");
                jsonObject1.put("item_group", target.getItemGroup());
                jsonObject1.put("fiscal_year", target.getFiscalYear());
                jsonObject1.put("target_qty", target.getTargetQty());
                jsonObject1.put("target_amount", target.getTargetAmount());
                jsonObject1.put("distribution_id", target.getDistributionId());
                targetArray.put(jsonObject1);
            }
            jsonObject.putOpt("targets", targetArray);
            repo.saveDoc(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getSearchLinkApi(String doctype, String query, String filters, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters);
    }

    public void getSearchLinkApiForDialog(String doctype, String query, String filters, int requestCode, String reference) {
        repo.getSearchLinkForDialog(requestCode, doctype, query, filters, reference);
    }

    public LiveData<List<List<String>>> getSearchedItems() {
        return repo.getSearchedItems();
    }

    public LiveData<GetPurchaseInvoiceResponse> getInvoice() {
        return repo.getInvoices();
    }

    public LiveData<SearchItemDetail> getItemDetail() {
        return repo.getItemDetail();
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public LiveData<PurchaseInvoiceDetailResponse> getInvoiceDetail() {
        return repo.getInvoiceDetail();
    }

    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }

}
