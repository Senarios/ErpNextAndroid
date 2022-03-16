package com.example.erpnext.viewmodels;

import android.text.Html;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.adapters.ApplicableChargesAdapter;
import com.example.erpnext.adapters.LandedCostItemsAdapter;
import com.example.erpnext.adapters.LandedCostReceiptAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.models.LandedCostItem;
import com.example.erpnext.models.LandedCostReceipt;
import com.example.erpnext.models.Tax;
import com.example.erpnext.network.serializers.response.FetchValuesResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddLandedCostRepo;
import com.example.erpnext.utils.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AddLandedCostViewModel extends ViewModel {
    AddLandedCostRepo repo;

    public AddLandedCostViewModel() {
        repo = AddLandedCostRepo.getInstance();
    }
    // TODO: Implement the ViewModel


    public void getSearchLinkApi(String doctype, String query, String filters, String ref, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters, "", ref);
    }

    public void getFetchValuesApi(String docRecType, String selectedReceipt) {
        repo.fetchValues(docRecType, selectedReceipt, "supplier, posting_date, base_grand_total");
    }

    public void getItemsApi(LandedCostReceiptAdapter landedCostReceiptAdapter) {
        try {
            JSONObject jsonObject = new JSONObject("{\"docstatus\":0,\"doctype\":\"Landed Cost Voucher\",\"name\":\"new-landed-cost-voucher-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"naming_series\":\"MAT-LCV-.YYYY.-\",\"company\":\"Izat Afghan Limited\",\"posting_date\":\"" + DateTime.getCurrentDate() + "\",\"distribute_charges_based_on\":\"Qty\",\"purchase_receipts\":[],\"items\":[],\"taxes\":[{\"docstatus\":0,\"doctype\":\"Landed Cost Taxes and Charges\",\"name\":\"new-landed-cost-taxes-and-charges-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"account_currency\":\"USD\",\"parent\":\"new-landed-cost-voucher-1\",\"parentfield\":\"taxes\",\"parenttype\":\"Landed Cost Voucher\",\"idx\":1,\"exchange_rate\":0,\"amount\":0,\"base_amount\":0}],\"idx\":0,\"total_taxes_and_charges\":0}");
            JSONArray purchaseItems = new JSONArray();
            for (LandedCostReceipt landedCostReceipt : landedCostReceiptAdapter.getAllItems()) {
                JSONObject jsonOBJ = new JSONObject("{\"docstatus\":0,\"doctype\":\"Landed Cost Purchase Receipt\",\"name\":\"new-landed-cost-purchase-receipt-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"receipt_document_type\":\"Purchase Receipt\",\"parent\":\"new-landed-cost-voucher-1\",\"parentfield\":\"purchase_receipts\",\"parenttype\":\"Landed Cost Voucher\",\"idx\":1,\"supplier\":\"ABC\",\"grand_total\":2000,\"receipt_document\":\"MAT-PRE-2021-00049\"}");
                jsonOBJ.put("supplier", landedCostReceipt.getSupplier());
                jsonOBJ.put("grand_total", landedCostReceipt.getGrand_total());
                jsonOBJ.put("receipt_document", landedCostReceipt.getInvoiceNo());
                purchaseItems.put(jsonOBJ);
            }
            jsonObject.putOpt("purchase_receipts", purchaseItems);
            repo.getItems(jsonObject, "get_items_from_purchase_receipts");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getDiffAccount(String purpose) {
        repo.getDiffAccountApi(purpose);
    }

    public void saveDocApi(LandedCostReceiptAdapter landedCostReceiptAdapter, LandedCostItemsAdapter landedCostItemsAdapter, ApplicableChargesAdapter applicableChargesAdapter, float totalCharges) {
        try {
            JSONObject jsonDoc = new JSONObject("{\"docstatus\":0,\"doctype\":\"Landed Cost Voucher\",\"name\":\"new-landed-cost-voucher-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"naming_series\":\"MAT-LCV-.YYYY.-\",\"company\":\"Izat Afghan Limited\",\"posting_date\":\"" + DateTime.getCurrentDate() + "\",\"distribute_charges_based_on\":\"Qty\",\"purchase_receipts\":[],\"items\":[],\"taxes\":[],\"total_taxes_and_charges\":0,\"idx\":0}");
            jsonDoc.put("total_taxes_and_charges", totalCharges);
            List<LandedCostReceipt> purchaseReceipts = landedCostReceiptAdapter.getAllItems();
            List<LandedCostItem> items = landedCostItemsAdapter.getAllItems();
            List<Tax> taxes = applicableChargesAdapter.getAllItems();
            JSONArray receiptsArray = new JSONArray();
            JSONArray itemsArray = new JSONArray();
            JSONArray taxArray = new JSONArray();
            for (LandedCostReceipt landedCostReceipt : purchaseReceipts) {
                JSONObject jsonObject1 = new JSONObject("{\"docstatus\":0,\"doctype\":\"Landed Cost Purchase Receipt\",\"name\":\"new-landed-cost-purchase-receipt-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"receipt_document_type\":\"Purchase Receipt\",\"parent\":\"new-landed-cost-voucher-1\",\"parentfield\":\"purchase_receipts\",\"parenttype\":\"Landed Cost Voucher\",\"idx\":1,\"supplier\":\"ABC\",\"posting_date\":\"\",\"grand_total\":2000,\"receipt_document\":\"MAT-PRE-2021-00049\"}");
                jsonObject1.put("supplier", landedCostReceipt.getSupplier());
                jsonObject1.put("receipt_document_type", landedCostReceipt.getReceiptDocType());
                jsonObject1.put("receipt_document", landedCostReceipt.getInvoiceNo());
                jsonObject1.put("grand_total", landedCostReceipt.getGrand_total());
                receiptsArray.put(jsonObject1);
            }
            for (LandedCostItem landedCostItem : items) {
                JSONObject jsonObject1 = new JSONObject("{\"docstatus\":0,\"doctype\":\"Landed Cost Item\",\"name\":\"new-landed-cost-voucher-2\",\"__islocal\":1,\"receipt_document_type\":\"Purchase Receipt\",\"is_fixed_asset\":0,\"parent\":\"new-landed-cost-voucher-1\",\"parentfield\":\"items\",\"parenttype\":\"Landed Cost Voucher\",\"idx\":1,\"item_code\":\"Q 1000\",\"receipt_document\":\"MAT-PRE-2021-00049\",\"qty\":200,\"rate\":10,\"amount\":2000,\"purchase_receipt_item\":\"44fbc4c64f\",\"cost_center\":\"Main - IAL\"}");
                jsonObject1.put("item_code", landedCostItem.getItemCode());
                jsonObject1.put("qty", landedCostItem.getQty());
                jsonObject1.put("receipt_document", landedCostItem.getReceiptDocument());
                jsonObject1.put("rate", landedCostItem.getRate());
                jsonObject1.put("amount", landedCostItem.getAmount());
                jsonObject1.put("description", Html.fromHtml(landedCostItem.getDescription()).toString());
                jsonObject1.put("purchase_receipt_item", landedCostItem.getPurchaseReceiptItem());
                itemsArray.put(jsonObject1);
            }
            for (Tax tax : taxes) {
                JSONObject jsonObject1 = new JSONObject("{\"docstatus\":0,\"doctype\":\"Landed Cost Taxes and Charges\",\"name\":\"new-landed-cost-taxes-and-charges-2\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"rvlirshad@gmail.com\",\"account_currency\":\"USD\",\"parent\":\"new-landed-cost-voucher-1\",\"parentfield\":\"taxes\",\"parenttype\":\"Landed Cost Voucher\",\"idx\":2,\"__unedited\":false,\"description\":\"des\",\"amount\":15,\"base_amount\":15,\"expense_account\":\"oil income - IAL\",\"exchange_rate\":1}");
                jsonObject1.put("expense_account", tax.getExpenseAccount());
                jsonObject1.put("base_amount", tax.getBaseAmount());
                jsonObject1.put("amount", tax.getAmount());
                jsonObject1.put("description", tax.getDescription());
                taxArray.put(jsonObject1);
            }
            jsonDoc.putOpt("items", itemsArray);
            jsonDoc.putOpt("purchase_receipts", receiptsArray);
            jsonDoc.putOpt("taxes", taxArray);
            repo.saveDoc(jsonDoc);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public LiveData<String> getDiffAccount() {
        return repo.getDiffAccount();
    }

    public LiveData<List<LandedCostItem>> getItems() {
        return repo.getItemsList();
    }

    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }

    public LiveData<FetchValuesResponse> fetchedValues() {
        return repo.getFetchValue();
    }


}