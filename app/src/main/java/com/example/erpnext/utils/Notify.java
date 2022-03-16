package com.example.erpnext.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.erpnext.app.MainApp;


/**
 * Created by junaid on 13/10/20.
 */

public class Notify {
    public static final String SUCCESS_COLOR = "#22A109";
    public static final String FAILURE_COLOR = "#EC0909";
//    private static Snackbar snackbar;


    public static void Toast(String message) {
        Toast.makeText(MainApp.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void ToastInt(int message) {
        Toast.makeText(MainApp.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }


    public static void ToastLong(String message) {
        Toast.makeText(MainApp.getAppContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void alertDialog(Activity activity, String message) {
//        if (activity != null)
//            Alerter.create(activity)
//                    .setText(message)
//                    .setBackgroundColorRes(R.color.red)
//                    .show();
    }

    public static void alertDialogGreen(Context activity, String message) {
//        if (activity != null)
//            Alerter.create((Activity) activity)
//                    .setText(message)
//                    .setBackgroundColorRes(R.color.green)
//                    .show();
    }
}
