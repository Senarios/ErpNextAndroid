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
    private DeliveryNoteViewModel mViewModel;
    private DeliveryNoteFragmentBinding binding;
    private StockListsAdapter stockListsAdapter;

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

    private void setEntriesObserver() {
        mViewModel.getDeliveryNotes().observe(getActivity(), lists -> {
            if (lists != null) {
                setDeliveryNoteAdapter(lists);
            }
        });
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