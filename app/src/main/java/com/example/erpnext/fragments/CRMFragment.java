package com.example.erpnext.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.MainActivity;
import com.example.erpnext.adapters.CardItemsAdapter;
import com.example.erpnext.adapters.ShortcutsItemsAdapter;
import com.example.erpnext.callbacks.ShortcutsCallback;
import com.example.erpnext.models.Item;
import com.example.erpnext.network.serializers.response.ItemResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CRMFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CRMFragment extends BaseFragment implements View.OnClickListener, ShortcutsCallback {

    Dialog dialog;
    private TextView title;
    private ImageView back;
    private RecyclerView mastersRv, shortcutsRv;
    private CardItemsAdapter cardItemsAdapter;
    private ShortcutsItemsAdapter shortcutsItemsAdapter;
    private ItemResponse itemResponse;
    private LinearLayout lead, opportunity,cusgroup,
    territory,customer,contact,communication,leadsource,salesperson;

    public CRMFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CRMFragment newInstance() {
        CRMFragment fragment = new CRMFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leads_and_opportunity, container, false);

        initViews(view);
        setClickListeners();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String projectJsonString = (String) getArguments().get(getString(R.string.crm));
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            itemResponse = gson.fromJson(projectJsonString, ItemResponse.class);
            title.setText(itemResponse.itemName);
            if (itemResponse.getMessage().getShortcuts().getItems() != null) {
                setShortcutsRvAdapter(itemResponse.getMessage().getShortcuts().getItems());
            }
            if (itemResponse.getMessage().getCards().getItems() != null) {
//                setCardsAdapter(itemResponse.getMessage().getCards().getItems());
            }
        }

        return view;
    }


    private void initViews(View view) {
        title = view.findViewById(R.id.title);
        shortcutsRv = view.findViewById(R.id.shortcuts_RV);
        mastersRv = view.findViewById(R.id.masters_RV);
        back = view.findViewById(R.id.back);
        lead = view.findViewById(R.id.lead);
        opportunity = view.findViewById(R.id.opportunity);
        cusgroup = view.findViewById(R.id.customergroup);
        territory = view.findViewById(R.id.territory);
        customer = view.findViewById(R.id.customerr);
        contact = view.findViewById(R.id.contactt);
        communication = view.findViewById(R.id.communicationn);
        leadsource = view.findViewById(R.id.leadsource);
        salesperson = view.findViewById(R.id.salesperson);
    }

    private void setClickListeners() {
        back.setOnClickListener(this);
        lead.setOnClickListener(this);
        opportunity.setOnClickListener(this);
        cusgroup.setOnClickListener(this);
        territory.setOnClickListener(this);
        customer.setOnClickListener(this);
        contact.setOnClickListener(this);
        communication.setOnClickListener(this);
        leadsource.setOnClickListener(this);
        salesperson.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.lead:
                ((MainActivity) getActivity()).fragmentTrx(LeadsFragment.newInstance(), null, getString(R.string.leadd));
                break;
            case R.id.opportunity:
                ((MainActivity) getActivity()).fragmentTrx(OpportunitiesFragment.newInstance(), null, getString(R.string.opportunityy));
                break;
            case R.id.customergroup:
                ((MainActivity) getActivity()).fragmentTrx(CustomerGroupsFragment.newInstance(), null, getString(R.string.customergroupp));
                break;
            case R.id.territory:
                ((MainActivity) getActivity()).fragmentTrx(TerritoryFragment.newInstance(), null, getString(R.string.territoryy));
                break;
            case R.id.customerr:
                ((MainActivity) getActivity()).fragmentTrx(CustomersFragment.newInstance(), null, getString(R.string.customerr));
                break;
            case R.id.contactt:
                ((MainActivity) getActivity()).fragmentTrx(ContactsFragment.newInstance(), null, getString(R.string.contactss));
                break;
            case R.id.communicationn:
                ((MainActivity) getActivity()).fragmentTrx(CommunicationsFragment.newInstance(), null, getString(R.string.communicationn));
                break;
            case R.id.leadsource:
                ((MainActivity) getActivity()).fragmentTrx(LeadSourcesFragment.newInstance(), null, getString(R.string.leadsourcee));
                break;
            case R.id.salesperson:
                ((MainActivity) getActivity()).fragmentTrx(SalesPersonsFragment.newInstance(), null, getString(R.string.salespersonn));
                break;
        }
    }

    @Override
    public void onShortcutClick(Item item) {
        if (item.getLabel().equalsIgnoreCase(getString(R.string.leadd))) {
            ((MainActivity) getActivity()).fragmentTrx(LeadsFragment.newInstance(), null, getString(R.string.leadd));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.territoryy))) {
            ((MainActivity) getActivity()).fragmentTrx(TerritoryFragment.newInstance(), null, getString(R.string.territoryy));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.opportunityy))) {
            ((MainActivity) getActivity()).fragmentTrx(OpportunitiesFragment.newInstance(), null, getString(R.string.opportunityy));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.customergroupp))) {
            ((MainActivity) getActivity()).fragmentTrx(CustomerGroupsFragment.newInstance(), null, getString(R.string.customergroupp));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.customerr))) {
            ((MainActivity) getActivity()).fragmentTrx(CustomersFragment.newInstance(), null, getString(R.string.customerr));
        }else if (item.getLabel().equalsIgnoreCase(getString(R.string.leadsourcee))) {
            ((MainActivity) getActivity()).fragmentTrx(LeadSourcesFragment.newInstance(), null, getString(R.string.leadsourcee));
        }else if (item.getLabel().equalsIgnoreCase(getString(R.string.communicationn))) {
            ((MainActivity) getActivity()).fragmentTrx(CommunicationsFragment.newInstance(), null, getString(R.string.communicationn));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.contactss))) {
            ((MainActivity) getActivity()).fragmentTrx(ContactsFragment.newInstance(), null, getString(R.string.contactss));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.salespersonn))) {
            ((MainActivity) getActivity()).fragmentTrx(SalesPersonsFragment.newInstance(), null, getString(R.string.salespersonn));
        }
    }
}