package com.example.erpnext.fragments;

import static com.example.erpnext.adapters.MyTaskAdapter.MY_PREF;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.erpnext.R;
import com.example.erpnext.adapters.MyTaskAdapter;
import com.example.erpnext.adapters.StockListsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.databinding.MyTasksFragmentBinding;
import com.example.erpnext.models.Info;
import com.example.erpnext.models.MyTaskOfflineModel;
import com.example.erpnext.models.MyTaskRes;
import com.example.erpnext.models.MyTaskUpdateRes;
import com.example.erpnext.models.Profile;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.MyTasksViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyTasksFragment extends Fragment implements View.OnClickListener, ProfilesCallback, MyTaskAdapter.TaskUpdate {
    private final List<Profile> profileList = new ArrayList<>();
    public boolean isItemsEnded = false;
    String doctype = "My Tasks";
    MyTasksFragmentBinding binding;
    StockListsAdapter stockListsAdapter;
    private MyTasksViewModel mViewModel;
    public MyTaskAdapter myTaskAdapter;
    public static final String MY_PREFF = "mypref";
    List<String> arrayList = new ArrayList<>();
    ArrayList<Info> infoArrayList = new ArrayList<>();
    FusedLocationProviderClient fusedLocationProviderClient;
    Dialog dialog;

    public static MyTasksFragment newInstance() {
        return new MyTasksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MyTasksViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = MyTasksFragmentBinding.inflate(inflater, container, false);

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                enlistRecords();
                binding.swipe.setRefreshing(false);
            }
        });

        setClickListeners();
        enlistRecords();
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
                        getTaskItems();

                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return binding.getRoot();
    }

    private void setObservers() {
        mViewModel.getMyTaskItems().observe(getActivity(), lists -> {
            if (lists != null) {
                setItemsAdapter(lists);
            }
        });
    }

    private void setItemsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stockListsAdapter = new StockListsAdapter(getContext(), profilesList, "doctype", this);
        binding.listRv.setLayoutManager(linearLayoutManager);
        binding.listRv.setAdapter(stockListsAdapter);
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);

    }

    private void getTaskItems() {
//        getCurrentLocation();
//        enlistRecords();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;

        }
    }

    @Override
    public void onProfileClick(List<String> list) {

    }

    @Override
    public void onLongClick(List<String> list, int position) {
//        selectAction(list, position);
    }

    private void enlistRecords() {
        Utils.showLoading(getActivity());
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://75.119.143.175:8080/ErpNext/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<MyTaskRes> call = apiServices.getMyTaskitem(AppSession.get("email"));
        call.enqueue(new Callback<MyTaskRes>() {
            @Override
            public void onResponse(Call<MyTaskRes> call, Response<MyTaskRes> response) {
                if (response.isSuccessful()) {
                    Utils.dismiss();
                    MyTaskRes resObj = response.body();
                    List<Info> list = resObj.getInfo();
                    myTaskAdapter = new MyTaskAdapter((ArrayList<Info>) list, getContext(), MyTasksFragment.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    binding.listRv.setLayoutManager(layoutManager);
                    binding.listRv.setAdapter(myTaskAdapter);
                    myTaskAdapter.notifyDataSetChanged();

                } else {
                    Utils.dismiss();
                    Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyTaskRes> call, Throwable t) {
                Utils.dismiss();
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getContext(), "Time out, Please try again", Toast.LENGTH_SHORT).show();
                } else if (t instanceof IOException) {
                    Toast.makeText(getContext(), "Check you internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void taskdoupdate() {
        SharedPreferences preferences = getContext().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        String taskName = preferences.getString("taskName", "abc");
        String shopName = preferences.getString("shopName", "bc");
        String comment = preferences.getString("added_comment", "b");
        getCurrentLocation(AppSession.get("email"), taskName, shopName,comment);
        myTaskAdapter.notifyDataSetChanged();
    }

    public void updateTaskApi(String email, String taskName, String shopName, String shopStatus,String comment) {
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
                        Utils.dismiss();
                        Toast.makeText(getContext(), getString(R.string.updated), Toast.LENGTH_SHORT).show();
                        binding.swipe.setRefreshing(true);
                        enlistRecords();
                        binding.swipe.setRefreshing(false);

//                        getCurrentLocation(email,taskName,shopName);
                    } else {
                        Utils.dismiss();
                        saveTaskForOffline(email, taskName, shopName, shopStatus, comment);
                        Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Utils.dismiss();
                    saveTaskForOffline(email, taskName, shopName, shopStatus, comment);
                    Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyTaskUpdateRes> call, Throwable t) {
                Utils.dismiss();
                saveTaskForOffline(email, taskName, shopName, shopStatus, comment);
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void saveTaskForOffline(String email, String taskName, String shopName, String shopStatus,String comment) {
        MyTaskOfflineModel myTask = new MyTaskOfflineModel();
        myTask.setEmailName(email);
        myTask.setTaskName(taskName);
        myTask.setShopName(shopName);
        myTask.setShopStat(shopStatus);
        myTask.setComment(comment);
        MainApp.database.myTaskDao().insertTask(myTask);
        Toast.makeText(getContext(), getString(R.string.mytask_logs), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation(String email, String taskName, String shopName,String comment) {
        Utils.showLoading(getActivity());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    SharedPreferences preferences = getContext().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
                    String lat = preferences.getString("lat", "abc");
                    String lon = preferences.getString("long", "abc");
                    Location startCurrentPoint = new Location("");
                    startCurrentPoint.setLatitude(Double.parseDouble(lat));
                    startCurrentPoint.setLongitude(Double.parseDouble(lon));

                    Location endPoint = new Location("");
                    endPoint.setLatitude(location.getLatitude());
                    endPoint.setLongitude(location.getLongitude());
                    SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFF, Context.MODE_PRIVATE).edit();
                    editor.putString("salesPersonCurrLat", String.valueOf(location.getLatitude()));
                    editor.putString("salesPersonCurrLng", String.valueOf(location.getLongitude()));
                    editor.apply();

                    SharedPreferences preferences1 = getContext().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
                    String shopStat = preferences1.getString("statusKey", "abc");

                    if (location != null && startCurrentPoint.distanceTo(endPoint) <= 50) {
                        if(Utils.isNetworkAvailable()){
                            updateTaskApi(email, taskName, shopName, shopStat,comment);
                        } else {
                            Utils.dismiss();
                            saveTaskForOffline(email, taskName, shopName, shopStat, comment);
                        }

                    } else {
                        Utils.dismiss();
                        Notify.Toast(getString(R.string.not_in_shop_location));
                    }
                }
            });
        }
    }

}