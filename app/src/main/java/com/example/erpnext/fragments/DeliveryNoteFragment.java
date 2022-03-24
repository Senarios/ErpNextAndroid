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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewDeliveryNoteActivity;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.DeliveryNoteFragmentBinding;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.DeliveryNoteViewModel;

import java.util.List;

public class DeliveryNoteFragment extends Fragment implements ProfilesCallback {

    public boolean isProfilesEnded = false;
    String doctype = "Delivery Note";
    ActivityResultLauncher<Intent> newDeliveryNoteLauncher;
    int limitStart = 0;
    int searchLimitStart = 0;
    private DeliveryNoteViewModel mViewModel;
    private DeliveryNoteFragmentBinding binding;
    private StockListsAdapter stockListsAdapter;
    private StockListsAdapter searchedStockListsAdapter;
    private boolean clear = true;
    private boolean renew = true;
    String pickedDate = "";

    public static DeliveryNoteFragment newInstance() {
        return new DeliveryNoteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DeliveryNoteFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(DeliveryNoteViewModel.class);
        if (Utils.isNetworkAvailable()) {
            getDeliveryNotes();
        }
        setEntriesObserver();
        setClickListeners();
        binding.deliveryNoteRv.setVisibility(View.VISIBLE);
        binding.searchInvoiceRv.setVisibility(View.GONE);
        binding.deliveryNoteRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(binding.deliveryNoteRv)) {
                        if (!isProfilesEnded) {
                            limitStart = limitStart + 20;
                            getDeliveryNotes();
                        }

                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        binding.searchInvoiceRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(binding.searchInvoiceRv)) {
                        if (!isProfilesEnded) {
                            int tempLimit = searchLimitStart;
                            if (searchedStockListsAdapter.getItemCount() >= 20)
                                searchLimitStart = searchLimitStart + 20;
                            if(tempLimit != searchLimitStart)
                            searchDeliveryNotes();
                        }

                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        newDeliveryNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // There are no request codes
                    Intent data = result.getData();
                    getDeliveryNotes();
                });

        return binding.getRoot();
    }

    private void getDeliveryNotes() {
        mViewModel.getDeliveryNotesApi(getActivity(),
                doctype,
                20,
                true,
                "`tabDelivery Note`.`modified` desc",
                limitStart);
    }

    private void searchDeliveryNotes() {
        mViewModel.searchDeliveryNotesApi(getActivity(),
                doctype,
                20,
                true,
                "`tabDelivery Note`.`modified` desc",
                searchLimitStart,
                pickedDate);
    }

    private void setEntriesObserver() {
        mViewModel.getDeliveryNotes().observe(getActivity(), lists -> {
            if (lists != null) {
                binding.deliveryNoteRv.setVisibility(View.VISIBLE);
                binding.searchInvoiceRv.setVisibility(View.GONE);
                setDeliveryNoteAdapter(lists);
            }
        });

        mViewModel.searchDeliveryNotes().observe(getActivity(), lists -> {
            binding.deliveryNoteRv.setVisibility(View.GONE);
            binding.searchInvoiceRv.setVisibility(View.VISIBLE);
            if (searchedStockListsAdapter == null || searchedStockListsAdapter.getAllItems() == null || searchedStockListsAdapter.getAllItems().isEmpty() || renew) {
                setSearchInvoicesAdapter(lists);
                renew = false;
            } else searchedStockListsAdapter.notifyItemRangeChanged(0, lists.size());
        });
    }

    private void setSearchInvoicesAdapter(List<List<String>> lists) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        searchedStockListsAdapter = new StockListsAdapter(getContext(), lists, doctype, this);
        binding.searchInvoiceRv.setLayoutManager(linearLayoutManager);
        binding.searchInvoiceRv.setAdapter(searchedStockListsAdapter);
    }

    private void setDeliveryNoteAdapter(List<List<String>> noteList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockListsAdapter = new StockListsAdapter(getContext(), noteList, doctype, this);
        binding.deliveryNoteRv.setLayoutManager(linearLayoutManager);
        binding.deliveryNoteRv.setAdapter(stockListsAdapter);
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        binding.addNew.setOnClickListener(v -> openNewStockEntry());

        binding.search.setOnClickListener(v -> {
            if (clear) {
                Utils.pickDate(requireContext(), date -> {
                    //filterTasks(date);
                    pickedDate = date;
                    binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_close_24));
                    clear = false;
                    renew = true;
                    searchDeliveryNotes();

                });
            } else {
                binding.search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_search_24));
                clear = true;
                pickedDate = "";
                searchLimitStart = 0;
                binding.deliveryNoteRv.setVisibility(View.VISIBLE);
                binding.searchInvoiceRv.setVisibility(View.GONE);

//                    getInvoices();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.clearList();
    }

    public void openNewStockEntry() {
        Intent intent = new Intent(requireActivity(), AddNewDeliveryNoteActivity.class);
        newDeliveryNoteLauncher.launch(intent);
    }

    @Override
    public void onProfileClick(List<String> list) {

    }

    @Override
    public void onLongClick(List<String> list, int position) {

    }
}