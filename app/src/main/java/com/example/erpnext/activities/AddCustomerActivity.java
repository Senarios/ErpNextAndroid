package com.example.erpnext.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.erpnext.R;
import com.example.erpnext.models.AddCustomerOfflineModel;
import com.example.erpnext.models.MyTaskOfflineModel;
import com.example.erpnext.models.ProfileDoc;
import com.example.erpnext.models.RealPathUtil;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.databinding.ActivityAddCustomerBinding;
import com.example.erpnext.models.AddCustomerRes;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddCustomerRepo;
import com.example.erpnext.roomDB.data.DBSearchLink;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.AddCustomerViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCustomerActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkResponse {

    private ActivityAddCustomerBinding binding;
    private AddCustomerViewModel viewModel;
    HashMap<String, String> data = new HashMap<>();
    AutoCompleteTextView cus_Name, phone, reference, lat, lng;
    String searchDoctype = "", query = "", filters = "", baseTotal, changeAmount = "0", outstandingAmount = "0", paidAmount = "0";
    private ProfileDoc profileDoc;
    FusedLocationProviderClient fusedLocationProviderClient;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.INSTANCE.setCurrentActivity(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer);
        viewModel = new ViewModelProvider(this).get(AddCustomerViewModel.class);

        setClickListeners();
//        setFocusListeners();
//        setOnItemSelectListeners();
//        setObservers();
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.selectImage.setOnClickListener(this);
        binding.cusCurrentLoc.setOnClickListener(this);
        binding.saveCustomer.setOnClickListener(this);
        binding.selectImageButton.setOnClickListener(this);
        cus_Name = findViewById(R.id.customer_namee);
        phone = findViewById(R.id.phone_no);
        reference = findViewById(R.id.reference);
        lat = findViewById(R.id.shop_latitude);
        lng = findViewById(R.id.shop_longitude);

        cus_Name.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (Utils.isNetworkAvailable())
                    getLinkSearch(RequestCodes.API.LINK_SEARCH_CUSTOMER);
                else {
                    DBSearchLink.load(this, "Customer", "erpnext.controllers.queries.customer_query", cus_Name);
                }
            }
        });

//        binding.customerGroup.setText("All Customer Groups");
//        binding.territory.setText("All Territories");
//        binding.type.setText("Company");
//        data.put("customer_group", "All Customer Groups");
//        data.put("territory", "All Territories");
//        data.put("customer_type", "Company");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_image_button:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 21);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.cus_current_loc:
                getCurrentLocation();
                break;
            case R.id.save_customer:
                if (!fieldErrorExist()) {
                    addCustomer(cus_Name.getText().toString(), phone.getText().toString(), reference.getText().toString(), Double.parseDouble(lat.getText().toString()), Double.parseDouble(lng.getText().toString()));
                }
//                if (!fieldErrorExist()) {
//                    viewModel.saveDocApi(data);
//                    setSaveObserver();
//                }
                break;
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Context context = AddCustomerActivity.this;
            path = RealPathUtil.getRealPath(context, uri);
            Log.wtf("pathimage", path);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            binding.selectImageButton.setVisibility(View.GONE);
            binding.selectImage.setVisibility(View.VISIBLE);
            binding.selectImage.setImageBitmap(bitmap);
        }

    }

    private void getLinkSearch(int requestCode) {

        if (requestCode == RequestCodes.API.LINK_SEARCH_CUSTOMER) {
            searchDoctype = "Customer";
            query = "erpnext.controllers.queries.customer_query";
            filters = null;
        } else if (requestCode == RequestCodes.API.LINK_SEARCH_ITEM_GROUP) {
            searchDoctype = "Item Group";
            query = "erpnext.selling.page.point_of_sale.point_of_sale.item_group_query";
            filters = "{\"pos_profile\": " + "\"" + profileDoc.getName() + "\"" + "}";
        } else if (requestCode == RequestCodes.API.LINK_SEARCH_UMO) {
            searchDoctype = "UOM";
            query = null;
            filters = null;
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .autoLoadingCancel(Utils.getLoading(this, "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", searchDoctype, "0", filters, "", query)))
                .execute();
    }

    private void setObservers() {
//        viewModel.getSearchResult().observe(this, searchLinkResponse -> {
//            if (searchLinkResponse != null && searchLinkResponse.getResults() != null && searchLinkResponse.getResults() != null && !searchLinkResponse.getResults().isEmpty()) {
//                if (searchLinkResponse.getRequestCode() == RequestCodes.API.SEARCH_TERRITORY) {
//                    Utils.setSearchAdapter(this, binding.territory, searchLinkResponse);
//                } else if (searchLinkResponse.getRequestCode() == RequestCodes.API.CUSTOMER_GROUP) {
//                    Utils.setSearchAdapter(this, binding.customerGroup, searchLinkResponse);
//                }
//                AddCustomerRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
//            }
//        });

    }

    private void setFocusListeners() {
//        binding.customerGroup.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                viewModel.getSearchLinkApi("Customer Group", "", null, "0", RequestCodes.API.CUSTOMER_GROUP);
//            }
//        });
//        binding.territory.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                viewModel.getSearchLinkApi("Territory", "", null, "0", RequestCodes.API.SEARCH_TERRITORY);
//            }
//        });
//        binding.type.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                SearchLinkResponse searchLinkResponse = new SearchLinkResponse();
//                List<SearchResult> searchResults = new ArrayList<>();
//                List<String> statusList = new ArrayList<>();
//                statusList.add("Company");
//                statusList.add("Individual");
//                for (String staus : statusList) {
//                    SearchResult searchResult = new SearchResult();
//                    searchResult.setValue(staus);
//                    searchResults.add(searchResult);
//                }
//                searchLinkResponse.setResults(searchResults);
//                Utils.setSearchAdapter(this, binding.type, searchLinkResponse);
//            }
//        });

    }

    private void setSaveObserver() {
        viewModel.docSaved().observe(this, doc -> {
            if (doc != null) {
                Intent intent = new Intent();
                intent.putExtra("saved", true);
                setResult(RESULT_OK, intent);
                Notify.Toast(getString(R.string.successfully_created));
                AddCustomerRepo.getInstance().savedDoc.setValue(null);
                finish();
            }
        });
    }

    private void setOnItemSelectListeners() {
//        binding.customerGroup.setOnItemClickListener((parent, view, position, id) -> {
//            String selected = (String) parent.getItemAtPosition(position);
//            data.put("customer_group", selected);
//        });
//        binding.territory.setOnItemClickListener((parent, view, position, id) -> {
//            String selected = (String) parent.getItemAtPosition(position);
//            data.put("territory", selected);
//        });
//        binding.fullName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString() != null && !s.toString().isEmpty()) {
//                    data.put("customer_name", s.toString());
//                }
//            }
//        });
//        binding.email.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString() != null && !s.toString().isEmpty()) {
//                    data.put("email_id", s.toString());
//                }
//            }
//        });
//        binding.mobile.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString() != null && !s.toString().isEmpty()) {
//                    data.put("mobile_no", s.toString());
//                }
//            }
//        });

    }

    public void saveCustomerForOffline(String image, String cusName, String phone, String reference,double lat,double lng) {
        AddCustomerOfflineModel addCustomerOfflineModel = new AddCustomerOfflineModel();
        addCustomerOfflineModel.setImage(image);
        addCustomerOfflineModel.setCustomerName(cusName);
        addCustomerOfflineModel.setPhoneNo(phone);
        addCustomerOfflineModel.setReference(reference);
        addCustomerOfflineModel.setLattitude(lat);
        addCustomerOfflineModel.setLongitude(lng);
        MainApp.database.addCustomerDao().insertCustomer(addCustomerOfflineModel);
        Toast.makeText(AddCustomerActivity.this, getString(R.string.offline_save), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    private void addCustomer(String cus_name, String phone, String reference, double lat, double lng) {
        Utils.showLoading(this);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.frappeurl))
                .addConverterFactory(GsonConverterFactory.create()).build();

        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody cusFullName =
                RequestBody.create(MediaType.parse("multipart/form-data"), cus_name);
        RequestBody cusPhone =
                RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        RequestBody cusReference =
                RequestBody.create(MediaType.parse("multipart/form-data"), reference);
        RequestBody cusLat =
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(lat));
        RequestBody cusLng =
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(lng));

        ApiServices apiServices = retrofit.create(ApiServices.class);
        Call<AddCustomerRes> call = apiServices.addCustomer(body, cusFullName, cusPhone, cusReference, cusLat, cusLng);
        call.enqueue(new Callback<AddCustomerRes>() {
            @Override
            public void onResponse(Call<AddCustomerRes> call, Response<AddCustomerRes> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("200")) {
                        Utils.dismiss();
                        Toast.makeText(getApplicationContext(), "Customer Added", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Utils.dismiss();
                        saveCustomerForOffline(path,cus_name,phone,reference,lat,lng);
                        Toast.makeText(getApplicationContext(), "Customer already exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Utils.dismiss();
                    saveCustomerForOffline(path,cus_name,phone,reference,lat,lng);
                    Toast.makeText(getApplicationContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddCustomerRes> call, Throwable t) {
                Utils.dismiss();
                saveCustomerForOffline(path,cus_name,phone,reference,lat,lng);
//                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    lat.setText(String.valueOf(location.getLatitude()));
                    lng.setText(String.valueOf(location.getLongitude()));
                }
            });
        }
    }

    private boolean fieldErrorExist() {
        if (binding.customerNamee.getText().toString() == null || binding.customerNamee.getText().toString().isEmpty()) {
            Notify.Toast(getString(R.string.add_customer_name));
            return true;
        } else if (binding.phoneNo.getText().toString() == null || binding.phoneNo.getText().toString().isEmpty()) {
            Notify.Toast(getString(R.string.add_phone));
            return true;
        } else if (binding.shopLatitude.getText().toString() == null || binding.shopLatitude.getText().toString().isEmpty()) {
            Notify.Toast(getString(R.string.add_cus_lat));
            return true;
        } else if (binding.shopLongitude.getText().toString() == null || binding.shopLongitude.getText().toString().isEmpty()) {
            Notify.Toast(getString(R.string.add_cus_lng));
            return true;
        } else if (binding.selectImage.getDrawable() == null) {
            Notify.Toast(getString(R.string.add_cus_img));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        AddCustomerRepo.getInstance().searchLinkResponseMutableLiveData.setValue(null);
        AddCustomerRepo.getInstance().savedDoc.setValue(null);
        super.onDestroy();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.LINK_SEARCH_CUSTOMER) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            DBSearchLink.save(res, searchDoctype, query);
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);
                cus_Name.setAdapter(adapter);
                cus_Name.showDropDown();
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Notify.ToastLong(response.getServerMessages());
    }
}