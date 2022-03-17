package com.example.erpnext.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.erpnext.R;
import com.example.erpnext.adapters.StockEntryItemsAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.StockEntryCallback;
import com.example.erpnext.databinding.ActivityAddStockEntryBinding;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.models.StockEntryItem;
import com.example.erpnext.models.StockEntryOfflineModel;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkRequestBody;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

public class AddNewStockEntryActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkResponse, StockEntryCallback {
    public static HashMap<String, String> data = new HashMap<String, String>();
    private final List<StockEntryItem> stockEntryItemsList = new ArrayList<>();
    ActivityAddStockEntryBinding binding;
    EditText quantity;
    StockEntryItemsAdapter stockEntryItemsAdapter;
    String postingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String postingTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    ImageView search;
    private Dialog dialog;
    private AutoCompleteTextView sourceWarehouseLink, targetWarehouseLink, itemCodeLink;
    private Button add;
    private JSONObject offlineObject;
    private String offlineId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_stock_entry);
        setClickListeners();
        setTextViews();
        setItemsAdapter(stockEntryItemsList);
        offlineId = String.valueOf(new Random().nextInt());
    }

    private void setTextViews() {
        binding.stockEntrySeries.setText("MAT-STE-.YYYY.-");
        binding.postingDateEdit.setText(postingDate);
        binding.postinTimeEdit.setText(postingTime);
    }

    private void setClickListeners() {
        binding.addItemBtn.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.stockEntryType.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                getLinkSearchForStockEntry();
            }
        });
        binding.save.setOnClickListener(v -> {
            if (fieldsCheck()) {
                setDoc();
            }
        });
    }

    private boolean fieldsCheck() {
        if (binding.stockEntryType.getText().toString().isEmpty()) {
            Notify.Toast(getString(R.string.enter_stock_entry_type));
            return false;
        } else if (stockEntryItemsAdapter.getItemCount() == 0) {
            Notify.Toast(getString(R.string.one_item_must_include));
            return false;
        }
        return true;
    }

    private void setDoc() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject("{\"docstatus\":0,\"doctype\":\"Stock Entry\",\"name\":\"new-stock-entry-1\",\"__islocal\":1,\"__unsaved\":1,\"naming_series\":\"MAT-STE-.YYYY.-\",\"purpose\":\"Material Transfer\",\"add_to_transit\":0,\"company\":\"Izat Afghan Limited\",\"posting_date\":\"2021-09-17\",\"set_posting_time\":0,\"inspection_required\":\"1\",\"from_bom\":0,\"apply_putaway_rule\":0,\"use_multi_level_bom\":1,\"letter_head\":\"Izat Afghan\",\"is_opening\":\"No\",\"items\":[{\"s_warehouse\":\"Testing Ravail Warehouse - IAL\",\"t_warehouse\":\"khan  - IAL\",\"item_code\":\"1221\",\"qty\":\"1\"}],\"posting_time\":\"10:58:21\",\"stock_entry_type\":\"Material Transfer\"}");

            data.put("docstatus", "0");
            data.put("doctype", "Stock Entry");
            data.put("name", "new-stock-entry-1");
            data.put("__islocal", "1");
            data.put("__unsaved", "1");
            data.put("naming_series", binding.stockEntrySeries.getText().toString());
            data.put("purpose", binding.stockEntryType.getText().toString());
            data.put("add_to_transit", "0");
            data.put("company", "Izat Afghan Limited");
            data.put("posting_date", binding.postingDateEdit.getText().toString());
            data.put("set_posting_time", "0");
            data.put("apply_putaway_rule", "0");
            data.put("use_multi_level_bom", "1");
            data.put("letter_head", "Izat Afghan");
            data.put("is_opening", "No");
            data.put("posting_time", binding.postinTimeEdit.getText().toString());
            data.put("stock_entry_type", binding.stockEntryType.getText().toString());
            Gson gson = new Gson();
            JSONArray jsonArray = new JSONArray(gson.toJson(stockEntryItemsList));
            jsonObject.putOpt("items", jsonArray);
            if (binding.inspectionRequiredCheck.isChecked()) {
                jsonObject.put("inspection_required", 1);
            } else {
                jsonObject.put("inspection_required", 0);
            }
            saveDoc(jsonObject);

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.add_item_btn:
                showAddNewItemDialog();
                break;

        }
    }

    private void setItemsAdapter(List<StockEntryItem> itemsList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        stockEntryItemsAdapter = new StockEntryItemsAdapter(this, itemsList, this);
        binding.itemsRV.setLayoutManager(linearLayoutManager);
        binding.itemsRV.setAdapter(stockEntryItemsAdapter);
    }

    private void showAddNewItemDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_new_item_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        sourceWarehouseLink = dialog.findViewById(R.id.source_warehouse);
        targetWarehouseLink = dialog.findViewById(R.id.target_warehouse);
        itemCodeLink = dialog.findViewById(R.id.item_code);
        quantity = dialog.findViewById(R.id.item_quantity);
        search = dialog.findViewById(R.id.search);
        add = dialog.findViewById(R.id.add);
        setDialogClickListeners();
    }

    private void setDialogClickListeners() {
        add.setOnClickListener(view -> {
            if (dialogChecks()) {
                stockEntryItemsAdapter.addItem(setItem());
                dialog.dismiss();
            } else {
                Notify.Toast(getString(R.string.fill_fields));
            }
        });
        sourceWarehouseLink.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                getLinkSearchWithFilters("", "Warehouse", "[[\"Warehouse\",\"company\",\"in\",[\"\",\"Izat Afghan Limited\"]],[\"Warehouse\",\"is_group\",\"=\",0]]", "", RequestCodes.API.LINK_SEARCH_SOURCE_WAREHOUSE);
            }
        });
        targetWarehouseLink.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                getLinkSearchWithFilters("", "Warehouse", "[[\"Warehouse\",\"company\",\"in\",[\"\",\"Izat Afghan Limited\"]],[\"Warehouse\",\"is_group\",\"=\",0]]", "", RequestCodes.API.LINK_SEARCH_TARGET_WAREHOUSE);
            }
        });
        itemCodeLink.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                getLinkSearchWithFilters("", "Item", "{\"is_stock_item\":1}", "erpnext.controllers.queries.item_query", RequestCodes.API.LINK_SEARCH_ITEM);
            }
        });
        search.setOnClickListener(v -> {
            if (itemCodeLink.getText().toString() != null) {
                getLinkSearchWithFilters(itemCodeLink.getText().toString(), "Item", "{\"is_stock_item\":1}", "erpnext.controllers.queries.item_query", RequestCodes.API.LINK_SEARCH_ITEM);
            }
        });

    }

    @NonNull
    private StockEntryItem setItem() {
        StockEntryItem stockEntryItem = new StockEntryItem();
        stockEntryItem.setItemCode(itemCodeLink.getText().toString());
        stockEntryItem.setQty(quantity.getText().toString());
        stockEntryItem.setSourceWarehouse(sourceWarehouseLink.getText().toString());
        stockEntryItem.setTargetWarehouse(targetWarehouseLink.getText().toString());
        return stockEntryItem;
    }

    private boolean dialogChecks() {
        if (sourceWarehouseLink.getText().toString().isEmpty() || targetWarehouseLink.getText().toString().isEmpty() || itemCodeLink.getText().toString().isEmpty() || quantity.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void getLinkSearchWithFilters(String text, String docType, String filters, String query, int tag) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(tag)
                .autoLoadingCancel(Utils.getLoading(this, "searching..."))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody(text, docType, "0", filters, "Stock Entry Detail", query)))
                .execute();
    }

    private void getLinkSearchForStockEntry() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.LINK_SEARCH_STOCK_ENTRY_TYPE)
                .autoLoadingCancel(Utils.getLoading(this, "searching..."))
                .enque(Network.apis().getSearchLink(new SearchLinkRequestBody("", "Stock Entry Type", "0", "Stock Entry")))
                .execute();
    }

    private void saveDoc(JSONObject jsonObject) {
        offlineObject = jsonObject;
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SAVE_DOC)
                .autoLoadingCancel(Utils.getLoading(this, "Please wait..."))
                .enque(Network.apis().saveDoc(jsonObject, "Submit"))
                .execute();
    }


    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.LINK_SEARCH_SOURCE_WAREHOUSE) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);
                sourceWarehouseLink.setAdapter(adapter);
                sourceWarehouseLink.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_TARGET_WAREHOUSE) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);
                targetWarehouseLink.setAdapter(adapter);
                targetWarehouseLink.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_ITEM) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);
                itemCodeLink.setAdapter(adapter);
                itemCodeLink.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_STOCK_ENTRY_TYPE) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);
                binding.stockEntryType.setAdapter(adapter);
                binding.stockEntryType.showDropDown();
            }
        } else if ((int) tag == RequestCodes.API.SAVE_DOC) {
            if (response.code() == 200) {
                Intent intent = new Intent();
                intent.putExtra("saveStockEntry", true);
                setResult(RESULT_OK, intent);
                Notify.Toast(getString(R.string.successfully_created));
                finish();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

//        try {
//            JSONObject jsonObject = new JSONObject(Html.fromHtml(response.getServerMessages()).toString());
//            Notify.ToastLong((String) jsonObject.get("message"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        if (response.getCode() != 0) {
            Notify.Toast(response.getServerMessages());
        } else if((int) tag == RequestCodes.API.SAVE_DOC){
            Notify.Toast(getString(R.string.offline_save));
            StockEntryOfflineModel model = new StockEntryOfflineModel();
            model.setEntryId(offlineId);
            model.setData(offlineObject.toString());
            MainApp.database.stockEntryDao().insertStockEntry(model);
            Intent intent = new Intent();
            intent.putExtra("saveStockEntry", false);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onDeleteItemClick(int position) {
        stockEntryItemsAdapter.deleteItem(position);
    }
}