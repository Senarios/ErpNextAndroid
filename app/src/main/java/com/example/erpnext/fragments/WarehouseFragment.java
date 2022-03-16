package com.example.erpnext.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewWarehouseActivity;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.WarehouseFragmentBinding;
import com.example.erpnext.repositories.PurchaseReceiptRepo;
import com.example.erpnext.repositories.WarehouseRepo;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.WarehouseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WarehouseFragment extends Fragment implements View.OnClickListener, ProfilesCallback {

    public boolean isProfilesEnded = false;
    String doctype = "Warehouse";
    private WarehouseViewModel mViewModel;
    private StockListsAdapter stockListsAdapter;
    private WarehouseFragmentBinding binding;
    private int limitStart = 0;

    public static WarehouseFragment newInstance() {
        return new WarehouseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(WarehouseViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = WarehouseFragmentBinding.inflate(inflater, container, false);

        setClickListeners();
        getReceipts();
        setObservers();
        binding.warehouseRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(binding.warehouseRv)) {
                        if (!isProfilesEnded) {
                            limitStart = limitStart + 20;
                            getReceipts();
                        }

                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return binding.getRoot();
    }

    private void getReceipts() {
        mViewModel.getWarehouseList(
                doctype,
                20,
                true,
                "`tabWarehouse`.`modified` desc",
                limitStart
        );
    }

    private void setObservers() {
        mViewModel.getWarehouse().observe(getActivity(), lists -> {
            if (lists != null) {
                setWarehouseAdapter(lists);
            }
        });
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.addNew.setOnClickListener(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WarehouseViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setWarehouseAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockListsAdapter = new StockListsAdapter(getContext(), profilesList, doctype, this);
        binding.warehouseRv.setLayoutManager(linearLayoutManager);
        binding.warehouseRv.setAdapter(stockListsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.add_new:
                startActivityForResult(new Intent(getActivity(), AddNewWarehouseActivity.class), RequestCodes.CREATE_NEW_WAREHOUSE);
                break;
        }
    }

    @Override
    public void onProfileClick(List<String> list) {

    }

    @Override
    public void onLongClick(List<String> list, int position) {

    }

    @Override
    public void onResume() {
        MainApp.getAppContext().setCurrentActivity(getActivity());
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        create new is pending
        if (requestCode == RequestCodes.CREATE_NEW_PURCHAS_RECEIPT) {
            if (resultCode == RESULT_OK) {
                PurchaseReceiptRepo.getInstance().purchaseReceipts.setValue(new ArrayList<>());
                limitStart = 0;
                getReceipts();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        WarehouseRepo.getInstance().warehouse.setValue(new ArrayList<>());
    }
}