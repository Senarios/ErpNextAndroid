package com.example.erpnext.network;

import android.util.Log;

import com.example.erpnext.R;
import com.example.erpnext.app.BaseClass;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.utils.Configurations;
import com.example.erpnext.utils.Loading;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.Utils;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NetworkCall extends BaseClass {
    boolean continueLoading = false;
    private Object taggedObject;
    private OnNetworkResponse callback;
    private Call request;
    private Loading loading;

    private NetworkCall() {
    }

    public static synchronized NetworkCall make() {
        return new NetworkCall();
    }

    public NetworkCall setCallback(OnNetworkResponse callback) {
        this.callback = callback;
        return this;
    }

    public NetworkCall enque(Call call) {
        this.request = call;
        Log.e("none", "enque: "+call.toString() );
        return this;
    }

    public NetworkCall setTag(Object tag) {
        this.taggedObject = tag;
        return this;
    }

    public NetworkCall autoLoadingCancel(Loading loading) {
        this.loading = loading;
        return this;
    }

    public NetworkCall execute() {
        request.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                cancelLoading();
                if (BaseResponse.isSuccess(response)) {
                    try {
                        callback.onSuccess(call, response, taggedObject);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (BaseResponse.isSessionExpired(response)) {
                    MainApp.resetApplication();
                    Notify.ToastLong("Your session has been expired, Please login again");
                } else if (response.body() == null || !BaseResponse.isSuccess(response)) {
                    try {
                        callback.onFailure(call, Network.parseErrorResponse(response), taggedObject);
                    } catch (Exception e) {
                        callback.onFailure(call, new BaseResponse(Configurations.isProduction() ? string(R.string.exceptionInErrorResponse) : string(R.string.invalid_data)), taggedObject);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                cancelLoading();
                BaseResponse response;
                if (t != null) {
                    if (Utils.isCause(SocketTimeoutException.class, t)) {
                        response = new BaseResponse(string(R.string.timeout));
                    } else if (Utils.isCause(ConnectException.class, t)) {
                        response = new BaseResponse(string(R.string.connectException));
                    } else if (Utils.isCause(MalformedJsonException.class, t)) {
                        response = new BaseResponse(string(R.string.invalid_data));
                    } else if (t instanceof SSLHandshakeException || t instanceof SSLException) {
                        response = new BaseResponse(string(R.string.ssl));
                    } else if (t instanceof IOException) {
                        response = new BaseResponse(string(R.string.no_internet));
                    } else {
                        response = new BaseResponse(Configurations.isDevelopment() ? t.getMessage() : string(R.string.text_somethingwentwrong));
                    }
                    callback.onFailure(call, response, taggedObject);
                } else {
                    response = new BaseResponse(string(R.string.text_somethingwentwrong));
                    callback.onFailure(call, response, taggedObject);
                }
            }
        });
        return this;
    }

    public void cancelLoading() {
        if (loading != null && loading.isVisible())
            loading.cancel();
    }
}
