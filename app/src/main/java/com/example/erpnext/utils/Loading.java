package com.example.erpnext.utils;

import android.app.Activity;
import android.app.ProgressDialog;

import com.example.erpnext.R;
import com.example.erpnext.app.MainApp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Loading {

    private final ProgressDialog pDialog;
    private ScheduledExecutorService scheduleService;
    private Activity activity;
    private int autoCancelAfter = 0;
    private OnLoadingAutoCanceled callback;

    public Loading(ProgressDialog pDialog) {
        this.pDialog = pDialog;


    }

    private Loading(Activity activity) {
        this.activity = activity != null ? activity : MainApp.getAppContext().getCurrentActivity();
        this.pDialog = new ProgressDialog(this.activity, R.style.MyAlertDialogStyle);
//        Sprite doubleBounce = new CubeGrid();
//        doubleBounce.setColorFilter(new ColorFilter());
//        doubleBounce.setColor(R.color.primary);
//        pDialog.setIndeterminateDrawable(doubleBounce);
    }


    public static Loading make(Activity activity) {
        return new Loading(activity);
    }

    public Loading setCancelable(boolean cancelable) {
        pDialog.setCancelable(cancelable);
        return this;
    }

    public Loading setMessage(String message) {
        // pDialog.setIndeterminateDrawable(activity.getResources().getDrawable(R.drawable.loader_icon_2x));
        pDialog.setMessage(message);

        return this;
    }

    public Loading updateMessage(String message) {
        if (pDialog != null && pDialog.isShowing()) {
            activity.runOnUiThread(() -> pDialog.setMessage(message));
        }
        return this;
    }

    public Loading autoCancel(int afterMillies) {
        this.autoCancelAfter = afterMillies;
        return this;
    }

    public Loading onCancel(OnLoadingAutoCanceled callback) {
        this.callback = callback;
        return this;
    }


    public Loading show() {
        if (!pDialog.isShowing())
            pDialog.show();
        if (autoCancelAfter > 0) {
            autoCancelAfter();
        }
        return this;
    }

    private void autoCancelAfter() {
        scheduleService = Executors.newScheduledThreadPool(1);
        scheduleService.schedule(() -> activity.runOnUiThread(() -> {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    scheduleService.shutdownNow();
                    scheduleService = null;
                    if (callback != null)
                        callback.onCancel();
                    cancel();
                }
            } catch (Exception e) {
            }
        }), autoCancelAfter, TimeUnit.SECONDS);
    }

    public void cancel() {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                if (scheduleService != null) {
                    scheduleService.shutdownNow();
                    scheduleService = null;
                }
                pDialog.dismiss();
                pDialog.cancel();
            }
        } catch (Exception e) {
            Logger.log("Loading", "cancel");
        }
    }


    public boolean isVisible() {
        return pDialog != null && pDialog.isShowing();
    }

    public interface OnLoadingAutoCanceled {
        void onCancel();
    }

}
