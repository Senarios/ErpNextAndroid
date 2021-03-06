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
import com.example.erpnext.activities.AddNewPurchaseReceiptActivity;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.PurchaseReceiptFragmentBinding;
import com.example.erpnext.repositories.PurchaseReceiptRepo;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.PurchaseReceiptViewModel;

import java.util.ArrayList;
import java.util.List;

public class PurchaseReceiptFragment extends Fragment implements View.OnClickListener, ProfilesCallback {

    public boolean isProfilesEnded = false;
    String doctype = "Purchase Receipt";
    private PurchaseReceiptViewModel mViewModel;
    private PurchaseReceiptFragmentBinding binding;
    private StockListsAdapter stockListsAdapter;
    private StockListsAdapter searchStockListsAdapter;
    private int limitStart = 0;
    private int searchLimitStart = 0;
    private boolean clear = true;
    private boolean renew = true;
    String pickedDate = "";

    public static PurchaseReceiptFragment newInstance() {
        return new PurchaseReceiptFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PurchaseReceiptViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = PurchaseReceiptFragmentBinding.inflate(inflater, container, false);

        setClickListeners();
        getReceipts();
        setObservers();
        binding.purchaseReceiptRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(binding.purchaseReceiptRv)) {
                        if (!isProfilesEnded) {
                            limitStart = limitStart + 20;
                            getReceipts();
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
                        if (searchStockListsAdapter.getItemCount() >= 20)
                            searchLimitStart = searchLimitStart + 20;
                        if(tempLimit != searchLimitStart)
                            getSearchReceipts();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return binding.getRoot();
    }

    private void getReceipts() {
        mViewModel.getPurchaseList(doctype,
                20,
                true,
                "`tabPurchase Receipt`.`modified` desc",
                limitStart);
    }
    private void getSearchReceipts() {
        mViewModel.getSearchPurchaseList(doctype,
                20,
                true,
                "`tabPurchase Receipt`.`modified` desc",
                searchLimitStart,
                pickedDate);
    }

    private void setObservers() {
        mViewModel.getPurchaseReceipts().observe(getActivity(), lists -> {
            if (lists != null) {
                binding.purchaseReceiptRv.setVisibility(View.VISIBLE);
                binding.searchRv.setVisibility(View.GONE);
                setPurchaseReceiptAdapter(lists);
            }
        });
        mViewModel.getSearchPurchaseReceipts().observe(requireActivity(), lists -> {
            if(lists != null){
                binding.searchRv.setVisibility(View.VISIBLE);
                binding.purchaseReceiptRv.setVisibility(View.GONE);
                if(searchStockListsAdapter == null || searchStockListsAdapter.getAllItems() == null || searchStockListsAdapter.getAllItems().isEmpty() || renew) {
                    setSearchPurchaseReceiptAdapter(lists);
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

    private void setPurchaseReceiptAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockListsAdapter = new StockListsAdapter(getContext(), profilesList, doctype, this);
        binding.purchaseReceiptRv.setLayoutManager(linearLayoutManager);
        binding.purchaseReceiptRv.setAdapter(stockListsAdapter);
    }
    private void setSearchPurchaseReceiptAdapter(List<List<String>> profilesList) {
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
                startActivityForResult(new Intent(getActivity(), AddNewPurchaseReceiptActivity.class), RequestCodes.CREATE_NEW_PURCHAS_RECEIPT);
                break;
            case R.id.search:
                if (clear) {
                    Utils.pickDate(requireContext(), date -> {
                        //filterTasks(date);
                        pickedDate = date;
                        binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_close_24));
                        clear = false;
                        renew = true;
                        getSearchReceipts();
                    });
                } else {
                    binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_search_24));
                    clear = true;
                    pickedDate = "";
                    searchLimitStart = 0;
                    binding.purchaseReceiptRv.setVisibility(View.VISIBLE);
                    binding.searchRv.setVisibility(View.GONE);
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
        if (requestCode == RequestCodes.CREATE_NEW_PURCHAS_RECEIPT) {
            if (resultCode == RESULT_OK) {
                PurchaseReceiptRepo.getInstance().purchaseReceipts.setValue(new ArrayList<>());
                limitStart = 0;
                getReceipts();
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
        PurchaseReceiptRepo.getInstance().purchaseReceipts.setValue(new ArrayList<>());
    }
}