package com.example.erpnext.repositories;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;

import com.example.erpnext.utils.Notify;

import java.text.ParseException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MyTaskViewRepo  implements OnNetworkResponse {
    private static MyTaskViewRepo instance;
    public MutableLiveData<List<List<String>>> myTask = new MutableLiveData<>();
    int limitSet;

    public static MyTaskViewRepo getInstance() {
        if (instance == null) {
            instance = new MyTaskViewRepo();
        }
        return instance;
    }

    public LiveData<List<List<String>>> getMyTaskItemsList() {
        return myTask;
    }

//    public void getMyTaskItemsApi(String email) {
//        NetworkCall.make()
//                .setCallback(this)
//                .setTag(RequestCodes.API.SHARJEEL)
//                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Loading..."))
//                .enque(Network.apis().myTaskitem(email))
//                .execute();
//    }


    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
//
        Notify.Toast("Success");
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.Toast("Failure");
    }


}

