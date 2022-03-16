package com.example.erpnext.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.StockSummaryFragmentBinding;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.StockSummaryViewModel;

import java.util.Arrays;
import java.util.List;

public class StockSummaryFragment extends Fragment {

    private final List<String> filterList = Arrays.asList("Warehouse", "Company");
    private StockSummaryFragmentBinding binding;
    private StockSummaryViewModel mViewModel;

    public static StockSummaryFragment newInstance() {
        return new StockSummaryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = StockSummaryFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(StockSummaryViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());

        setFilter();
        setClickListeners();
        setObservers();

        if (Utils.isNetworkAvailable()) {
            getReport();
        }
        return binding.getRoot();
    }

    private void getReport() {
        mViewModel.generateReport("Warehouse");
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
        binding.filter.setOnClickListener(v -> {
            binding.filter.showDropDown();
        });
        binding.filter.setOnItemClickListener((parent, view, position, id) -> {
            mViewModel.generateReport(parent.getAdapter().getItem(position).toString());
        });
    }

    private void setFilter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, filterList);
        binding.filter.setAdapter(adapter);
    }

    private void setObservers() {
        mViewModel.getReport().observe(getActivity(), reportResponse -> {
            if (reportResponse != null && reportResponse.getMessage().getColumns() != null) {
                binding.notFound.setVisibility(View.GONE);
                mViewModel.initializeTable(reportResponse, binding);

            } else binding.notFound.setVisibility(View.VISIBLE);
        });
    }

}