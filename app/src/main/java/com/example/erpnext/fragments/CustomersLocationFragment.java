package com.example.erpnext.fragments;

import static android.app.Activity.RESULT_OK;
import static com.example.erpnext.utils.Constants.COURSE_lOCATION;
import static com.example.erpnext.utils.RequestCodes.LOCATION_REQUEST_CODE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.erpnext.R;
import com.example.erpnext.adapters.MyTaskAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.CustomersLocationFragmentBinding;
import com.example.erpnext.models.CustomerInfo;
import com.example.erpnext.models.CustomerRes;
import com.example.erpnext.models.Info;
import com.example.erpnext.models.MyTaskRes;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.utils.Constants;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.CustomersLocationViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomersLocationFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener {

    //    private static final String MY_PREFF = "mypref";
    private CustomersLocationViewModel mViewModel;
    private CustomersLocationFragmentBinding binding;
    private Location currentLocation;
    private GoogleMap mGoogleMap;
    private TextView name;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private View liveButtonView;
    private RelativeLayout drawerToggle, findParking;
    private ImageView close, myPin;
    //    PlacesClient placesClient;
//    String apiKey = "AIzaSyCYPyTw9pX488kIehQb-yY0v-A5JRTKqFo";
    private Marker centerMarker;
    private LatLng mapCenterLatLng;
    private String pinAddress;
    private List<String> titlesList = new ArrayList<>();
    //    private List<LatLng> latLngList = new ArrayList<>();
    private MarkerOptions myMarker = new MarkerOptions();
    MyTaskAdapter myTaskAdapter;


    private ArrayList<LatLng> latLngArrayList;
    private ArrayList<String> locationNameArraylist;
    private ArrayList<String> locationReferenceArraylist;

    public static CustomersLocationFragment newInstance() {
        return new CustomersLocationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CustomersLocationViewModel.class);
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        binding = CustomersLocationFragmentBinding.inflate(inflater, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        latLngArrayList = new ArrayList<>();
        locationNameArraylist = new ArrayList<String>();
        locationReferenceArraylist = new ArrayList<String>();

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.drawerToggle:
//                ((UserMainActivity) getActivity()).openDrawer();
//                break;
//            case R.id.findParking:
//                startActivityForResult(new Intent(getActivity(), FindParkingActivity.class), RequestCodes.FIND_PARKING);
//            default:
//                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
//            if (requestCode == RequestCodes.FIND_PARKING) {
//                parking = (Parking) data.getSerializableExtra("parking");
//                LatLng latLongs = new LatLng(parking.getLatLongs().getLatitude(), parking.getLatLongs().getLongitude());
//                moveCameraFor(latLongs, DEFAULT_ZOOM);
//
//            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://75.119.143.175:8080/ErpNext/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<CustomerRes> call = apiServices.getCustomers();
        call.enqueue(new Callback<CustomerRes>() {
            @Override
            public void onResponse(Call<CustomerRes> call, Response<CustomerRes> response) {
                if (response.isSuccessful()) {
                    Utils.dismiss();
                    CustomerRes resObj = response.body();
                    List<CustomerInfo> list = resObj.getInfo();
                    for (int i = 0; i < list.size(); i++) {
                        String lat = list.get(i).getShopLat();
                        String lng = list.get(i).getShopLng();
                        String shopname = list.get(i).getName().trim();
                        String shopreference = list.get(i).getReference().trim();
                        LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                        Log.wtf("latlong", String.valueOf(latLng));
                        latLngArrayList.add(latLng);
                        locationNameArraylist.add(shopname);
                        locationReferenceArraylist.add(shopreference);
                        for (int j = 0; j < latLngArrayList.size(); j++) {

                            // adding marker to each location on google maps
                            mGoogleMap.addMarker(new MarkerOptions().position(latLngArrayList.get(j)).title("Shop name: " + locationNameArraylist.get(i))
                                    .snippet("Shop refernce: " + locationReferenceArraylist.get(i)));

                            // below line is use to move camera.
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
                    }

                } else {
                    Utils.dismiss();
                    Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerRes> call, Throwable t) {
                Utils.dismiss();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
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

//            centerMarker = mGoogleMap.addMarker(new MarkerOptions().position(mGoogleMap.getCameraPosition().target)
//                    .title("Center of Map")
//                    .icon(BitmapDescriptorFactory.fromBitmap(Converters.getBitmapFromVectorDrawable(getContext(), R.drawable.ic_placeholder))));
    }

    @Override
    public void onCameraIdle() {
        mapCenterLatLng = mGoogleMap.getCameraPosition().target;
//                    UserHomeFragment.this.animateMarker(centerMarker, mapCenterLatLng, false);
//        parkingMarkers();
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(mapCenterLatLng.latitude, mapCenterLatLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            Address address = addresses.get(0);
            pinAddress = address.getAddressLine(0);
            String city = address.getLocality();
            String state = address.getAdminArea();
            String area = address.getSubLocality();
            String country = address.getCountryName();
            String postalCode = address.getPostalCode();
            String knownName = address.getFeatureName();
//            Notify.Toast(pinAddress);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        if (!parkingList.isEmpty()) {
//            for (Parking parking : parkingList) {
//                marker.showInfoWindow();
//                if (parking.getLatLongs().getLongitude() == marker.getPosition().longitude &&
//                        parking.getLatLongs().getLatitude() == marker.getPosition().latitude) {
//                    AvailableParkingBottomSheetFragment bottomSheet = new AvailableParkingBottomSheetFragment();
//                    GsonBuilder builder = new GsonBuilder();
//                    Gson gson = builder.create();
//                    String lodgeToJsonString = gson.toJson(parking);
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("isUser", true);
//                    bundle.putString("parking", lodgeToJsonString);
//                    bottomSheet.setArguments(bundle);
//                    bottomSheet.show(getActivity().getSupportFragmentManager(), "exampleBottomSheet");
////                    GsonBuilder builder = new GsonBuilder();
////                    Gson gson = builder.create();
////                    String lodgeToJsonString = gson.toJson(parking);
////                    Bundle bundle = new Bundle();
////                    bundle.putBoolean("isUser", true);
////                    bundle.putString("parking", lodgeToJsonString);
////                    ParkingDetailsFragment fragment = new ParkingDetailsFragment();
////                    fragment.setArguments(bundle);
////                    getFragmentManager().beginTransaction()
////                            .replace(R.id.fragmentContainer, fragment)
////                            .addToBackStack(fragment.getTag())
////                            .commit();
//                }
//            }
//        }
        return false;
    }


    @Override
    public void onCameraMoveStarted(int i) {

    }

    private void fetchLastLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Constants.FINE_lOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), COURSE_lOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }
        Task<Location> task = mFusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(CustomersLocationFragment.this);

                    liveButtonView = supportMapFragment.getView();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                break;

            }
        }
    }

    private void setLiveButtonBottomRight() {
        if (liveButtonView != null &&
                liveButtonView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) liveButtonView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 80, 30, 30);
        }

    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (title != "My Location") {
            MarkerOptions marker = new MarkerOptions().position(latLng).title(title);
            mGoogleMap.addMarker(marker);
        }
        hideSoftKeyboard();
    }

    private void moveCameraFor(LatLng latLng, float zoom) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        hideSoftKeyboard();
    }

    public void checkGPS() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else {
            fetchLastLocation();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Your GPS Location seems to be disabled, You have to enable it, do you want to enable it?")
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection projection = mGoogleMap.getProjection();
        Point startPoint = projection.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = projection.fromScreenLocation(startPoint);
        final long duration = 500;
        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    private void hideSoftKeyboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}