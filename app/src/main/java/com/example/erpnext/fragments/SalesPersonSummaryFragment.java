package com.example.erpnext.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.erpnext.R;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.SalesPersonSummaryFragmentBinding;
import com.example.erpnext.repositories.AddReconciliationRepo;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.SalesPersonSummaryViewModel;

public class SalesPersonSummaryFragment extends Fragment implements View.OnClickListener, DateTime.datePickerCallback {

    SalesPersonSummaryFragmentBinding binding;
    TextView fromDate, toDate;
    String clickedDate;
    AutoCompleteTextView warehouse, item;
    private SalesPersonSummaryViewModel mViewModel;
    private static final String[] VEH_STATUS = new String[]{
            "Sales Order", "Delivery Note", "Sales Invoice"
    };

    public static SalesPersonSummaryFragment newInstance() {
        return new SalesPersonSummaryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SalesPersonSummaryViewModel.class);
        binding = SalesPersonSummaryFragmentBinding.inflate(inflater, container, false);
        MainApp.INSTANCE.setCurrentActivity(getActivity());


        setClickListeners();
        setObservers("Sales+Person+Commission+Summary", "%7B%22doc_type%22%3A%22Sales+Order%22%7D");
        if (Utils.isNetworkAvailable()) {
            getDoctyType();
        }

        return binding.getRoot();
    }

    private void getDoctyType() {
        mViewModel.getDocTypeApi("Report", "Stock Ledger");
    }

    private void setObservers(String report, String filter) {
        mViewModel.getDoctype().observe(getActivity(), doctypeResponse -> {
            if (doctypeResponse != null) {

                mViewModel.getStokeReportApi(report, filter);
            }
        });

        mViewModel.getReport().observe(getActivity(), reportResponse -> {
            if (reportResponse != null && reportResponse.getMessage().getColumns() != null) {
                binding.notFound.setVisibility(View.GONE);
                mViewModel.initializeTable(reportResponse, binding, getActivity());

            } else Notify.Toast(getString(R.string.no_record_found));
        });
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.generate.setOnClickListener(this);
        binding.selectReport.setKeyListener(null);
        binding.selectReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, VEH_STATUS);
                binding.selectReport.setAdapter(adapter);
                binding.selectReport.showDropDown();
            }
        });
        binding.selectReport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.selectReport.getText().toString().equals("Sales Order")) {
                    setObservers("Sales+Person+Commission+Summary", "%7B%22doc_type%22%3A%22Sales+Order%22%7D");
                } else if (binding.selectReport.getText().toString().equals("Delivery Note")) {
                    setObservers("Sales+Person+Commission+Summary", "%7B%22doc_type%22%3A%22Delivery+Note%22%7D");
                } else if (binding.selectReport.getText().toString().equals("Sales Invoice")) {
                    setObservers("Sales+Person+Commission+Summary", "%7B%22doc_type%22%3A%22Sales+Invoice%22%7D");
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.generate:
                showGenerateDialog();
                break;
        }
    }

    private void showGenerateDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.generate_report_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        fromDate = dialog.findViewById(R.id.from_date_edit);
        toDate = dialog.findViewById(R.id.to_date_edit);
        warehouse = dialog.findViewById(R.id.warehouse_edit);
        ImageView searchItem = dialog.findViewById(R.id.search_item);
        item = dialog.findViewById(R.id.item_edit);
        Button add = dialog.findViewById(R.id.add);
        Button cancel = dialog.findViewById(R.id.cancel);
        fromDate.setText(DateTime.getPrevMonthCurrentDate());
        toDate.setText(DateTime.getCurrentDate());
        setFoucusListeners();
        fromDate.setOnClickListener(v -> {
            clickedDate = "fromDate";
            DateTime.showDatePickerWithNoFutureDates(getActivity(), this);
        });
        toDate.setOnClickListener(v -> {
            clickedDate = "toDate";
            DateTime.showDatePickerWithoutLimits(getActivity(), this);
        });
        add.setOnClickListener(v -> {
            String selectedFromDate = fromDate.getText().toString();
            String selectedToDate = toDate.getText().toString();
            if (selectedFromDate != null && !selectedFromDate.equalsIgnoreCase("") && selectedToDate != null && !selectedToDate.equalsIgnoreCase("")) {
                dialog.dismiss();
                mViewModel.generateReport(selectedToDate, selectedFromDate, warehouse.getText().toString(), item.getText().toString());
            } else Notify.Toast(getString(R.string.enter_dates));
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        searchItem.setOnClickListener(v -> {
            if (item.getText().toString() != null) {
                mViewModel.getSearchLinkApi(item.getText().toString(), "Item", "erpnext.controllers.queries.item_query", null, RequestCodes.API.SEARCH_ITEM);
            } else Notify.Toast(getString(R.string.enter_code));
        });
        mViewModel.getSearchResult().observe(this, searchLinkResponse -> {
            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
                if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_WAREHOUSE) {
                    Utils.setSearchAdapter(getActivity(), warehouse, searchLinkResponse);
                    AddReconciliationRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_ITEM) {
                    Utils.setSearchAdapter(getActivity(), item, searchLinkResponse);
                    AddReconciliationRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
                }
            }
        });

    }

    private void setFoucusListeners() {
        warehouse.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi("", "Warehouse", "", null, RequestCodes.API.SEARCH_WAREHOUSE);
            }
        });
        item.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mViewModel.getSearchLinkApi("", "Item", "erpnext.controllers.queries.item_query", null, RequestCodes.API.SEARCH_ITEM);
            }
        });
    }

    @Override
    public void onSelected(String date) {
        if (clickedDate != null && clickedDate.equalsIgnoreCase("toDate")) {
            toDate.setText(date);
        } else if (clickedDate != null && clickedDate.equalsIgnoreCase("fromDate")) {
            fromDate.setText(date);
        }
    }
}