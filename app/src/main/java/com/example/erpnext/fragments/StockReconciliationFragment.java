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
import com.example.erpnext.activities.AddReconciliationActivity;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.StockReconcilationFragmentBinding;
import com.example.erpnext.repositories.ItemPriceRepo;
import com.example.erpnext.repositories.StockReconciliationRepo;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.StockReconciliationViewModel;

import java.util.ArrayList;
import java.util.List;

public class StockReconciliationFragment extends Fragment implements View.OnClickListener, ProfilesCallback {
    public boolean isItemsEnded = false;
    String doctype = "Stock Reconciliation";
    private StockReconcilationFragmentBinding binding;
    private StockListsAdapter stockListsAdapter;
    private int limitStart = 0;
    private StockReconciliationViewModel mViewModel;

    public static StockReconciliationFragment newInstance() {
        return new StockReconciliationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(StockReconciliationViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = StockReconcilationFragmentBinding.inflate(inflater, container, false);

        setClickListeners();
        getItems("[[\"Stock Reconciliation\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"]]");
        setObservers();
        binding.stockReconciliationRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(binding.stockReconciliationRv)) {
                        if (!isItemsEnded) {
                            limitStart = limitStart + 20;
                            getItems("[[\"Stock Reconciliation\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"]]");
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return binding.getRoot();
    }

    private void getItems(String filter) {
        mViewModel.getReconciliationItems(doctype,
                filter,
                20,
                true,
                "`tabStock Reconciliation`.`modified` desc",
                limitStart);
    }

    private void setObservers() {
        mViewModel.getItems().observe(getActivity(), lists -> {
            if (lists != null) {
                setItemsAdapter(lists);
            }
        });
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.addNew.setOnClickListener(this);
    }

    private void setItemsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockListsAdapter = new StockListsAdapter(getContext(), profilesList, doctype, this);
        binding.stockReconciliationRv.setLayoutManager(linearLayoutManager);
        binding.stockReconciliationRv.setAdapter(stockListsAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.add_new:
                startActivityForResult(new Intent(getActivity(), AddReconciliationActivity.class), RequestCodes.ADD_RECONCILIATION);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodes.ADD_RECONCILIATION) {
            if (resultCode == RESULT_OK) {
                ItemPriceRepo.getInstance().items.setValue(new ArrayList<>());
                limitStart = 0;
                getItems("[[\"Stock Reconciliation\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"]]");
            }
        }
    }

    @Override
    public void onResume() {
        MainApp.getAppContext().setCurrentActivity(getActivity());
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        StockReconciliationRepo.getInstance().items.setValue(new ArrayList<>());
    }
}