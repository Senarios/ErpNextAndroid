package com.example.erpnext.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewClosingActivity;
import com.example.erpnext.activities.AddNewLoyaltyProgramActivity;
import com.example.erpnext.activities.AddNewOpeningActivity;
import com.example.erpnext.activities.AddNewPOSProfileActivity;
import com.example.erpnext.activities.MainActivity;
import com.example.erpnext.adapters.PosProfileListAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.models.Doc;
import com.example.erpnext.models.Info;
import com.example.erpnext.models.Profile;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.DocDetailResponse;
import com.example.erpnext.network.serializers.response.DocTypeResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.DateTime;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link POSProfileListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class POSProfileListFragment extends Fragment implements ProfilesCallback, View.OnClickListener, OnNetworkResponse {
    private final List<Profile> profileList = new ArrayList<>();
    int limitSet = 0;
    List<List<String>> profilesList = new ArrayList<>();
    private RecyclerView profilesRv;
    private PosProfileListAdapter profilesAdapter;
    private ImageView back;
    private ImageView search;
    private ImageView addNewPOSProfile_btn;
    private boolean isProfilesEnded = false;
    private String docType;
    private String fields;
    private TextView title;
    private DocTypeResponse docTypeResponse = new DocTypeResponse();
    private String pickedDate = "";
    private boolean clear = true;

    public POSProfileListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static POSProfileListFragment newInstance() {
        POSProfileListFragment fragment = new POSProfileListFragment();

        return fragment;
    }

    public static List<String> getFields(String docType) {
        List<String> fieldsList = new ArrayList<>();
        fieldsList.add("`tab" + docType + "`.`" + "name" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "modified" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_assign" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_seen" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_liked_by" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_comments" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "docstatus" + "`");
        return fieldsList;
    }

    public static List<String> getFieldsPOSOpeningEntry(String docType) {
        List<String> fieldsList = new ArrayList<>();
        fieldsList.add("`tab" + docType + "`.`" + "name" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "owner" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "creation" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "modified" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "modified_by" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_user_tags" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_comments" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_assign" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_liked_by" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "docstatus" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "parent" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "parenttype" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "parentfield" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "idx" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "period_start_date" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "period_end_date" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "status" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "posting_date" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "pos_profile" + "`");
        return fieldsList;
    }

    public static List<String> getFieldsLoyaltyProgram(String docType) {
        List<String> fieldsList = new ArrayList<>();
        fieldsList.add("`tab" + docType + "`.`" + "name" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "owner" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "creation" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "modified" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "modified_by" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_user_tags" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_comments" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_assign" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "_liked_by" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "docstatus" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "parent" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "parenttype" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "parentfield" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "idx" + "`");
        fieldsList.add("`tab" + docType + "`.`" + "loyalty_program_name" + "`");
        return fieldsList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pos_profile_list, container, false);

        initViews(view);
        setClickisteners();
        Bundle bundle = getArguments();
        if (bundle != null) {
            docType = (String) getArguments().get("docType");
            title.setText(docType);
            if (Utils.isNetworkAvailable()) getDocType(docType);
            else {
                if (MainApp.database.doctypeDao().getDocTypeResponse(docType) != null) {
                    setProfilesAdapter(Room.loadReportItem(docType));
                    docTypeResponse = MainApp.database.doctypeDao().getDocTypeResponse(docType);
                }
            }
        }
        profilesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(profilesRv)) {
                        if (!isProfilesEnded) {
                            Gson gson = new Gson();
                            String jsonString = null;
                            if (docType.equalsIgnoreCase("POS profile")) {
                                jsonString = gson.toJson(getFields(docType));
                            } else if (docType.equalsIgnoreCase("POS Opening Entry")) {
                                jsonString = gson.toJson(getFieldsPOSOpeningEntry(docType));
                            } else if (docType.equalsIgnoreCase("POS Closing Entry")) {
                                jsonString = gson.toJson(getFieldsPOSOpeningEntry(docType));
                            } else if (docType.equalsIgnoreCase("Loyalty Program")) {
                                jsonString = gson.toJson(getFieldsLoyaltyProgram(docType));
                            }
                            limitSet = limitSet + 10;
                            if(!pickedDate.isEmpty()) {
                                searchReportView(docType, fields, 10, true, "`tab" + docType + "`.`modified` desc", limitSet, pickedDate);
                            } else {
                                getReportView(docType, fields, 10, true, "`tab" + docType + "`.`modified` desc", limitSet);
                            }
//                            getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", limitSet);
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }

    private void checkItemsFromLocalDB() {
        if (MainApp.database.doctypeDao().getDocTypeResponse(docType) != null) {

            if (!Utils.isNetworkAvailable()) {
                if (MainApp.database.reportViewDao().getReportViewResponse(docType) != null) {
                    ReportViewResponse response = MainApp.database.reportViewDao().getReportViewResponse(docType);
                    setProfilesAdapter(response.getReportViewMessage().getValues());
                }
            }
        }
    }

    private void setClickisteners() {
        back.setOnClickListener(this);
        addNewPOSProfile_btn.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    private void initViews(View view) {
        profilesRv = view.findViewById(R.id.profiles_list_RV);
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        addNewPOSProfile_btn = view.findViewById(R.id.add_new);
        search = view.findViewById(R.id.search);
    }

    private void setProfilesAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        profilesAdapter = new PosProfileListAdapter(getContext(), profilesList, docType, this);
        profilesRv.setLayoutManager(linearLayoutManager);
        profilesRv.setAdapter(profilesAdapter);
    }

    @Override
    public void onProfileClick(List<String> list) {
        if (docType.equalsIgnoreCase("POS Profile")) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Doc docs = null;
            docs = docTypeResponse.getDocs().get(0);
            String responseToJsonString = gson.toJson(docs);
            String projectToJsonString = gson.toJson(list);
            Bundle bundle = new Bundle();
            bundle.putString("profileStringList", projectToJsonString);
            bundle.putString("doc", responseToJsonString);
            if (!Utils.isNetworkAvailable()) {
                DocDetailResponse docDetail = Room.getProfileDetail(list.get(0));
                if (docDetail != null && docDetail.getDocs() != null) {
                    ((MainActivity) getActivity()).fragmentTrx(POSProfileDetailFragment.newInstance(), bundle, "POSProfileDetailFragment");
                }
            } else {
                ((MainActivity) getActivity()).fragmentTrx(POSProfileDetailFragment.newInstance(), bundle, "POSProfileDetailFragment");
            }
        }
    }

    @Override
    public void onLongClick(List<String> list, int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.add_new:
                Doc docs = null;
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                if (Utils.isNetworkAvailable()) {
                    docs = docTypeResponse.getDocs().get(0);
                } else {
                    docTypeResponse = MainApp.database.doctypeDao().getDocTypeResponse(docType);
                    if (docTypeResponse != null) docs = docTypeResponse.getDocs().get(0);
                }
                if (docs != null && docs.getName().equalsIgnoreCase("POS Profile")) {
                    String responseToJsonString = gson.toJson(docs);
                    startActivityForResult(new Intent(getContext(), AddNewPOSProfileActivity.class).putExtra("doc", responseToJsonString), RequestCodes.CREATE_NEW_POS_PROFILE);

                } else if (docs != null && docs.getName().equalsIgnoreCase("POS Opening Entry")) {
                    String responseToJsonString = gson.toJson(docs);
                    startActivityForResult(new Intent(getContext(), AddNewOpeningActivity.class).putExtra("doc", responseToJsonString), RequestCodes.CREATE_NEW_POS_OPENING);

                } else if (docs != null && docs.getName().equalsIgnoreCase("POS Closing Entry")) {
                    String responseToJsonString = gson.toJson(docs);
                    startActivityForResult(new Intent(getContext(), AddNewClosingActivity.class).putExtra("doc", responseToJsonString), RequestCodes.CREATE_NEW_POS_CLOSING);

                } else if (docs != null && docs.getName().equalsIgnoreCase("Loyalty Program")) {
                    String responseToJsonString = gson.toJson(docs);
                    startActivityForResult(new Intent(getContext(), AddNewLoyaltyProgramActivity.class).putExtra("doc", responseToJsonString), RequestCodes.CREATE_NEW_LOYALTY_PROGRAM);
                }
                break;
            case R.id.search:
                if (clear) {
                    Utils.pickDate(requireContext(), date -> {
                        //filterTasks(date);
                        pickedDate = date;
                        search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_close_24));
                        clear = false;
                        limitSet = 0;
                        searchReportView(docType, fields, 10, true, "`tab" + docType + "`.`modified` desc", limitSet, date);
                    });
                } else {
                    search.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_search_24));
                    clear = true;
                    limitSet = 0;
                    pickedDate = "";
                    getReportView(docType, fields, 10, true, "`tab" + docType + "`.`modified` desc", limitSet);
                }
                break;
        }
    }

    private void getDocType(String docType) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.DOCTYPE)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Loading"))
                .enque(Network.apis().getDocType(docType))
                .execute();
    }

    private void getReportView(String docType, String fields, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        this.fields = fields;
        this.docType = docType;
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Loading"))
                .enque(Network.apis().getReportView(docType, fields, pageLength, isCommentCount, orderBy, limitStart))
                .execute();
    }

    private void searchReportView(String docType, String fields, int pageLength, boolean isCommentCount, String orderBy, int limitStart, String filterDate) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(getActivity(), "Loading"))
                .enque(Network.apis().getReportView(docType, fields, "[[\""+docType+"\",\"period_start_date\",\"=\",\"" + filterDate + " 00:00:00" + "\"]]", pageLength, isCommentCount, orderBy, limitStart))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.DOCTYPE) {
            DocTypeResponse res = (DocTypeResponse) response.body();
            if (res != null && res.getDocs() != null) {
                docTypeResponse = res;
                String docType = res.getDocs().get(0).getName();
                this.docType = docType;
                Room.saveDoc(res, docType);
                Gson gson = new Gson();
                if (docType.equalsIgnoreCase("POS Profile")) {
                    String jsonString = gson.toJson(getFields(docType));
                    getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", limitSet);
                } else if (docType.equalsIgnoreCase("POS Opening Entry")) {
                    String jsonString = gson.toJson(getFieldsPOSOpeningEntry(docType));
                    getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", limitSet);
                } else if (docType.equalsIgnoreCase("POS Closing Entry")) {
                    String jsonString = gson.toJson(getFieldsPOSOpeningEntry(docType));
                    getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", limitSet);
                } else if (docType.equalsIgnoreCase("Loyalty Program")) {
                    String jsonString = gson.toJson(getFieldsLoyaltyProgram(docType));
                    getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", limitSet);
                }
            }
        } else if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {

                    if (limitSet == 0 && !profilesList.isEmpty()) {
                        profilesList.clear();
                    }
                    profilesList = res.getReportViewMessage().getValues();
                    if (profilesList != null && !profilesList.isEmpty()) {
                        if (limitSet == 0) {
                            setProfilesAdapter(profilesList);
                            Room.saveReportView(res, docType);
                        } else {
                            profilesAdapter.addItem(res.getReportViewMessage().getValues());
                            Room.saveMoreReportView(res, docType);
                        }
                    }
                } else {
                    isProfilesEnded = true;
                }
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodes.CREATE_NEW_POS_PROFILE) {
            if (resultCode == RESULT_OK) {
                Gson gson = new Gson();
                String jsonString = gson.toJson(getFields(docType));
                limitSet = 0;
                getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", limitSet);

            }
        } else if (requestCode == RequestCodes.CREATE_NEW_POS_OPENING) {
            if (Utils.isNetworkAvailable()) {
                if (resultCode == RESULT_OK) {
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(getFieldsPOSOpeningEntry(docType));
                    limitSet = 0;
                    getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", limitSet);

                }
            } else {
                if (data != null && data.hasExtra("localOPE")) {
                    String string = (String) data.getExtras().get("localOPE");
                    JSONObject jsonObject = new Gson().fromJson(string, JSONObject.class);
                    List<String> item = new ArrayList<>();
                    item.add(0, docTypeResponse.getDocs().get(0).getAutoname());
                    item.add(1, AppSession.get("email"));
                    item.add(2, DateTime.getServerCurrentDateTime());
                    item.add(3, DateTime.getServerCurrentDateTime());
                    item.add(4, AppSession.get("email"));
                    item.add(5, null);
                    item.add(6, null);
                    item.add(7, null);
                    item.add(8, null);
                    item.add(9, "1");
                    item.add(10, null);
                    item.add(11, null);
                    item.add(12, null);
                    item.add(13, "0");
                    try {
                        item.add(14, jsonObject.get("period_start_date").toString());
                        item.add(15, jsonObject.get("period_end_date").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    item.add(16, "Open");
                    try {
                        item.add(17, jsonObject.get("posting_date").toString());
                        item.add(18, jsonObject.get("pos_profile").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    item.add(19, "0");
                    ReportViewResponse reportViewResponse = MainApp.database.reportViewDao().getReportViewResponse(docType);
                    reportViewResponse.getReportViewMessage().getValues().add(0, item);
                    saveReportViewToLocalDB(reportViewResponse);

                    profilesAdapter.addNewItem(item);
                }
                if (data != null && data.hasExtra("loyalty")) {
                    String string = (String) data.getExtras().get("loyalty");
                    JSONObject jsonObject = new Gson().fromJson(string, JSONObject.class);
                    List<String> item = new ArrayList<>();
                    try {
                        item.add(0, jsonObject.getString("loyalty_program_name"));
                        item.add(1, AppSession.get("email"));
                        item.add(2, DateTime.getServerCurrentDateTime());
                        item.add(3, DateTime.getServerCurrentDateTime());
                        item.add(4, AppSession.get("email"));
                        item.add(5, null);
                        item.add(6, null);
                        item.add(7, null);
                        item.add(8, null);
                        item.add(9, "0");
                        item.add(10, null);
                        item.add(11, null);
                        item.add(12, null);
                        item.add(13, "0");
                        item.add(14, jsonObject.getString("loyalty_program_name"));
                        item.add(15, "0");
                        ReportViewResponse reportViewResponse = MainApp.database.reportViewDao().getReportViewResponse(docType);
                        reportViewResponse.getReportViewMessage().getValues().add(0, item);
                        saveReportViewToLocalDB(reportViewResponse);

                        profilesAdapter.addNewItem(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == RequestCodes.CREATE_NEW_LOYALTY_PROGRAM) {
            if (Utils.isNetworkAvailable()) {
                if (resultCode == RESULT_OK) {
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(getFieldsLoyaltyProgram(docType));
                    limitSet = 0;
                    getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", limitSet);

                }
            } else {
                if (data != null && data.hasExtra("loyalty")) {
                    String string = (String) data.getExtras().get("loyalty");
                    JSONObject jsonObject = new Gson().fromJson(string, JSONObject.class);
                    List<String> item = new ArrayList<>();
                    try {
                        item.add(0, jsonObject.getString("loyalty_program_name"));
                        item.add(1, AppSession.get("email"));
                        item.add(2, DateTime.getServerCurrentDateTime());
                        item.add(3, DateTime.getServerCurrentDateTime());
                        item.add(4, AppSession.get("email"));
                        item.add(5, null);
                        item.add(6, null);
                        item.add(7, null);
                        item.add(8, null);
                        item.add(9, "0");
                        item.add(10, null);
                        item.add(11, null);
                        item.add(12, null);
                        item.add(13, "0");
                        item.add(14, jsonObject.getString("loyalty_program_name"));
                        item.add(15, "0");
                        ReportViewResponse reportViewResponse = MainApp.database.reportViewDao().getReportViewResponse(docType);
                        reportViewResponse.getReportViewMessage().getValues().add(0, item);
                        saveReportViewToLocalDB(reportViewResponse);

                        profilesAdapter.addNewItem(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == RequestCodes.CREATE_NEW_POS_CLOSING) {
            if (resultCode == RESULT_OK) {
                Gson gson = new Gson();
                String jsonString = gson.toJson(getFieldsPOSOpeningEntry(docType));
                limitSet = 0;
                getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", limitSet);

            }
        }
    }

    private void saveReportViewToLocalDB(ReportViewResponse res) {
        res.doctype = docType;
        if (MainApp.database.reportViewDao().getReportViewResponse(docType) != null) {
            int id = MainApp.database.reportViewDao().getReportViewResponse(docType).uids;
            res.uids = (id);
            MainApp.database.reportViewDao().insertReportViewResponse(res);
        } else MainApp.database.reportViewDao().insertReportViewResponse(res);
    }

    @Override
    public void onResume() {
        super.onResume();
        limitSet = 0;
    }
}