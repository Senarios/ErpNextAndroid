package com.example.erpnext.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddCustomerActivity;
import com.example.erpnext.adapters.MyTaskAdapter;
import com.example.erpnext.adapters.ShowCustomerAdapter;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.CustomerFragmentBinding;
import com.example.erpnext.models.Info;
import com.example.erpnext.models.MyTaskRes;
import com.example.erpnext.models.ShowCustomerDatum;
import com.example.erpnext.models.ShowCustomerRes;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.repositories.CustomersRepo;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.CustomersViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomersFragment extends Fragment implements View.OnClickListener, ProfilesCallback {

    public boolean isItemsEnded = false;
    String doctype = "Customer";
    CustomerFragmentBinding binding;
    private StockListsAdapter stockListsAdapter;
    private int limitStart = 0;
    private CustomersViewModel mViewModel;
    ShowCustomerAdapter showCustomerAdapter;

    public static CustomersFragment newInstance() {
        return new CustomersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CustomersViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = CustomerFragmentBinding.inflate(inflater, container, false);

        setClickListeners();
        enlistCustomers();
//        getItems();
//        setObservers();
//        binding.listRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if (Utils.isNetworkAvailable()) {
//                    if (Utils.isLastItemDisplaying(binding.listRv)) {
//                        if (!isItemsEnded && stockListsAdapter != null && stockListsAdapter.getAllItems() != null && stockListsAdapter.getAllItems().size() > 10) {
//                            limitStart = limitStart + 20;
//                            getItems();
//                        }
//                    }
//                }
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
        return binding.getRoot();
    }

    private void getItems() {
        mViewModel.getItemsApi(doctype,
                20,
                true,
                "`tabCustomer`.`modified` desc",
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
                startActivityForResult(new Intent(getActivity(), AddCustomerActivity.class), RequestCodes.ADD_CUSTOMER);
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
        if (requestCode == RequestCodes.ADD_CUSTOMER) {
            if (resultCode == RESULT_OK) {
                CustomersRepo.getInstance().items.setValue(new ArrayList<>());
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
        CustomersRepo.getInstance().items.setValue(new ArrayList<>());
    }

    private void enlistCustomers() {
        Utils.showLoading(getActivity());
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://75.119.143.175:8080/ErpNext/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<ShowCustomerRes> call = apiServices.getAllCustomers();
        call.enqueue(new Callback<ShowCustomerRes>() {
            @Override
            public void onResponse(Call<ShowCustomerRes> call, Response<ShowCustomerRes> response) {
                if (response.isSuccessful()) {
                    Utils.dismiss();
                    ShowCustomerRes resObj = response.body();
                    List<ShowCustomerDatum> list = resObj.getData();
                    showCustomerAdapter = new ShowCustomerAdapter((ArrayList<ShowCustomerDatum>) list, getContext());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    binding.listRv.setLayoutManager(layoutManager);
                    binding.listRv.setAdapter(showCustomerAdapter);
                    showCustomerAdapter.notifyDataSetChanged();

                } else {
                    Utils.dismiss();
                    Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShowCustomerRes> call, Throwable t) {
                Utils.dismiss();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}