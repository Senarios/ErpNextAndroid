package com.example.erpnext.fragments;

import static android.app.Activity.RESULT_OK;

import static com.example.erpnext.utils.Constants.COURSE_lOCATION;
import static com.example.erpnext.utils.RequestCodes.LOCATION_REQUEST_CODE;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erpnext.R;
import com.example.erpnext.activities.SalesPersonLocHistoryActivity;
import com.example.erpnext.adapters.MyTaskAdapter;
import com.example.erpnext.adapters.SPLocHistoryAdapter;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.LocationHistoryFragmentBinding;
import com.example.erpnext.models.SPLocHisDatum;
import com.example.erpnext.models.SPLocHisRes;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.repositories.LocationHistoryRepo;
import com.example.erpnext.utils.Constants;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.LocationHistoryViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationHistoryFragment extends Fragment implements View.OnClickListener, ProfilesCallback,DateTime.datePickerCallback, SPLocHistoryAdapter.Abc {

    public boolean isItemsEnded = false;
    String doctype = "User";
    LocationHistoryFragmentBinding binding;
    private StockListsAdapter stockListsAdapter;
    private int limitStart = 0;
    private LocationHistoryViewModel mViewModel;
    TextView date_on;
    AutoCompleteTextView sales_person_email;
    String clickedDate;
    SPLocHistoryAdapter spLocHistoryAdapter;

    public static LocationHistoryFragment newInstance() {
        return new LocationHistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(LocationHistoryViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = LocationHistoryFragmentBinding.inflate(inflater, container, false);

        setClickListeners();
        getItems();
        setObservers();
        checkGPS();
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
        Utils.showLoading(getActivity());
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://75.119.143.175:8080/ErpNext/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<SPLocHisRes> call = apiServices.getSpUnderSupervisor(AppSession.get("email"));
        call.enqueue(new Callback<SPLocHisRes>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<SPLocHisRes> call, Response<SPLocHisRes> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Utils.dismiss();
                        SPLocHisRes resObj = response.body();
                        List<SPLocHisDatum> list = resObj.getInfo();
                        spLocHistoryAdapter = new SPLocHistoryAdapter((ArrayList<SPLocHisDatum>) list, getContext(),LocationHistoryFragment.this);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        binding.listRv.setLayoutManager(layoutManager);
                        binding.listRv.setAdapter(spLocHistoryAdapter);
                        spLocHistoryAdapter.notifyDataSetChanged();
                    } else {
                        Utils.dismiss();
                        Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Utils.dismiss();
                    Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SPLocHisRes> call, Throwable t) {
                Utils.dismiss();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

        });
//        mViewModel.getItemsApi(doctype,
//                20,
//                true,
//                "`tabUser`.`modified` desc",
//                limitStart);
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
//                startActivityForResult(new Intent(getActivity(), AddSalesPersonActivity.class), RequestCodes.ADD_SALES_PERSON);
                break;
        }
    }
    @Override
    public void gett(String salesPerson) {
        showGenerateDialog(salesPerson);
    }
    @Override
    public void onProfileClick(List<String> list) {
        showGenerateDialog(list.get(0));
    }

    @Override
    public void onLongClick(List<String> list, int position) {

    }

    private void showGenerateDialog(String email) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.get_salesperson_loc_history);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        date_on = dialog.findViewById(R.id.date_edit);
        sales_person_email = dialog.findViewById(R.id.sales_person_email);
        sales_person_email.setText(email);
        sales_person_email.setKeyListener(null);
        Button add = dialog.findViewById(R.id.show_history);
        Button cancel = dialog.findViewById(R.id.cancel);
        date_on.setText(DateTime.getPrevMonthCurrentDate());
//        setFoucusListeners();
        date_on.setOnClickListener(v -> {
            clickedDate = "date";
            DateTime.showDatePickerWithNoFutureDates(getActivity(), this);
        });
        add.setOnClickListener(v -> {
            String selected_Date = date_on.getText().toString();
            Intent intent = new Intent(getContext(), SalesPersonLocHistoryActivity.class);
            intent.putExtra("selectedDate", selected_Date);
            intent.putExtra("selectedSPEmail", email);
            startActivity(intent);
            dialog.dismiss();
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodes.ADD_SALES_PERSON) {
            if (resultCode == RESULT_OK) {
                LocationHistoryRepo.getInstance().items.setValue(new ArrayList<>());
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
        LocationHistoryRepo.getInstance().items.setValue(new ArrayList<>());
    }


    @Override
    public void onSelected(String date) {
        if (clickedDate != null && clickedDate.equalsIgnoreCase("date")) {
            date_on.setText(date);
        }
    }
    private void getLocationAccess() {
        if (ContextCompat.checkSelfPermission(getActivity(), Constants.FINE_lOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), COURSE_lOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);

        }
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Your GPS Location seems to be disabled, You have to enable it, Do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public void checkGPS() {
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocationAccess();
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();


        } else {
            getLocationAccess();
        }
    }

}