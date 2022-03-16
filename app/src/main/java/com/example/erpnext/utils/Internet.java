package com.example.erpnext.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.example.erpnext.app.MainApp;

import java.net.HttpURLConnection;
import java.net.URL;


public class Internet {

    public static boolean isConnected(Context mContext) {
        if (mContext == null)
            return true;
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    static synchronized void hasActiveInternetConnection(OnConnectivityCheckCallback callback) {
        new Handler().postDelayed(() -> {
            new Thread(() -> {
                if (isConnected(MainApp.getAppContext())) {
                    try {
                        HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                        urlc.setRequestProperty("User-Agent", "Test");
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(1500);
                        urlc.connect();
//                        Log.e("URLC"," = "+urlc.getResponseCode());
                        if (urlc.getResponseCode() == 200)
                            callback.onInternetConnectivityStatusResponse(true);
                        urlc.disconnect();
                    } catch (Exception e) {
                        Log.e("", "Error checking internet connection", e);
                        callback.onInternetConnectivityStatusResponse(false);
                    }
                } else {
                    Log.e("URLC", " = " + callback);

                    callback.onInternetConnectivityStatusResponse(false);
                }
            }).start();
        }, 3000);
    }

    public enum ErrorType {
        TOAST, SNACK_BAR, DIALOG
    }

    public interface OnConnectivityCheckCallback {
        void onInternetConnectivityStatusResponse(boolean isConnected);
    }

}