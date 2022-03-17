package com.example.erpnext.activities;

import static android.content.ContentValues.TAG;

import static com.example.erpnext.adapters.MyTaskAdapter.MY_PREF;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.print.PdfPrinter;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.fcm.BgServiceForSPLoc;
import com.example.erpnext.fragments.AddFragment;
import com.example.erpnext.fragments.CRMFragment;
import com.example.erpnext.fragments.ChatRoomFragment;
import com.example.erpnext.fragments.CommunicationsFragment;
import com.example.erpnext.fragments.ContactsFragment;
import com.example.erpnext.fragments.CustomerGroupsFragment;
import com.example.erpnext.fragments.CustomersLocationFragment;
import com.example.erpnext.fragments.DeliveryNoteFragment;
import com.example.erpnext.fragments.HomeFragment;
import com.example.erpnext.fragments.ItemFragment;
import com.example.erpnext.fragments.ItemPriceFragment;
import com.example.erpnext.fragments.LandedCostFragment;
import com.example.erpnext.fragments.LeadSourcesFragment;
import com.example.erpnext.fragments.LeadsFragment;
import com.example.erpnext.fragments.LocationHistoryFragment;
import com.example.erpnext.fragments.LogsFragment;
import com.example.erpnext.fragments.MyTasksFragment;
import com.example.erpnext.fragments.OpportunitiesFragment;
import com.example.erpnext.fragments.POSProfileDetailFragment;
import com.example.erpnext.fragments.POSProfileListFragment;
import com.example.erpnext.fragments.PointOfSaleFragment;
import com.example.erpnext.fragments.PosInvoicesFragment;
import com.example.erpnext.fragments.ProfileFragment;
import com.example.erpnext.fragments.PurchaseReceiptFragment;
import com.example.erpnext.fragments.RetailFragment;
import com.example.erpnext.fragments.SalesPersonsFragment;
import com.example.erpnext.fragments.SettingsFragment;
import com.example.erpnext.fragments.StockBalanceFragment;
import com.example.erpnext.fragments.StockEntryFragment;
import com.example.erpnext.fragments.StockFragment;
import com.example.erpnext.fragments.StockLedgerFragment;
import com.example.erpnext.fragments.StockReconciliationFragment;
import com.example.erpnext.fragments.StockSummaryFragment;
import com.example.erpnext.fragments.TerritoryFragment;
import com.example.erpnext.fragments.WarehouseFragment;
import com.example.erpnext.models.FcmRes;
import com.example.erpnext.models.MenuMessage;
import com.example.erpnext.models.ProfileDoc;
import com.example.erpnext.fcm.BackgroundService;
import com.example.erpnext.network.ApiServices;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.ItemResponse;
import com.example.erpnext.network.serializers.response.MenuResponse;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.NetworkReceiver;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PdfPrinter.OnPdfGeneratedListener, View.OnClickListener, AdapterView.OnItemSelectedListener, OnNetworkResponse {
    public static DrawerLayout drawer;
    private final String doctye = "";
    public RelativeLayout container, appBar;
    BroadcastReceiver networkBroadcast;
    ItemResponse itemResponse;
    ProfileDoc profileDoc;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navView;
    private ImageView drawerToggle;
    private String itemName;
    private Spinner homeSpinner;

    public static void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT, true);
        }
    }

    public static void openDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT, true);
        } else drawer.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String language = preferences.getString("MyLang", "");
        if (language.startsWith("far")) {
            setLocale("fa");
        } else {
            setLocale("en");
        }

        getFcmToken();
        startBgService();
        startBgServiceForSpLoc();
        initViews();
        networkBroadcast = new NetworkReceiver();
        registerNetworkReceiver();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, HomeFragment.newInstance());
        transaction.commit();
        onBottomNavClick();
        onDrawerNavClick();
        if (Utils.isNetworkAvailable()) {
            getMenu();
        } else if (MainApp.database.menuDao().getMenu() != null) {
            for (MenuMessage message : MainApp.database.menuDao().getMenu().getMessage()) {
                if (message.getName().equalsIgnoreCase("Retail") || message.getName().equalsIgnoreCase("CRM")) {
                    navView.getMenu().add(message.getName());
                }
            }
            navView.getMenu().add(getString(R.string.logs));
        }

    }

    @SuppressLint("MissingPermission")
    private void startBgServiceForSpLoc() {
        Intent intent = new Intent(this, BgServiceForSPLoc.class);
        if (isMyServiceRunning(BgServiceForSPLoc.class)) {
            Log.d("isservice", "running");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(intent);
            } else {
                this.startService(intent);
            }
        }
    }
    @SuppressLint("MissingPermission")
    private void startBgService() {
        Intent intent = new Intent(this, BackgroundService.class);
        if (isMyServiceRunning(BackgroundService.class)) {
            Log.d("isservice", "running");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(intent);
            } else {
                this.startService(intent);
            }
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        container = findViewById(R.id.container);
        appBar = findViewById(R.id.appBar);
        homeSpinner = findViewById(R.id.home_spinner);
        navView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        drawerToggle = findViewById(R.id.drawer_toggle);


        List<String> stringList = new ArrayList<>();
        stringList.add("Home");
        stringList.add("Retail");
        String[] list = stringList.toArray(new String[0]);
        setSpinner(list, homeSpinner);
    }

//    private void setNavItemIconRight() {
//        for (int i = 0; i < 3; i++) {
//            navView.getMenu().getItem(i).setActionView(R.layout.notification_counter);
//        }
//    }

    private void setSpinner(String[] list, Spinner spinner) {
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(this, R.layout.checkout_spinner_text, list);
        spinnerAdapter.setDropDownViewResource(R.layout.checkout_spinner_layout);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
    }

    private void onDrawerNavClick() {
//        setNavItemIconRight();
        navView.setNavigationItemSelectedListener(this);
        drawerToggle.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        itemName = menuItem.getTitle().toString();
        if (menuItem.getTitle().equals(getString(R.string.retail))) {
            fragmentTrx(RetailFragment.newInstance(), null, "RetailFragment");

//            if (Utils.isNetworkAvailable()) {
//                getItem(menuItem.getTitle());
//
//            } else if (MainApp.database.menuItemsDao().getMenuItems(itemName) != null) {
//                ItemResponse res = MainApp.database.menuItemsDao().getMenuItems(itemName);
//                GsonBuilder builder = new GsonBuilder();
//                Gson gson = builder.create();
//                String responseToJsonString = gson.toJson(res);
//                Bundle bundle = new Bundle();
//                bundle.putString("retail_item", responseToJsonString);
//                fragmentTrx(RetailFragment.newInstance(), bundle, "RetailFragment");
//            } else Notify.Toast(getString(R.string.no_data_offline));
        } else if (menuItem.getTitle().equals(getString(R.string.crm))) {
            fragmentTrx(CRMFragment.newInstance(), null, "CRMFragment");

//            if (Utils.isNetworkAvailable()) {
//                getItem(menuItem.getTitle());
//            } else if (MainApp.database.menuItemsDao().getMenuItems(itemName) != null) {
//                ItemResponse res = MainApp.database.menuItemsDao().getMenuItems(itemName);
//                GsonBuilder builder = new GsonBuilder();
//                Gson gson = builder.create();
//                String responseToJsonString = gson.toJson(res);
//                Bundle bundle = new Bundle();
//                bundle.putString("CRM", responseToJsonString);
//                fragmentTrx(CRMFragment.newInstance(), bundle, "CRMFragment");
//            } else Notify.Toast(getString(R.string.no_data_offline));
        } else if (menuItem.getTitle().equals(getString(R.string.stock))) {
            fragmentTrx(StockFragment.newInstance(), null, "StockFragment");

//            if (Utils.isNetworkAvailable()) {
//                getItem(menuItem.getTitle());
//            }
        }
        else if (menuItem.getTitle().equals(getString(R.string.customerlocation))) {
            fragmentTrx(CustomersLocationFragment.newInstance(), null, "CustomersLocationFragment");
        } else if (menuItem.getTitle().equals(getString(R.string.my_tasks))) {
            fragmentTrx(MyTasksFragment.newInstance(), null, "MyTasksFragment");
        } else if (menuItem.getTitle().equals(getString(R.string.locationhistory))) {
            fragmentTrx(LocationHistoryFragment.newInstance(), null, "LocationHistoryFragment");
        }
        else if (menuItem.getTitle().equals(getString(R.string.chatroom))) {
            fragmentTrx(ChatRoomFragment.newInstance(), null, "ChatRoomFragment");
        }
        else if (menuItem.getTitle().equals(getString(R.string.logs))) {
            fragmentTrx(LogsFragment.newInstance(), null, "LogsFragment");
        }
        else if (menuItem.getTitle().equals(getString(R.string.settings))) {
            fragmentTrx(SettingsFragment.newInstance(), null, "SettingsFragment");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onBottomNavClick() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.getMenu().getItem(2).setTitle(String.valueOf(AppSession.get("full_name").charAt(10)));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.home:
                        if (fm.getBackStackEntryCount() > 0) {
                            clearAllFragmentStack();
                            fragmentTrx(HomeFragment.newInstance(), null, "HomeFragment");
                        }
                        break;
                    case R.id.search_bar:
                        fragmentTrx(PosInvoicesFragment.newInstance(), null, "SearchFragment");

                        break;
                    case R.id.logout:
                        fragmentTrx(ProfileFragment.newInstance(), null, "ProfileFragment");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private FragmentManager getLastFragmentManagerWithBack(FragmentManager fm) {
        FragmentManager fmLast = fm;
        List<Fragment> fragments = fm.getFragments();
        for (Fragment f : fragments) {
            if ((f.getChildFragmentManager() != null) && (f.getChildFragmentManager().getBackStackEntryCount() > 0)) {
                fmLast = f.getFragmentManager();
                FragmentManager fmChild = getLastFragmentManagerWithBack(f.getChildFragmentManager());
                if (fmChild != fmLast)
                    fmLast = fmChild;
            }
        }
        return fmLast;
    }

    public void clearAllFragmentStack() {
        FragmentManager fm = getLastFragmentManagerWithBack(getSupportFragmentManager());
        if (fm.getBackStackEntryCount() > 0) {
            for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                fm.popBackStack();
            }
            bottomNavigationView.getMenu().getItem(0).setChecked(true);

        }
    }

    public void fragmentTrx(Fragment fragment, Bundle bundle, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment, tag);
        fragment.setArguments(bundle);
        transaction.addToBackStack(fragment.getTag());
        transaction.commitAllowingStateLoss();
    }

    public void popBackStack() {
        FragmentManager fm = getLastFragmentManagerWithBack(getSupportFragmentManager());
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT, true);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT, true);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (f instanceof HomeFragment || f instanceof POSProfileListFragment
                    || f instanceof RetailFragment || f instanceof PosInvoicesFragment
                    || f instanceof PointOfSaleFragment || f instanceof POSProfileDetailFragment
                    || f instanceof AddFragment || f instanceof CRMFragment || f instanceof LeadsFragment
                    || f instanceof StockFragment || f instanceof StockEntryFragment
                    || f instanceof StockBalanceFragment || f instanceof PurchaseReceiptFragment || f instanceof DeliveryNoteFragment
                    || f instanceof StockLedgerFragment || f instanceof ItemPriceFragment
                    || f instanceof StockReconciliationFragment || f instanceof WarehouseFragment
                    || f instanceof LandedCostFragment || f instanceof ItemFragment
                    || f instanceof StockSummaryFragment || f instanceof OpportunitiesFragment
                    || f instanceof CustomerGroupsFragment || f instanceof TerritoryFragment
                    || f instanceof CustomersLocationFragment || f instanceof LeadSourcesFragment
                    || f instanceof CommunicationsFragment || f instanceof ContactsFragment
                    || f instanceof SalesPersonsFragment) {

                popBackStack();
            } else
                clearAllFragmentStack();
            return;
        } else super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.drawer_toggle) {
            openDrawer();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = parent.getItemAtPosition(position).toString();
        if (name.equalsIgnoreCase("home")) {
            fragmentTrx(HomeFragment.newInstance(), null, "HomeFragment");
        } else if (name.equalsIgnoreCase("retail")) {
            fragmentTrx(RetailFragment.newInstance(), null, "RetailFragment");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getMenu() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.MENU)
                .autoLoadingCancel(Utils.getLoading(this, "Loading"))
                .enque(Network.apis().getMenu(AppSession.get("sid"), null, null))
                .execute();
    }

    private void getItem(CharSequence item) {
        itemName = item.toString();
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.ITEM_DETAIL)
                .autoLoadingCancel(Utils.getLoading(this, "Please wait"))
                .enque(Network.apis().getItem(item))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.MENU) {
            if (response.code() == 200) {
                MenuResponse res = (MenuResponse) response.body();
                Room.saveMenu(res);
                MainApp.getDataWorker();
//                for (MenuMessage message : res.getMessage()) {
//                    if (message.getName().equalsIgnoreCase("Retail") || message.getName().equalsIgnoreCase("CRM") || message.getName().equalsIgnoreCase("Stock")) {
//                        navView.getMenu().add(message.getName());
//                    }
//                }
                navView.getMenu().add(getString(R.string.crm));
                navView.getMenu().add(getString(R.string.retail));
                navView.getMenu().add(getString(R.string.stock));
                navView.getMenu().add(getString(R.string.customerlocation));
                navView.getMenu().add(getString(R.string.my_tasks));
                navView.getMenu().add(getString(R.string.locationhistory));
                navView.getMenu().add(getString(R.string.chatroom));
                navView.getMenu().add(getString(R.string.logs));
                navView.getMenu().add(getString(R.string.settings));
                if (navView.getMenu().hasVisibleItems()) {
//                    getItem(navView.getMenu().getItem(0).getTitle());
                }
            }
        } else if ((int) tag == RequestCodes.API.ITEM_DETAIL) {
            ItemResponse res = (ItemResponse) response.body();
            itemResponse = res;
            res.itemName = itemName;
            Room.saveMenuItems(res);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String responseToJsonString = gson.toJson(itemResponse);
            Bundle bundle = new Bundle();

//            if (itemResponse.getMessage().getCards().getItems().get(0).getParent().equalsIgnoreCase("Retail")) {
//                bundle.putString("retail_item", responseToJsonString);
//                fragmentTrx(RetailFragment.newInstance(), bundle, "RetailFragment");
//            } else if (itemName.equalsIgnoreCase("CRM")) {
//                bundle.putString("CRM", responseToJsonString);
//                fragmentTrx(CRMFragment.newInstance(), bundle, "CRMFragment");
//            } else if (itemName.equalsIgnoreCase("Stock")) {
//                bundle.putString("Stock", responseToJsonString);
//                fragmentTrx(StockFragment.newInstance(), bundle, "StockFragment");
//            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        Log.d(TAG, "onFailure: api");
        Notify.Toast(response.getMessage());
        Utils.cancelLoading();
    }


    protected void registerNetworkReceiver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(networkBroadcast, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(networkBroadcast, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkReceiver() {
        try {
            unregisterReceiver(networkBroadcast);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterNetworkReceiver();
        super.onDestroy();
    }

    @Override
    public void onPdfGenerated(File file) {

    }

    private void getFcmToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(Constraints.TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.wtf("fcmtokemn", token);
                        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://75.119.143.175:8080/ErpNext/")
                                .addConverterFactory(GsonConverterFactory.create()).build();
                        ApiServices apiServices = retrofit.create(ApiServices.class);
                        Call<FcmRes> call = apiServices.getFcmToken(AppSession.get("email"), token);
                        call.enqueue(new Callback<FcmRes>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<FcmRes> call, Response<FcmRes> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getStatus().toString().equals("200")) {
//                                        Toast.makeText(MainActivity.this, "token", Toast.LENGTH_SHORT).show();

                                    } else {
//                                        Toast.makeText(MainActivity.this, "PProcess Failed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
//                                    Toast.makeText(MainActivity.this, "Prrocess Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<FcmRes> call, Throwable t) {
                                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                            }

                        });

                    }
                });
    }
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

    }

}