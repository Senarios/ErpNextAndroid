package com.example.erpnext.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.FragmentLeadSourcesBinding;
import com.example.erpnext.repositories.LeadSourcesRepo;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.LeadSourcesViewModel;

import java.util.ArrayList;
import java.util.List;

public class LeadSourcesFragment extends Fragment implements View.OnClickListener, ProfilesCallback {

    public boolean isItemsEnded = false;
    String doctype = "Lead Source";
    FragmentLeadSourcesBinding binding;
    private StockListsAdapter stockListsAdapter;
    private int limitStart = 0;
    private LeadSourcesViewModel mViewModel;

    Dialog dialog;

    public static LeadSourcesFragment newInstance() {
        return new LeadSourcesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(LeadSourcesViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = FragmentLeadSourcesBinding.inflate(inflater, container, false);

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
                "`tabLead Source`.`modified` desc",
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
                showAddDialog();
                break;
        }
    }

    @Override
    public void onProfileClick(List<String> list) {

    }

    @Override
    public void onLongClick(List<String> list, int position) {

    }

    private void showAddDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_lead_source_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        EditText sourceName = dialog.findViewById(R.id.source_name);

        Button add = dialog.findViewById(R.id.add);
        Button cancel = dialog.findViewById(R.id.cancel);

        add.setOnClickListener(v -> {
            if (sourceName.getText().toString() != null && !sourceName.getText().toString().isEmpty()) {
                setSaveObserver();
                mViewModel.saveDocApi(sourceName.getText().toString());
                dialog.dismiss();
            } else Notify.Toast(getString(R.string.write_source_name));
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private void setSaveObserver() {
        mViewModel.docSaved().observe(this, doc -> {
            if (doc != null) {
                Notify.Toast(getString(R.string.successfully_created));
                LeadSourcesRepo.getInstance().savedDoc.setValue(null);
                limitStart = 0;
                isItemsEnded = false;
                getItems();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodes.ADD_CUSTOMER) {
            if (resultCode == RESULT_OK) {
                LeadSourcesRepo.getInstance().items.setValue(new ArrayList<>());
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
        LeadSourcesRepo.getInstance().items.setValue(new ArrayList<>());
    }

}