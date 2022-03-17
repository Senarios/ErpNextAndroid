package com.example.erpnext.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.erpnext.R;
import com.example.erpnext.activities.MainActivity;
import com.example.erpnext.adapters.HomeItemsAdapter;
import com.example.erpnext.adapters.SelectOPEAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.CheckOPECallback;
import com.example.erpnext.models.CheckOpeningEntry;
import com.example.erpnext.models.HomeItemsModel;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.CheckOpeningEntryResponse;
import com.example.erpnext.network.serializers.response.GetProfileDocResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, OnNetworkResponse, CheckOPECallback {

    private SelectOPEAdapter selectOPEAdapter;
    Dialog dialog;
    private ImageView drawerToggle;
    private GridView gridView;
    ArrayList<HomeItemsModel> itemsList = new ArrayList<>();
    RecyclerView recyclerView;


    public HomeFragment() {
        // Required empty public constructor
        setDrawer(true);
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        initViews(view);
        setOnClickListeners();

        homeFeatures();

        return view;
    }

    private void homeFeatures() {
        itemsList = new ArrayList<>();
        itemsList.add(new HomeItemsModel(getString(R.string.point_of_sale), R.drawable.pos));
        itemsList.add(new HomeItemsModel(getString(R.string.pos_opening_entry), R.drawable.posopen));
        itemsList.add(new HomeItemsModel(getString(R.string.pos_closing_entry), R.drawable.posclose));
        itemsList.add(new HomeItemsModel(getString(R.string.customerr), R.drawable.customerr));
        itemsList.add(new HomeItemsModel(getString(R.string.customerlocation), R.drawable.cuslocation));
        itemsList.add(new HomeItemsModel(getString(R.string.my_tasks), R.drawable.mytask));
        itemsList.add(new HomeItemsModel(getString(R.string.chatroom), R.drawable.chatroom));
        itemsList.add(new HomeItemsModel(getString(R.string.stockentryy), R.drawable.stockentry));
        itemsList.add(new HomeItemsModel(getString(R.string.stock_summary), R.drawable.stocksummary));
        itemsList.add(new HomeItemsModel(getString(R.string.locationhistory), R.drawable.lochistory));
        HomeItemsAdapter homeItemsAdapter = new HomeItemsAdapter(itemsList, getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeItemsAdapter);
        homeItemsAdapter.setOnItemClickListener(new HomeItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();

                if (position == 0) {
                    if (Utils.isNetworkAvailable()) {
                        checkOpeningEntry();
                    } else if (MainApp.database.reportViewDao().getReportViewResponse("POS Opening Entry") != null) {
                        ReportViewResponse response = MainApp.database.reportViewDao().getReportViewResponse("POS Opening Entry");
                        boolean status = false;
                        String name = "";
                        if (response.getReportViewMessage() != null) {
                            for (List<String> stringList : response.getReportViewMessage().getValues()) {
                                if (stringList.get(1).equalsIgnoreCase(AppSession.get("email")) && stringList.get(16).equalsIgnoreCase("Open")) {
                                    if (MainApp.database.docProfileDao().getProfileDocResponse(stringList.get(18)) != null) {
                                        name = stringList.get(18);
                                        status = true;
                                        break;
                                    }
                                }
                            }
                            if (status) {
                                getProfileDocFromDB(name);
                            } else Notify.Toast(getString(R.string.create_opening_entry));
                        }

                    } else Notify.Toast(getString(R.string.create_opening_entry));
                }
                if (position == 1) {
                    bundle.putString("docType", "POS Opening Entry");
                    fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
                }
                if (position == 2) {
                    bundle.putString("docType", "POS Closing Entry");
                    fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
                }
                if (position == 3) {
                    fragmentTrx(CustomersFragment.newInstance(), null, "CustomerFragment");
                }
                if (position == 4) {
                    fragmentTrx(CustomersLocationFragment.newInstance(), null, "CustomersLocationFragment");
                }
                if (position == 5) {
                    fragmentTrx(MyTasksFragment.newInstance(), null, "MyTasksFragment");
                }
                if (position == 6) {
                    fragmentTrx(ChatRoomFragment.newInstance(), null, "ChatRoomFragment");
                }
                if (position == 7) {
                    fragmentTrx(StockEntryFragment.newInstance(), null, "StockEntryFragment");
                }
                if (position == 8) {
                    fragmentTrx(StockSummFragment.newInstance(), null, "StockSummaryFragment");
                }
                if (position == 9) {
                    fragmentTrx(LocationHistoryFragment.newInstance(), null, "LocationHistoryFragment");
                }
                itemsList.clear();
            }
        });

    }

    private void setOnClickListeners() {
        drawerToggle.setOnClickListener(this);
    }

    private void initViews(View view) {
        drawerToggle = view.findViewById(R.id.drawer_toggle);
        recyclerView = view.findViewById(R.id.recycler_view_home);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_toggle:
                MainActivity.openDrawer();
                break;
        }
    }

    public void fragmentTrx(Fragment fragment, Bundle bundle, String tag) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment, tag);
        fragment.setArguments(bundle);
        transaction.addToBackStack(fragment.getTag());
        transaction.commitAllowingStateLoss();
    }

    private void checkOpeningEntry() {
        NetworkCall.make()
                .setCallback((OnNetworkResponse) this)
                .setTag(RequestCodes.API.CHECK_OPENING_ENTRY)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Loading"))
                .enque(Network.apis().checkOpeningEntry(AppSession.get("email")))
                .execute();
    }

    private void getProfileDocFromDB(String pos_profile) {
        GetProfileDocResponse response = MainApp.database.docProfileDao().getProfileDocResponse(pos_profile);
        if (response.getProfileDoc() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("doc", new Gson().toJson(response.getProfileDoc()));
            ((MainActivity) getActivity()).fragmentTrx(PointOfSaleFragment.newInstance(), bundle, "PointOfSaleFragment");
        } else Notify.Toast(getString(R.string.no_profile_offline));
    }

    private void selectOPEDialog(List<CheckOpeningEntry> checkOpeningEntryList) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_ope_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        RecyclerView enteriesRV = dialog.findViewById(R.id.ope_enteries);
        if (checkOpeningEntryList != null && !checkOpeningEntryList.isEmpty()) {
            setOPEAdapter(checkOpeningEntryList, enteriesRV);
        }
        dialog.setOnCancelListener(dialog -> homeFeatures());
    }

    private void setOPEAdapter(List<CheckOpeningEntry> itemList, RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        selectOPEAdapter = new SelectOPEAdapter(getContext(), itemList, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(selectOPEAdapter);
    }

    private void getProfileDoc(String name) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_PROFILE_DOC)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Please wait.."))
                .enque(Network.apis().getProfileDoc("POS Profile", name))
                .execute();
    }

    @Override
    public void onEntryClick(CheckOpeningEntry openingEntry) {
        if (Utils.isNetworkAvailable()) {
            getProfileDoc(openingEntry.getPosProfile());
            dialog.dismiss();
        } else {
            getProfileDocFromDB(openingEntry.getPosProfile());
            dialog.dismiss();
        }
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.CHECK_OPENING_ENTRY) {
            CheckOpeningEntryResponse res = (CheckOpeningEntryResponse) response.body();
            if (res != null && !res.getCheckOpeningEntryList().isEmpty()) {
                if (res.getCheckOpeningEntryList().get(0) != null) {
                    Room.saveCheckOPE(res);
//                    MainApp.database.checkOPEDao().insertCheckOpeningEntry(res);
                    selectOPEDialog(res.getCheckOpeningEntryList());
//                    getProfileDoc(res.getCheckOpeningEntryList().get(0).getPosProfile());
                } else Notify.ToastLong(getString(R.string.no_opening_entry));
            } else Notify.ToastLong(getString(R.string.no_opening_entry));
        } else if ((int) tag == RequestCodes.API.GET_PROFILE_DOC) {
            GetProfileDocResponse res = (GetProfileDocResponse) response.body();
            if (res.getProfileDoc() != null) {
                Room.saveProfileDoc(res);
                Bundle bundle = new Bundle();
                bundle.putString("doc", new Gson().toJson(res.getProfileDoc()));
                ((MainActivity) getActivity()).fragmentTrx(PointOfSaleFragment.newInstance(), bundle, "PointOfSaleFragment");


            } else Notify.ToastLong(getString(R.string.no_profile_doc));
        }

    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
        if (response.getServerMessages() != null) Notify.Toast(response.getServerMessages());
    }

}