package com.example.erpnext.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.adapters.TerritoryTargetsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.models.Target;
import com.example.erpnext.network.serializers.response.DepartmentResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddSalesPersonRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class AddSalesPersonViewModel extends ViewModel {
    AddSalesPersonRepo repo;

    public AddSalesPersonViewModel() {
        repo = AddSalesPersonRepo.getInstance();
    }

    // TODO: Implement the ViewModel


    public void saveDocApi(TerritoryTargetsAdapter employeeTargetAdapter, HashMap<String, String> data) {
        try {
            JSONObject jsonDoc = new JSONObject("{\"docstatus\":0,\"doctype\":\"Sales Person\",\"name\":\"new-sales-person-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"is_group\":0,\"enabled\":1,\"targets\":[]}");
            JSONObject jsonObject = new JSONObject(String.valueOf(jsonDoc));
            jsonObject.put("sales_person_name", data.get("sales_person_name"));
            jsonObject.put("parent_sales_person", data.get("parent_sales_person"));
            jsonObject.put("commission_rate", data.get("commission_rate"));
            jsonObject.put("department", data.get("department"));
            jsonObject.put("employee", data.get("employee"));
            if (employeeTargetAdapter != null && employeeTargetAdapter.getAllItems() != null && !employeeTargetAdapter.getAllItems().isEmpty()) {
                List<Target> targets = employeeTargetAdapter.getAllItems();
                JSONArray targetArray = new JSONArray();
                for (Target target : targets) {
                    JSONObject jsonObject1 = new JSONObject("{\"docstatus\":0,\"doctype\":\"Target Detail\",\"name\":\"new-target-detail-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"parent\":\"new-sales-person-1\",\"parentfield\":\"targets\",\"parenttype\":\"Sales Person\",\"idx\":1,\"__unedited\":false}");
                    jsonObject1.put("item_group", target.getItemGroup());
                    jsonObject1.put("fiscal_year", target.getFiscalYear());
                    jsonObject1.put("target_qty", target.getTargetQty());
                    jsonObject1.put("target_amount", target.getTargetAmount());
                    jsonObject1.put("distribution_id", target.getDistributionId());
                    targetArray.put(jsonObject1);
                }
                jsonObject.putOpt("targets", targetArray);
            }
            repo.saveDoc(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getSearchLinkApi(String doctype, String query, String filters, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters);
    }

    public void getDepartment(String value) {
        repo.getDepartment(value);
    }

    public void getSearchLinkApiForDialog(String doctype, String query, String filters, int requestCode, String reference) {
        repo.getSearchLinkForDialog(requestCode, doctype, query, filters, reference);
    }


    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }


    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }

    public LiveData<DepartmentResponse> getDepartment() {
        return repo.getDepartment();
    }
}
