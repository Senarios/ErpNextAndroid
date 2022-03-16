package com.example.erpnext.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.erpnext.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mGoogleMap;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    LatLng sydney = new LatLng(-34, 151);
    LatLng tamworth = new LatLng(-31.083332, 150.916672);

    ArrayList<String> title = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_details);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        arrayList.add(sydney);
        arrayList.add(tamworth);
        title.add("Sydney");
        title.add("Tamworth");
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                mGoogleMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(j))));
            }
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String markerTitle = marker.getTitle();
                Intent i = new Intent(MapDetailsActivity.this, MapDetailsActivity.class);
                startActivity(i);
                return false;
            }
        });
    }
}