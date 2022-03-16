package com.example.erpnext.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewStockEntryActivity;
import com.example.erpnext.adapters.StockEntryAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.StockEntryFragmentBinding;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.StockEntryViewModel;

import java.util.List;

public class StockEntryFragment extends Fragment implements ProfilesCallback, View.OnClickListener {

    public boolean isProfilesEnded = false;
    int limitStart = 0;
    StockEntryAdapter stockEntryAdapter;
    ActivityResultLauncher<Intent> newStockEntryLauncher;
    private StockEntryViewModel mViewModel;
    private StockEntryFragmentBinding binding;

    public StockEntryFragment() {
        // Required empty public constructor
    }

    public static StockEntryFragment newInstance() {
        return new StockEntryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = StockEntryFragmentBinding.inflate(inflater, container, false);
        setClickListeners();
        mViewModel = new ViewModelProvider(requireActivity()).get(StockEntryViewModel.class);
        if (Utils.isNetworkAvailable()) {
            getStockEntries();
            setEntriesObserver();
        }
        binding.stockEntryRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(binding.stockEntryRv)) {
                        if (!isProfilesEnded) {
                            limitStart = limitStart + 20;
                            getStockEntries();
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        newStockEntryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // There are no request codes
                    Intent data = result.getData();
                    stockEntryAdapter = null; // to get latest list
                    getStockEntries();
                });

        return binding.getRoot();
    }

    private void getStockEntries() {
        mViewModel.getStockEntriesApi(getActivity(),
                "Stock Entry",
                20,
                true,
                "`tabStock Entry`.`modified` desc",
                limitStart);
    }

    private void setEntriesObserver() {
        mViewModel.getStockEntries().observe(getActivity(), lists -> {
            if (lists != null) {
                setLeadsAdapter(lists);
            } else {
                stockEntryAdapter.addItems(lists);
                stockEntryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.addNew.setOnClickListener(this);
    }

    private void setLeadsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockEntryAdapter = new StockEntryAdapter(getContext(), profilesList, this);
        binding.stockEntryRv.setLayoutManager(linearLayoutManager);
        binding.stockEntryRv.setAdapter(stockEntryAdapter);
    }


    @Override
    public void onProfileClick(List<String> profile) {

    }

    @Override
    public void onLongClick(List<String> profile, int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.add_new:
                openNewStockEntry();
                break;
        }
    }

    @Override
    public void onResume() {
        MainApp.getAppContext().setCurrentActivity(getActivity());
        super.onResume();
    }

    public void openNewStockEntry() {
        Intent intent = new Intent(requireActivity(), AddNewStockEntryActivity.class);
        newStockEntryLauncher.launch(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.clearList();
    }
}