package com.example.erpnext.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.MainActivity;
import com.example.erpnext.adapters.CardItemsAdapter;
import com.example.erpnext.adapters.SelectOPEAdapter;
import com.example.erpnext.adapters.ShortcutsItemsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.CheckOPECallback;
import com.example.erpnext.callbacks.ShortcutsCallback;
import com.example.erpnext.models.CheckOpeningEntry;
import com.example.erpnext.models.Item;
import com.example.erpnext.models.NewDocModel;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.CheckOpeningEntryResponse;
import com.example.erpnext.network.serializers.response.GetProfileDocResponse;
import com.example.erpnext.network.serializers.response.ItemResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@limnk Fragment} subclass.
 * Use the {@link RetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetailFragment extends Fragment implements View.OnClickListener, ShortcutsCallback, OnNetworkResponse, CheckOPECallback {
    Dialog dialog;
    private TextView posProfile_text;
    private ImageView back;
    private RecyclerView mastersRv, shortcutsRv;
    private CardItemsAdapter cardItemsAdapter;
    private ShortcutsItemsAdapter shortcutsItemsAdapter;
    private ItemResponse itemResponse;
    private SelectOPEAdapter selectOPEAdapter;
    private NewDocModel newDocModel;
    LinearLayout pointofsale, pointofsaleprofile, loyaltyprogram,posopening,posclosing;

    public RetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RetailFragment newInstance() {
        RetailFragment fragment = new RetailFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retail, container, false);

        initViews(view);
        setClickListeners();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String projectJsonString = (String) getArguments().get("retail_item");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            itemResponse = gson.fromJson(projectJsonString, ItemResponse.class);
            if (itemResponse.getMessage().getShortcuts().getItems() != null) {
                setShortcutsRvAdapter(itemResponse.getMessage().getShortcuts().getItems());
            }
            if (itemResponse.getMessage().getCards().getItems() != null) {
                setCardsAdapter(itemResponse.getMessage().getCards().getItems());
            }
        }

        return view;
    }


    private void initViews(View view) {
        shortcutsRv = view.findViewById(R.id.shortcuts_RV);
        mastersRv = view.findViewById(R.id.masters_RV);
        back = view.findViewById(R.id.back);
        pointofsale = view.findViewById(R.id.pointofsale);
        pointofsaleprofile = view.findViewById(R.id.pointofsaleprofile);
        loyaltyprogram = view.findViewById(R.id.loyaltyprogram);
        posopening = view.findViewById(R.id.posopeningentry);
        posclosing = view.findViewById(R.id.posclosingentry);

    }

    private void setClickListeners() {
        back.setOnClickListener(this);
        pointofsale.setOnClickListener(this);
        pointofsaleprofile.setOnClickListener(this);
        loyaltyprogram.setOnClickListener(this);
        posopening.setOnClickListener(this);
        posclosing.setOnClickListener(this);
    }

    private void setCardsAdapter(List<Item> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        cardItemsAdapter = new CardItemsAdapter(getContext(), itemList);
        mastersRv.setLayoutManager(linearLayoutManager);
        mastersRv.setAdapter(cardItemsAdapter);
    }

    private void setShortcutsRvAdapter(List<Item> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        shortcutsItemsAdapter = new ShortcutsItemsAdapter(getContext(), itemList, this);
        shortcutsRv.setLayoutManager(linearLayoutManager);
        shortcutsRv.setAdapter(shortcutsItemsAdapter);
    }

    private void setOPEAdapter(List<CheckOpeningEntry> itemList, RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        selectOPEAdapter = new SelectOPEAdapter(getContext(), itemList, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(selectOPEAdapter);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.pointofsale:
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
                break;
            case R.id.pointofsaleprofile:
                bundle.putString("docType", "POS Profile");
                ((MainActivity) getActivity()).fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
                break;
            case R.id.loyaltyprogram:
                bundle.putString("docType", "Loyalty Program");
                ((MainActivity) getActivity()).fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
                break;
            case R.id.posopeningentry:
                bundle.putString("docType", "POS Opening Entry");
                ((MainActivity) getActivity()).fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
                break;
            case R.id.posclosingentry:
                bundle.putString("docType", "POS Closing Entry");
                ((MainActivity) getActivity()).fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
                break;
        }
    }

    @Override
    public void onShortcutClick(Item item) {
        if (item.getLabel().equalsIgnoreCase("Point Of Sale")) {
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
    }

    private void getProfileDocFromDB(String pos_profile) {
        GetProfileDocResponse response = MainApp.database.docProfileDao().getProfileDocResponse(pos_profile);
        if (response.getProfileDoc() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("doc", new Gson().toJson(response.getProfileDoc()));
            ((MainActivity) getActivity()).fragmentTrx(PointOfSaleFragment.newInstance(), bundle, "PointOfSaleFragment");
        } else Notify.Toast(getString(R.string.no_profile_offline));
    }

    private void createNewDoc(String name) {
        newDocModel = new NewDocModel();
        newDocModel.setDocstatus(0);
        newDocModel.setDoctype("POS Invoice");
        newDocModel.setName("new-pos-invoice");
        newDocModel.setIslocal(1);
        newDocModel.setUnsaved(1);
        newDocModel.setOwner(AppSession.get("email"));
        newDocModel.setNamingSeries("ACC-PSINV-.YYYY.-");
        newDocModel.setIsPos(1);
        newDocModel.setIsReturn(0);
        newDocModel.setUpdateBilledAmountInSalesOrder(0);
        newDocModel.setCompany("Izat Afghan Limited");
        newDocModel.setPostingDate(DateTime.getCurrentDate());
        newDocModel.setSetPostingTime(1);
        newDocModel.setTerritory("All Territories");
        newDocModel.setCurrency("USD");
        newDocModel.setSellingPriceList("Standard Selling");
        newDocModel.setPriceListCurrency("USD");
        newDocModel.setIgnorePricingRule(0);
        newDocModel.setUpdateStock(0);
        newDocModel.setTotalBillingAmount(0);
        newDocModel.setRedeemLoyaltyPoints(0);
        newDocModel.setApplyDiscountOn("Grand Total");
        newDocModel.setAllocateAdvancesAutomatically(0);
        newDocModel.setWriteOffOutstandingAmountAutomatically(0);
        newDocModel.setLetterHead("Izat Afghan");
        newDocModel.setGroupSameItems(0);
        newDocModel.setCustomerGroup("All Customer Groups");
        newDocModel.setIsDiscounted(0);
        newDocModel.setStatus("Draft");
        newDocModel.setPartyAccountCurrency("USD");
        newDocModel.setRunLinkTriggers(1);
        newDocModel.setcFormApplicable("No");
        newDocModel.setIsOpening("No");
        newDocModel.setItems(new ArrayList<>());
        newDocModel.setPosProfile(name);
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

    private void checkOpeningEntry() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.CHECK_OPENING_ENTRY)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Loading"))
                .enque(Network.apis().checkOpeningEntry(AppSession.get("email")))
                .execute();
    }

    private void getProfileDoc(String name) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_PROFILE_DOC)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Please wait.."))
                .enque(Network.apis().getProfileDoc("POS Profile", name))
                .execute();
    }

    private void runDoc(NewDocModel newDocModel) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(newDocModel));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.RUN_DOC)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Please wait.."))
                .enque(Network.apis().runDocMethod(jsonObject, "set_missing_values"))
                .execute();
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