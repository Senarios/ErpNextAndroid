package com.example.erpnext.fragments;

import android.annotation.SuppressLint;
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
import com.example.erpnext.adapters.LogsInvoiceAdapter;
import com.example.erpnext.adapters.LogsTasksAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.FragmentLogsBinding;
import com.example.erpnext.models.AddCustomerOfflineModel;
import com.example.erpnext.models.MyTaskOfflineModel;
import com.example.erpnext.models.MyTaskUpdateRes;
import com.example.erpnext.models.PendingOrder;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.utils.Utils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

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
public class LogsFragment extends Fragment implements LogsTasksAdapter.LogTaskUpdate {
    private FragmentLogsBinding binding;
    private LogsViewModel mViewModel;
    List<MyTaskOfflineModel> tasks = new ArrayList<>();
    List<AddCustomerOfflineModel> customers = new ArrayList<>();

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

    private void setCustomerAdapter() {
        customers = MainApp.database.addCustomerDao().getCustomers();
        if (customers.size() > 0) {
            Log.wtf("addcustomers", customers.toString());
        } else {
            Log.wtf("showcustomers", customers.toString());
        }
    }

    private void setTasksAdapter() {
        tasks = MainApp.database.myTaskDao().getOrders();
        if (tasks.size() > 0) {
            binding.noDataOfflineTV.setVisibility(View.GONE);
            binding.tasksRV.setVisibility(View.VISIBLE);
            binding.taskTV.setVisibility(View.VISIBLE);
            LogsTasksAdapter adapter = new LogsTasksAdapter(tasks, requireContext(),LogsFragment.this);
            binding.tasksRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.tasksRV.setAdapter(adapter);
        } else {
            binding.tasksRV.setVisibility(View.GONE);
            binding.taskTV.setVisibility(View.GONE);
        }
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
            binding.noDataOfflineTV.setVisibility(View.GONE);
            binding.invoiceRV.setVisibility(View.VISIBLE);
            binding.invoicesTV.setVisibility(View.VISIBLE);
            LogsInvoiceAdapter adapter = new LogsInvoiceAdapter(requireContext(), pendingOrders);
            binding.invoiceRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.invoiceRV.setAdapter(adapter);
        } else {
            binding.noDataOfflineTV.setVisibility(View.VISIBLE);
            binding.invoiceRV.setVisibility(View.GONE);
            binding.invoicesTV.setVisibility(View.GONE);
        }
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
                        MainApp.database.myTaskDao().deleteTask(tasks.get(0));
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
    public void taskdoupdate(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.sync_log_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sync:
                        if (tasks.size() > 0) {
                            MyTaskOfflineModel task = tasks.get(0);
                            updateTaskApi(task.getEmailName(), task.getTaskName(), task.getShopName(), task.getShopStat(), task.getComment());
                        } else {
                            Toast.makeText(getContext(), "No Logs Found", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}