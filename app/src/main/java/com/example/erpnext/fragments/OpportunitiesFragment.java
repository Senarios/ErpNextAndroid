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
import com.example.erpnext.activities.AddOpportunityActivity;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.OpportunitiesFragmentBinding;
import com.example.erpnext.repositories.OpportunitiesRepo;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.OpportunitiesViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OpportunitiesFragment extends Fragment implements View.OnClickListener, ProfilesCallback {
    public boolean isItemsEnded = false;
    String doctype = "Opportunity";
    OpportunitiesFragmentBinding binding;
    private StockListsAdapter stockListsAdapter;
    private StockListsAdapter stockSearchedListsAdapter;
    private int limitStart = 0;
    private int searchLimitStart = 0;
    private OpportunitiesViewModel mViewModel;
    private boolean clear = true;
    private boolean renew = true;
    String pickedDate = "";

    public static OpportunitiesFragment newInstance() {
        return new OpportunitiesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(OpportunitiesViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = OpportunitiesFragmentBinding.inflate(inflater, container, false);

        setClickListeners();
        getItems();
        setObservers();
        binding.listRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(binding.listRv)) {
                        if (!isItemsEnded && stockListsAdapter != null && stockListsAdapter.getAllItems() != null && stockListsAdapter.getAllItems().size() > 10) {
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
                        int tempLimit = searchLimitStart;
                            if (stockSearchedListsAdapter.getItemCount() >= 20)
                                searchLimitStart = searchLimitStart + 20;
                        if(tempLimit != searchLimitStart)
                            searchItems();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return binding.getRoot();
    }

    private void getItems() {
        mViewModel.getLandedCostList(doctype,
                20,
                true,
                "`tabOpportunity`.`modified` desc",
                limitStart);
    }

    private void searchItems() {
        mViewModel.getSearchedLandedCostList(doctype,
                20,
                true,
                "`tabOpportunity`.`modified` desc",
                searchLimitStart,
                pickedDate);
    }

    private void setObservers() {
        mViewModel.getItems().observe(getActivity(), lists -> {
            if (lists != null) {
                binding.listRv.setVisibility(View.VISIBLE);
                binding.searchRv.setVisibility(View.GONE);
                setItemsAdapter(lists);
            }
        });
        mViewModel.getSearchedItems().observe(requireActivity(), lists -> {
            if(lists != null){
                binding.searchRv.setVisibility(View.VISIBLE);
                binding.listRv.setVisibility(View.GONE);
                if(stockSearchedListsAdapter == null || stockSearchedListsAdapter.getAllItems() == null || stockSearchedListsAdapter.getAllItems().isEmpty() || renew) {
                    setSearchedItemsAdapter(lists);
                    renew = false;
                }else stockSearchedListsAdapter.notifyItemRangeChanged(0, lists.size());
//                setSearchedItemsAdapter(lists);
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
        binding.listRv.setLayoutManager(linearLayoutManager);
        binding.listRv.setAdapter(stockListsAdapter);
    }

    private void setSearchedItemsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockSearchedListsAdapter = new StockListsAdapter(getContext(), profilesList, doctype, this);
        binding.searchRv.setLayoutManager(linearLayoutManager);
        binding.searchRv.setAdapter(stockSearchedListsAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.add_new:
                startActivityForResult(new Intent(getActivity(), AddOpportunityActivity.class), RequestCodes.ADD_OPPORTUNITY);
                break;
            case R.id.search:
                if (clear) {
                    Utils.pickDate(requireContext(), date -> {
                        //filterTasks(date);
                        pickedDate = date;
                        binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_close_24));
                        clear = false;
                        renew = true;
                        searchItems();

                    });
                } else {
                    binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_search_24));
                    clear = true;
                    pickedDate = "";
                    searchLimitStart = 0;
                    binding.listRv.setVisibility(View.VISIBLE);
                    binding.searchRv.setVisibility(View.GONE);

//                    getInvoices();
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
        if (requestCode == RequestCodes.ADD_OPPORTUNITY) {
            if (resultCode == RESULT_OK) {
                OpportunitiesRepo.getInstance().items.setValue(new ArrayList<>());
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
        OpportunitiesRepo.getInstance().items.setValue(new ArrayList<>());
    }


}