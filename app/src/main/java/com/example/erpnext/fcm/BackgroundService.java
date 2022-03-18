package com.example.erpnext.fcm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.models.SalesPersonLocRes;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.utils.Notify;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackgroundService extends Service {
    FusedLocationProviderClient fusedLocationProviderClient;
    Handler handler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRepeatingTask();
        createNotificationChannel();

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, "ChannelId1")
                .setContentTitle("Location Status")
                .setContentText("Keep your location enabled")
                .setSmallIcon(R.mipmap.app_icon)
                .setContentIntent(pendingIntent).build();

        startForeground(1, notification);

        return START_REDELIVER_INTENT;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    "ChannelId1", "Foreground notification", NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

    @SuppressLint("MissingPermission")
    private void sendLocToAdmin() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://75.119.143.175:8080/ErpNext/")
                                .addConverterFactory(GsonConverterFactory.create()).build();
                        ApiServices apiServices = retrofit.create(ApiServices.class);
                        Call<SalesPersonLocRes> call = apiServices.sendSalesPersonLoc(AppSession.get("email"), location.getLatitude(), location.getLongitude());
                        call.enqueue(new Callback<SalesPersonLocRes>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<SalesPersonLocRes> call, Response<SalesPersonLocRes> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getStatus().toString().equals("200")) {
//                                    Toast.makeText(getApplicationContext(), "Loc Sent", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SalesPersonLocRes> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }

                        });
                    }
                }
            });
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sendLocToAdmin();
            handler.postDelayed(runnable, 90000);
        }
    };

    void startRepeatingTask() {
        runnable.run();
    }
}
