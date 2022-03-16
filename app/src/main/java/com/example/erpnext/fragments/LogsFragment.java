package com.example.erpnext.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.erpnext.R;
import com.example.erpnext.adapters.LogsAddCustomerAdapter;
import com.example.erpnext.adapters.LogsInvoiceAdapter;
import com.example.erpnext.adapters.LogsStockEntryAdapter;
import com.example.erpnext.adapters.LogsTasksAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.FragmentLogsBinding;
import com.example.erpnext.models.AddCustomerOfflineModel;
import com.example.erpnext.models.AddCustomerRes;
import com.example.erpnext.models.MyTaskOfflineModel;
import com.example.erpnext.models.MyTaskUpdateRes;
import com.example.erpnext.models.PendingOrder;
import com.example.erpnext.models.StockEntryOfflineModel;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogsFragment extends Fragment implements LogsTasksAdapter.LogTaskUpdate, LogsStockEntryAdapter.LogStockEntryUpdate, LogsAddCustomerAdapter.LogCustomerUpdate, OnNetworkResponse {
    private FragmentLogsBinding binding;
    private LogsViewModel mViewModel;
    List<MyTaskOfflineModel> tasks = new ArrayList<>();
    List<AddCustomerOfflineModel> customers = new ArrayList<>();
    List<StockEntryOfflineModel> stockEntries = new ArrayList<>();
    private MyTaskOfflineModel selectedTask;
    private StockEntryOfflineModel selectedStockEntry;
    private AddCustomerOfflineModel selectedCustomer;

    public LogsFragment() {
        // Required empty public constructor
    }

    public static LogsFragment newInstance() {
        return new LogsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(LogsViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = FragmentLogsBinding.inflate(inflater, container, false);

        binding.back.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        setStockEntriesAdapter();
        setCustomerAdapter();
        setTasksAdapter();
        setInvoicesAdapter();

//        binding.menu.setOnClickListener(v -> {
//            PopupMenu popupMenu = new PopupMenu(getActivity(), binding.menu);
//            popupMenu.inflate(R.menu.sync_log_menu);
//            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    switch (item.getItemId()) {
//                        case R.id.sync:
//                            if (tasks.size() > 0) {
//                                MyTaskOfflineModel task = tasks.get(0);
//                                updateTaskApi(task.getEmailName(), task.getTaskName(), task.getShopName(), task.getShopStat(), task.getComment());
//                            } else {
//                                Toast.makeText(getContext(), "No Logs Found", Toast.LENGTH_SHORT).show();
//                            }
//                            break;
//                    }
//                    return true;
//                }
//            });
//            popupMenu.show();
//        });

        return binding.getRoot();
    }

    private void setStockEntriesAdapter() {
        stockEntries = MainApp.database.stockEntryDao().getEntries();
        if (stockEntries.size() > 0) {
            LogsStockEntryAdapter adapter = new LogsStockEntryAdapter(stockEntries, requireContext(),LogsFragment.this);
            binding.stockRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.stockRV.setAdapter(adapter);
        }
        setViews();
    }

    private void setCustomerAdapter() {
        customers = MainApp.database.addCustomerDao().getCustomers();
        if (customers.size() > 0) {
            LogsAddCustomerAdapter adapter = new LogsAddCustomerAdapter(customers, requireContext(),LogsFragment.this);
            binding.customerRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.customerRV.setAdapter(adapter);
        }
        setViews();
    }

    private void setTasksAdapter() {
        tasks = MainApp.database.myTaskDao().getOrders();
        if (tasks.size() > 0) {
            LogsTasksAdapter adapter = new LogsTasksAdapter(tasks, requireContext(),LogsFragment.this);
            binding.tasksRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.tasksRV.setAdapter(adapter);
        }
        setViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setInvoicesAdapter() {
        List<PendingOrder> pendingOrders = MainApp.database.pendingOrderDao().getOrders();
        if (pendingOrders.size() > 0) {
            binding.invoiceLinear.setVisibility(View.VISIBLE);
            LogsInvoiceAdapter adapter = new LogsInvoiceAdapter(requireContext(), pendingOrders);
            binding.invoiceRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.invoiceRV.setAdapter(adapter);
        } else {
            binding.invoiceLinear.setVisibility(View.GONE);
        }
        setViews();
    }

    public void updateTaskApi(String email, String taskName, String shopName, String shopStatus, String comment) {
        Utils.showLoading(getActivity());
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://75.119.143.175:8080/ErpNext/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<MyTaskUpdateRes> call = apiServices.getMyTaskUpdateitem(email, taskName, shopName, shopStatus, comment);
        call.enqueue(new Callback<MyTaskUpdateRes>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<MyTaskUpdateRes> call, Response<MyTaskUpdateRes> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        MainApp.database.myTaskDao().deleteTask(selectedTask);
                        setTasksAdapter();
                        Utils.dismiss();
                        Toast.makeText(getContext(), getString(R.string.updated), Toast.LENGTH_SHORT).show();

//                        getCurrentLocation(email,taskName,shopName);
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
            public void onFailure(Call<MyTaskUpdateRes> call, Throwable t) {
                Utils.dismiss();
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getContext(), "Time out, Please try again", Toast.LENGTH_SHORT).show();
                } else if (t instanceof IOException) {
                    Toast.makeText(getContext(), "Check you internet connection", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void taskToUpdate(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.sync_log_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.sync:
                    if (tasks.size() > 0 && position < tasks.size()) {
                        selectedTask = tasks.get(position);
                        updateTaskApi(selectedTask.getEmailName(), selectedTask.getTaskName(), selectedTask.getShopName(), selectedTask.getShopStat(), selectedTask.getComment());
                    } else {
                        Toast.makeText(getContext(), "No Logs Found", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    @Override
    public void customerdoupdate(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.sync_log_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.sync:
                    if (customers.size() > 0 && position < customers.size()) {
                        selectedCustomer = customers.get(position);
                        syncCustomer(selectedCustomer);
                    } else {
                        Toast.makeText(getContext(), "No Logs Found", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    private void syncCustomer(AddCustomerOfflineModel customerModel){
        Utils.showLoading(requireActivity());
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://75.119.143.175:8080/ErpNext/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        File file = new File(customerModel.getImage());
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody cusFullName =
                RequestBody.create(MediaType.parse("multipart/form-data"), customerModel.getCustomerName());
        RequestBody cusPhone =
                RequestBody.create(MediaType.parse("multipart/form-data"), customerModel.getPhoneNo());
        RequestBody cusReference =
                RequestBody.create(MediaType.parse("multipart/form-data"), customerModel.getReference());
        RequestBody cusLat =
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(customerModel.getLattitude()));
        RequestBody cusLng =
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(customerModel.getLongitude()));
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<AddCustomerRes> call = apiServices.addCustomer(body, cusFullName, cusPhone, cusReference, cusLat, cusLng);
        call.enqueue(new Callback<AddCustomerRes>() {
            @Override
            public void onResponse(Call<AddCustomerRes> call, Response<AddCustomerRes> response) {
                if (response.isSuccessful()) {
                    Utils.dismiss();
                    if (response.body().getStatus().toString().equals("200")) {
                        Toast.makeText(requireContext(), "Customer Added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Customer already exist", Toast.LENGTH_SHORT).show();
                    }
                    MainApp.database.addCustomerDao().deleteCustomer(selectedCustomer);
                    setCustomerAdapter();
                } else {
                    Utils.dismiss();
                    Toast.makeText(requireContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddCustomerRes> call, Throwable t) {
                Utils.dismiss();
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateStockEntry(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.sync_log_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.sync:
                    if (stockEntries.size() > 0 && position < stockEntries.size()) {
                        selectedStockEntry = stockEntries.get(position);
                        try {
                            JSONObject jsonObject = new JSONObject(selectedStockEntry.getData());
                            syncStockEntry(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getContext(), "No Logs Found", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    private void syncStockEntry(JSONObject jsonObject) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SAVE_DOC)
                .autoLoadingCancel(Utils.getLoading(requireActivity(), "Please wait..."))
                .enque(Network.apis().saveDoc(jsonObject, "Submit"))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if (response.code() == 200) {
            Notify.Toast(getString(R.string.successfully_created));
            MainApp.database.stockEntryDao().deleteStockEntry(selectedStockEntry);
            setStockEntriesAdapter();
        } else {
            Notify.Toast(response.message());
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.Toast(response.getServerMessages());
    }

    private void setViews(){
        if(tasks == null || tasks.size() <= 0) {
            binding.taskLinear.setVisibility(View.GONE);
        } else {
            binding.taskLinear.setVisibility(View.VISIBLE);
        }
        if(customers == null || customers.size() <= 0) {
            binding.customerLinear.setVisibility(View.GONE);
        } else {
            binding.customerLinear.setVisibility(View.VISIBLE);
        }
        if(stockEntries == null || stockEntries.size() <= 0) {
            binding.stockLinear.setVisibility(View.GONE);
        } else {
            binding.stockLinear.setVisibility(View.VISIBLE);
        }

        if(binding.invoiceLinear.getVisibility() == View.GONE && binding.taskLinear.getVisibility() == View.GONE
            && binding.customerLinear.getVisibility() == View.GONE && binding.stockLinear.getVisibility() == View.GONE ){
            binding.noDataOfflineTV.setVisibility(View.VISIBLE);
        } else {
            binding.noDataOfflineTV.setVisibility(View.GONE);
        }
    }

}