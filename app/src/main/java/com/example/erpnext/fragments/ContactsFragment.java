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
import com.example.erpnext.activities.AddContactActivity;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.ContactsFragmentBinding;
import com.example.erpnext.repositories.ContactsRepo;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.ContactsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment implements View.OnClickListener, ProfilesCallback {

    public boolean isItemsEnded = false;
    String doctype = "Contact";
    ContactsFragmentBinding binding;
    private StockListsAdapter stockListsAdapter;
    private int limitStart = 0;
    private ContactsViewModel mViewModel;

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = ContactsFragmentBinding.inflate(inflater, container, false);

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
        return binding.getRoot();
    }

    private void getItems() {
        mViewModel.getItemsApi(doctype,
                20,
                true,
                "`tabContact`.`modified` desc",
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
        binding.listRv.setLayoutManager(linearLayoutManager);
        binding.listRv.setAdapter(stockListsAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.add_new:
                startActivityForResult(new Intent(getActivity(), AddContactActivity.class), RequestCodes.ADD_CONTACT);
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
        if (requestCode == RequestCodes.ADD_CONTACT) {
            if (resultCode == RESULT_OK) {
                ContactsRepo.getInstance().items.setValue(new ArrayList<>());
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
        ContactsRepo.getInstance().items.setValue(new ArrayList<>());
    }

}