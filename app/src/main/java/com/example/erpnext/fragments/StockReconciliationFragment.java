package com.example.erpnext.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
    private StockListsAdapter searchStockListsAdapter;
    private int limitStart = 0;
    private int searchlimitStart = 0;
    private StockReconciliationViewModel mViewModel;
    private boolean clear = true;
    private boolean renew = true;
    String pickedDate = "";

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
        getItems();
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
                        if (!isItemsEnded&&clear) {
                            limitStart = limitStart + 20;
                            getItems();
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
                        int tempLimit = searchlimitStart;
                        if (searchStockListsAdapter.getItemCount() >= 20)
                            searchlimitStart = searchlimitStart + 20;
                        if(tempLimit != searchlimitStart)
                            getSearchItems();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return binding.getRoot();
    }

    private void getItems() {
        mViewModel.getReconciliationItems(doctype,
                20,
                true,
                "`tabStock Reconciliation`.`modified` desc",
                limitStart);
    }
    private void getSearchItems() {
        mViewModel.getSearchReconciliationItems(doctype,
                20,
                true,
                "`tabStock Reconciliation`.`modified` desc",
                searchlimitStart,
                pickedDate);
    }

    private void setObservers() {
        mViewModel.getItems().observe(getActivity(), lists -> {
            if (lists != null) {
                binding.stockReconciliationRv.setVisibility(View.VISIBLE);
                binding.searchRv.setVisibility(View.GONE);
                setItemsAdapter(lists);
            }
        });
        mViewModel.getSearchItems().observe(requireActivity(), lists -> {
            if(lists != null){
                binding.searchRv.setVisibility(View.VISIBLE);
                binding.stockReconciliationRv.setVisibility(View.GONE);
                if(searchStockListsAdapter == null || searchStockListsAdapter.getAllItems() == null || searchStockListsAdapter.getAllItems().isEmpty() || renew) {
                    setSearchItemsAdapter(lists);
                    renew = false;
                }else searchStockListsAdapter.notifyItemRangeChanged(0, lists.size());
//                setSearchLeadsAdapter();
            }
        });
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.addNew.setOnClickListener(this);
        binding.search.setOnClickListener(this);
    }

    private void setItemsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockListsAdapter = new StockListsAdapter(getContext(), profilesList, doctype, this);
        binding.stockReconciliationRv.setLayoutManager(linearLayoutManager);
        binding.stockReconciliationRv.setAdapter(stockListsAdapter);
    }
    private void setSearchItemsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        searchStockListsAdapter = new StockListsAdapter(getContext(), profilesList, doctype, this);
        binding.searchRv.setLayoutManager(linearLayoutManager);
        binding.searchRv.setAdapter(searchStockListsAdapter);
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
            case R.id.search:
                if (clear) {
                    Utils.pickDate(requireContext(), date -> {
                        //filterTasks(date);
                        pickedDate = date;
                        binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_close_24));
                        clear = false;
                        renew = true;
                        getSearchItems();
//                        getItems("[[\"Stock Reconciliation\",\"posting_date\",\"=\",\""+pickedDate+"\"]]");

                    });
                } else {
                    binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_search_24));
                    clear = true;
                    pickedDate = "";
                    searchlimitStart = 0;
                    binding.stockReconciliationRv.setVisibility(View.VISIBLE);
                    binding.searchRv.setVisibility(View.GONE);
//                    getItems("[[\"Stock Reconciliation\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"]]");
                }
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
                getItems();
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