package com.example.erpnext.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddLeadActivity;
import com.example.erpnext.adapters.PosProfileListAdapter;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.models.Profile;
import com.example.erpnext.repositories.LeadsRepo;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.example.erpnext.viewmodels.LeadFragmentViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeadsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeadsFragment extends Fragment implements View.OnClickListener, ProfilesCallback {

    private final List<Profile> leadsList = new ArrayList<>();
    public boolean isProfilesEnded = false;
    List<List<String>> profilesList = new ArrayList<>();
    int limitStart = 0;
    private RecyclerView leadsRV;
    private PosProfileListAdapter leadsAdapter;
    private LeadFragmentViewModel leadFragmentViewModel;
    private ImageView back, addNew;


    public LeadsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LeadsFragment newInstance() {
        LeadsFragment fragment = new LeadsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leads, container, false);

        initViews(view);
        setClickListeners();
        if (leadFragmentViewModel != null)
            Objects.requireNonNull(leadFragmentViewModel.getLeads().getValue()).clear();
        leadFragmentViewModel = new ViewModelProvider(requireActivity()).get(LeadFragmentViewModel.class);
        if (Utils.isNetworkAvailable()) {
            getLeads();
            setInvoicesObserver();
        } else setLeadsAdapter(Room.getReportView("Lead"));
        leadsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (Utils.isNetworkAvailable()) {
                    if (Utils.isLastItemDisplaying(leadsRV)) {
                        if (!isProfilesEnded) {
                            limitStart = limitStart + 20;
                            getLeads();
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }


    private void initViews(View view) {
        leadsRV = view.findViewById(R.id.leads_rv);
        back = view.findViewById(R.id.back);
        addNew = view.findViewById(R.id.add_new);

    }

    private void setClickListeners() {
        back.setOnClickListener(this);
        addNew.setOnClickListener(this);
    }

    private void setLeadsAdapter(List<List<String>> profilesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        leadsAdapter = new PosProfileListAdapter(getContext(), profilesList, "Lead", this);
        leadsRV.setLayoutManager(linearLayoutManager);
        leadsRV.setAdapter(leadsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.add_new:
                startActivityForResult(new Intent(getContext(), AddLeadActivity.class), RequestCodes.ADD_NEW_LEAD);
        }
    }

    private void setInvoicesObserver() {
        leadFragmentViewModel.getLeads().observe(getActivity(), lists -> {
            if (lists != null) {
                setLeadsAdapter(lists);
            }
        });
    }

    private void getLeads() {
        leadFragmentViewModel.getLeadsApi(getActivity(),
                "Lead",
                20,
                true,
                "`tabLead`.`modified` desc",
                limitStart);
    }

    @Override
    public void onProfileClick(List<String> list) {

    }

    @Override
    public void onLongClick(List<String> list, int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodes.ADD_NEW_LEAD) {
            if (resultCode == RESULT_OK) {
                LeadsRepo.getInstance().leadsList.setValue(new ArrayList<>());
                limitStart = 0;
                getLeads();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        MainApp.INSTANCE.setCurrentActivity(getActivity());
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LeadsRepo.getInstance().leadsList.setValue(new ArrayList<>());
    }
}