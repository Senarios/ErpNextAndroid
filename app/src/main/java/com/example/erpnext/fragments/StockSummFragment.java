package com.example.erpnext.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.erpnext.R;
import com.example.erpnext.activities.ScanQRActivity;
import com.example.erpnext.adapters.ItemsAdapter;
import com.example.erpnext.adapters.StockSummaryItemAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.StockBalanceFragmentBinding;
import com.example.erpnext.databinding.StockSummFragmentBinding;
import com.example.erpnext.models.CartItem;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.models.StockSummDatum;
import com.example.erpnext.models.StockSummRes;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.CompleteOrderResponse;
import com.example.erpnext.network.serializers.response.GetItemDetailResponse;
import com.example.erpnext.network.serializers.response.GetItemsResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddReconciliationRepo;
import com.example.erpnext.repositories.StockSummRepo;
import com.example.erpnext.repositories.WarehouseRepo;
import com.example.erpnext.roomDB.data.DBSearchLink;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.StockBalanceViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class StockSummFragment extends Fragment implements View.OnClickListener, OnNetworkResponse {
    private StockSummViewModel mViewModel;
    private Integer limitSet = 0;
    private boolean isItemsEnded = false, isSearchingItem = false;
    private StockSummaryItemAdapter itemsAdapter;
    StockSummFragmentBinding binding;

    public StockSummFragment() {
        // Required empty public constructor
    }

    public static StockSummFragment newInstance() {
        return new StockSummFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(StockSummViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = StockSummFragmentBinding.inflate(inflater, container, false);
        initViews();
        setClickListeners();
        getItems("");
        binding.stockSummaryRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(binding.stockSummaryRV)) {
                        if (!isItemsEnded && !isSearchingItem) {
                            limitSet = limitSet + 20;
                            getItems("");
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy);
                }
            }
        });
        return binding.getRoot();
    }

    @SuppressLint("FragmentLiveDataObserve")
    private void initViews() {
//        itemsRV = findViewById(R.id.stock_summary_RV);
//        filterWarehouseSearch = findViewById(R.id.filter_warehouse);
//        back = findViewById(R.id.back);

        binding.filterWarehouse.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi("", "Warehouse", "", null, RequestCodes.API.SEARCH_WAREHOUSE);
                mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
                    if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                        if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_WAREHOUSE) {
                            Utils.setSearchAdapter(getActivity(), binding.filterWarehouse, searchLinkResponse);
                            StockSummRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
                        }
//                        else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_ITEM) {
//                            Utils.setSearchAdapter(getActivity(), item, searchLinkResponse);
//                            AddReconciliationRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
//                        }
                    }
                });
            }
        });
        binding.filterWarehouse.setOnItemClickListener((parent, view12, position, id) -> {
//            item_group = (String) parent.getItemAtPosition(position);
            limitSet = 0;
            isItemsEnded = false;
            if (Utils.isNetworkAvailable()) getItems(binding.filterWarehouse.getText().toString());
//            else loadFromLoacal();
        });

//        customerSearch.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                if (Utils.isNetworkAvailable())
//                    getLinkSearch(RequestCodes.API.LINK_SEARCH_CUSTOMER);
//                else {
//                    DBSearchLink.load(getContext(), "Customer", "erpnext.controllers.queries.customer_query", customerSearch);
//                }
//            }
//        });
//        customerSearch.setOnItemClickListener((parent, view1, position, id) -> {
//            selectedCustomer = (String) parent.getItemAtPosition(position);
//        });
//        itemSearch.setOnFocusChangeListener((v, hasFocus) -> {
//            isSearchingItem = hasFocus;
//        });

    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StockSummViewModel.class);
        // TODO: Use the ViewModel
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
        }
    }
    private void getItems(String warehouse) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_STOCK_SUMMARY_LIST)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Loading"))
                .enque(Network.apis().getStockItems(warehouse, limitSet, "projected_qty", "asc"))
                .execute();
    }
    private void setItemsAdapter(List<StockSummDatum> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        itemsAdapter = new StockSummaryItemAdapter(getContext(), itemList);
        binding.stockSummaryRV.setLayoutManager(linearLayoutManager);
        binding.stockSummaryRV.setAdapter(itemsAdapter);
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.GET_STOCK_SUMMARY_LIST) {
            StockSummRes res = (StockSummRes) response.body();
            if (!res.getMessage().isEmpty()) {
                List<StockSummDatum> itemList = new ArrayList<>();
                for (StockSummDatum cartItem1 : res.getMessage()) {
                    itemList.add(cartItem1);
                }
                if (itemList != null && !itemList.isEmpty()) {
                    if (limitSet == 0) {
                        setItemsAdapter(itemList);
//                        Room.savePOSItems(res, item_group, profileDoc);
                    } else {
//                        itemsAdapter.addItem(res.getItemMessage().getCartItemList());
//                        Room.saveMorePOSItems(res, item_group);
                    }
                }
            } else {
//                isItemsEnded = true;
//                setItemsAdapter(new ArrayList<>());
            }
//            if (itemsAdapter != null && itemsAdapter.getAllItems() != null && !itemsAdapter.getAllItems().isEmpty()) {
//                itemList = itemsAdapter.getAllItems();
//            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }
}