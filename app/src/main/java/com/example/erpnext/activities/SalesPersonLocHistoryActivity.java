package com.example.erpnext.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.erpnext.R;
import com.example.erpnext.models.SPLocHistoryDatum;
import com.example.erpnext.models.SPLocHistoryRes;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SalesPersonLocHistoryActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraMoveStartedListener {

    private ArrayList<LatLng> latLngArrayList;
    private ArrayList<String> locationNameArraylist;
    private ArrayList<String> locationReferenceArraylist;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_person_loc_history);
        String date = getIntent().getStringExtra("selectedDate");
        String email = getIntent().getStringExtra("selectedSPEmail");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        latLngArrayList = new ArrayList<>();
        locationNameArraylist = new ArrayList<String>();
        locationReferenceArraylist = new ArrayList<String>();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Utils.showLoading(this);
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.RED);
        polyOptions.width(5);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.frappeurl))
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        String date = getIntent().getStringExtra("selectedDate");
        String email = getIntent().getStringExtra("selectedSPEmail");
        Call<SPLocHistoryRes> call = apiServices.getSPLocHistory(email, date);
        call.enqueue(new Callback<SPLocHistoryRes>() {
            @Override
            public void onResponse(Call<SPLocHistoryRes> call, Response<SPLocHistoryRes> response) {
                if (response.isSuccessful()) {
                    SPLocHistoryRes resObj = response.body();
                    List<SPLocHistoryDatum> list = resObj.getData();
                    if (resObj.getStatus().toString().equals("200")) {
                        for (int i = 0; i < list.size(); i++) {
                            String lat = list.get(i).getLat();
                            String lng = list.get(i).getLng();
                            String shopname = list.get(i).getSalesPersonEmail();
                            String shopreference = list.get(i).getDate();
                            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                            polyOptions.addAll(Collections.singleton(latLng));
                            Log.wtf("latlong", String.valueOf(latLng));
                            latLngArrayList.add(latLng);
                            locationNameArraylist.add(shopname);
                            locationReferenceArraylist.add(shopreference);
                            for (int j = 0; j < latLngArrayList.size(); j++) {
                                // adding marker to each location on google maps
                                Utils.dismiss();
                                mGoogleMap.addMarker(new MarkerOptions().position(latLngArrayList.get(j)).title("Email: " + locationNameArraylist.get(i))
                                        .snippet("Date: " + locationReferenceArraylist.get(i))
                                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.salesman_icon))));
                                // below line is use to move camera.
                                mGoogleMap.addPolyline(polyOptions);
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            }
                        }

                    } else {
                        onBackPressed();
                        Notify.Toast(getString(R.string.check_email_or_date));
                    }

                } else {
                    Utils.dismiss();
                    Toast.makeText(SalesPersonLocHistoryActivity.this, R.string.process_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SPLocHistoryRes> call, Throwable t) {
                Utils.dismiss();
                Toast.makeText(SalesPersonLocHistoryActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // adding on click listener to marker of google maps.
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // on marker click we are getting the title of our marker
                // which is clicked and displaying it in a toast message.
                String markerName = marker.getTitle();
//                Toast.makeText(getContext(), "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_customer_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    @Override
    public void onCameraMoveStarted(int i) {

    }
}