package com.example.erpnext.activities;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.erpnext.utils.Constants.COURSE_lOCATION;
import static com.example.erpnext.utils.RequestCodes.ERROR_DIALOGE_REQUEST;
import static com.example.erpnext.utils.RequestCodes.LOCATION_REQUEST_CODE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.models.FcmRes;
import com.example.erpnext.models.MyTaskUpdateRes;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.LoginRequest;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.LoginResponse;
import com.example.erpnext.utils.Constants;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkResponse {
    private Button signIn;
    private TextView doNotHaveAccount, forgetPassword;
    private String email, password, network;
    private EditText edit_email, edit_password;
    public static final String MAIN_ACT = "main";
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        clickListeners();
        checkGPS();
    }

    private void initViews() {
        auth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.login);
        edit_email = findViewById(R.id.email);
        edit_password = findViewById(R.id.password);

    }

    private void clickListeners() {
        signIn.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (!isFieldErrorExist()) {
                    if (checkPermission()) {
                        if (isServicesOK()) {
                            getCurrLoc();
                            loginWithFirebase(edit_email.getText().toString(), edit_password.getText().toString());
                            login();
                        }
                    } else requestPermission();
                }
                break;
            default:
                break;
        }
    }

    private void getValues() {
        email = edit_email.getText().toString();
        password = edit_password.getText().toString();
    }

    private boolean isFieldErrorExist() {
        getValues();
        if (email.isEmpty()) {
            Notify.ToastLong(getString(R.string.enter_email));
            return true;
        } else if (password.isEmpty()) {
            Notify.ToastLong(getString(R.string.enter_password));
            return true;
//        } else if (password.length() < 8) {
//            Notify.alertDialog(this, "Password length must be a minimum of 8 characters.");
//            return true;
        } else
            return false;
    }

    private String getDeviceToken() {
        String token = "";

        return token;
    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }
//    public boolean ifPermissions() {
//        if (!EasyPermissions.hasPermissions(this, Constants.REQUIRED_PERMISSIONS)) {
//            if (SDK_INT >= Build.VERSION_CODES.R) {
//                return Environment.isExternalStorageManager();
//            } else {
//                int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
//                int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
//                return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
//            }
////            EasyPermissions.requestPermissions(this, "App requires some permissions to function properly.", 1, Constants.REQUIRED_PERMISSIONS);
//            return false;
//        } else return true;

    //    }
    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, RequestCodes.WRITE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestCodes.WRITE_PERMISSION:
                if (grantResults.length > 0) {
                    boolean READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WRITE_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                        // perform action when allow permission success
                        Toast.makeText(this, getString(R.string.click_login), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.permission_for_storage, Toast.LENGTH_SHORT).show();


                    }
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(this, R.string.permission_for_storage, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void login() {
        if (Utils.isNetworkAvailable()) {
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCodes.API.LOGIN)
                    .autoLoadingCancel(Utils.getLoading(this, "Signing in..."))
                    .enque(Network.apis().login(new LoginRequest(email, password)))
                    .execute();
        } else {
            Notify.Toast(getString(R.string.no_internet_avail));
        }

    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK:  checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: google play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOGE_REQUEST);
            dialog.show();
        } else {
            Notify.Toast(getString(R.string.cannot_make_mapreq));
        }
        return false;

    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.LOGIN) {
            LoginResponse res = (LoginResponse) response.body();
            if (res != null) {
                Notify.Toast(res.getMessage());
                List<String> cookielist = response.headers().values("Set-Cookie");
                String jsessionid = (cookielist.get(0).split(";"))[0];
                String fullName = (cookielist.get(2).split(";"))[0];
                String userId = (cookielist.get(3).split(";"))[0];
                AppSession.put(Constants.IS_LOGGED_IN, true);
                AppSession.put("sid", jsessionid);
                AppSession.put("user_id", userId);
                AppSession.put("full_name", fullName);
                AppSession.put("email", email);
                AppSession.put("password", password);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.Toast(response.getMessage());
    }

    public void checkGPS() {
        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        getLocationAccess();
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();


        } else {
            getLocationAccess();
        }
    }

    private void getLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, Constants.FINE_lOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, COURSE_lOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS Location seems to be disabled, You have to enable it, Do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @SuppressLint("MissingPermission")
    private void getCurrLoc() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @SuppressLint("ApplySharedPref")
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    SharedPreferences.Editor editor = getSharedPreferences(MAIN_ACT, Context.MODE_PRIVATE).edit();
                    editor.putString("lati", "31.4818595180907");
                    editor.putString("longi", "74.34997037765945");
                    editor.commit();

                }
            });
        }
    }

    private void loginWithFirebase(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("email", email);
                            hashMap.put("status", "offline");
//                            hashMap.put("bio", "");
//                            hashMap.put("search", username.toLowerCase());
//                            if(dialog!=null){
//                                dialog.dismiss();
//                            }

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
//                                        Toast.makeText(LoginActivity.this, "OK", Toast.LENGTH_SHORT).show();

//                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
//                                        finish();
                                    }
                                }
                            });
                        } else {
//                            Toast.makeText(LoginActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}