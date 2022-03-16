package com.example.erpnext.fcm;

import static com.example.erpnext.activities.LoginActivity.MAIN_ACT;
import static com.example.erpnext.fragments.MyTasksFragment.MY_PREFF;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.erpnext.models.FcmRes;
import com.example.erpnext.models.SalesPersonLocRes;
import com.example.erpnext.models.SendNotificationRes;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BgServiceForSPLoc extends Service {
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
                @SuppressLint("ApplySharedPref")
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    SharedPreferences preferences = getSharedPreferences(MAIN_ACT, Context.MODE_PRIVATE);
                    String lat = preferences.getString("lati", "abc");
                    String lon = preferences.getString("longi", "ac");
                    Location near_location = new Location("");
                    near_location.setLatitude(Double.parseDouble(lat));
                    near_location.setLongitude(Double.parseDouble(lon));

                    Location start_location = new Location("");
                    start_location.setLatitude(location.getLatitude());
                    start_location.setLongitude(location.getLongitude());
                    SharedPreferences.Editor editor = getSharedPreferences(MAIN_ACT, Context.MODE_PRIVATE).edit();
                    editor.putString("lati", String.valueOf(location.getLatitude()));
                    editor.putString("longi", String.valueOf(location.getLongitude()));
                    editor.commit();

                    if (start_location.distanceTo(near_location) <= 20) {
//                        Notify.Toast("You are not moving");
                        sendNotification();
                    } else {
//                        Notify.Toast("You are moving");
                    }


                }
            });
        }
    }
    private void sendNotification() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://75.119.143.175:8080/ErpNext/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<SendNotificationRes> call = apiServices.getNotification(AppSession.get("email"));
        call.enqueue(new Callback<SendNotificationRes>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<SendNotificationRes> call, Response<SendNotificationRes> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
//                        Toast.makeText(getApplicationContext(), "notification", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(getApplicationContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendNotificationRes> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sendLocToAdmin();
            handler.postDelayed(runnable, 1800000);
        }
    };

    void startRepeatingTask() {
        runnable.run();
    }
}
