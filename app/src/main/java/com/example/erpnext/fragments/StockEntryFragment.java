package com.example.erpnext.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewStockEntryActivity;
import com.example.erpnext.adapters.StockEntryAdapter;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.StockEntryFragmentBinding;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.StockEntryViewModel;

import java.util.List;

public class StockEntryFragment extends Fragment implements ProfilesCallback, View.OnClickListener {

    public boolean isProfilesEnded = false;
    int limitStart = 0;
    private int searchLimitStart = 0;
    private StockEntryAdapter stockEntryAdapter;
    private StockEntryAdapter searchStockEntryAdapter;
    ActivityResultLauncher<Intent> newStockEntryLauncher;
    private StockEntryViewModel mViewModel;
    private StockEntryFragmentBinding binding;
    private boolean clear = true;
    private boolean renew = true;
    String pickedDate = "";

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
        binding.searchRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(binding.searchRv)) {
                        int tempLimit = searchLimitStart;
                        if (searchStockEntryAdapter.getItemCount() >= 20)
                            searchLimitStart = searchLimitStart + 20;
                        if(tempLimit != searchLimitStart)
                            getSearchStockEntries();
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
    private void getSearchStockEntries() {
        mViewModel.getSearchStockEntriesApi(getActivity(),
                "Stock Entry",
                20,
                true,
                "`tabStock Entry`.`modified` desc",
                searchLimitStart,
                pickedDate);
    }

    private void setEntriesObserver() {
        mViewModel.getStockEntries().observe(getActivity(), lists -> {
            if (lists != null) {
                binding.stockEntryRv.setVisibility(View.VISIBLE);
                binding.searchRv.setVisibility(View.GONE);
                setLeadsAdapter(lists);
            } else {
                stockEntryAdapter.addItems(lists);
                stockEntryAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getSearchStockEntries().observe(requireActivity(), lists -> {
            if(lists != null){
                binding.searchRv.setVisibility(View.VISIBLE);
                binding.stockEntryRv.setVisibility(View.GONE);
                if(searchStockEntryAdapter == null || searchStockEntryAdapter.getAllItems() == null || searchStockEntryAdapter.getAllItems().isEmpty() || renew) {
                    setSearchLeadsAdapter(lists);
                    renew = false;
                }else searchStockEntryAdapter.notifyItemRangeChanged(0, lists.size());
//                setSearchLeadsAdapter();
            }
        });
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.addNew.setOnClickListener(this);
        binding.search.setOnClickListener(this);
    }

    private void setLeadsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockEntryAdapter = new StockEntryAdapter(getContext(), profilesList, this);
        binding.stockEntryRv.setLayoutManager(linearLayoutManager);
        binding.stockEntryRv.setAdapter(stockEntryAdapter);
    }

    private void setSearchLeadsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        searchStockEntryAdapter = new StockEntryAdapter(getContext(), profilesList, this);
        binding.searchRv.setLayoutManager(linearLayoutManager);
        binding.searchRv.setAdapter(searchStockEntryAdapter);
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
            case R.id.search:
                if (clear) {
                    Utils.pickDate(requireContext(), date -> {
                        //filterTasks(date);
                        pickedDate = date;
                        binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_close_24));
                        clear = false;
                        renew = true;
                        getSearchStockEntries();
//                        getStockEntries("[[\"Stock Entry\",\"posting_date\",\"=\",\""+pickedDate+"\"]]");

                    });
                } else {
                    binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_search_24));
                    clear = true;
                    pickedDate = "";
                    searchLimitStart = 0;
                    binding.stockEntryRv.setVisibility(View.VISIBLE);
                    binding.searchRv.setVisibility(View.GONE);
//                    getStockEntries("[]");
                }
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