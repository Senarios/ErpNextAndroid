package com.example.erpnext.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.StrictMode;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.erpnext.activities.LoginActivity;
import com.example.erpnext.utils.BackgroundWorker;


public class MainApp extends Application {
    public static MainApp INSTANCE;
    public static AppDatabase database;
    private Activity activity;

    public static MainApp getAppContext() {
        return INSTANCE;
    }

    public static void resetApplication() {
//        RealmManager.getInstance().clearDB();
//        RealmMethods.deleteAll(User.class);
//        RealmMethods.deleteAll(LocalRoomList.class);
        AppSession.clearSharedPref();
        Intent intent = new Intent(MainApp.getAppContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainApp.getAppContext().startActivity(intent);
    }

    public static void getDataWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data data = new Data.Builder().putString("workType", "complete_order").build();
        OneTimeWorkRequest onetimeJob = new OneTimeWorkRequest.Builder(BackgroundWorker.class)
                .setConstraints(constraints)
                .setInputData(data)
                .build(); // or PeriodicWorkRequest
//        PeriodicWorkRequest request =
//                // Executes MyWorker every 15 minutes
//                new PeriodicWorkRequest.Builder(UploadWorker.class,    15, TimeUnit.MINUTES,
//                        5, TimeUnit.MINUTES)
//                        // Sets the input data for the ListenableWorker
//                        .setConstraints(constraints)
//                        .setInputData(data)
//                        .build();
        WorkManager.getInstance(MainApp.getAppContext()).enqueue(onetimeJob);
//        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork("WorkManagerDemo", ExistingPeriodicWorkPolicy.KEEP, request);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        database = AppDatabase.getAppDatabase(this);


    }

    public Activity getCurrentActivity() {
        return activity;
    }

    public void setCurrentActivity(Activity activity) {
        this.activity = activity;
    }

}


