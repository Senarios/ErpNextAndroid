package com.example.erpnext.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.MainActivity;
import com.example.erpnext.adapters.ShortcutsItemsAdapter;
import com.example.erpnext.callbacks.ShortcutsCallback;
import com.example.erpnext.databinding.FragmentStockBinding;
import com.example.erpnext.models.Item;
import com.example.erpnext.network.serializers.response.ItemResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockFragment extends Fragment implements ShortcutsCallback, View.OnClickListener {

    private FragmentStockBinding binding;
    private ShortcutsItemsAdapter shortcutsItemsAdapter;
    private RecyclerView mastersRv, shortcutsRv;
    private ItemResponse itemResponse;

    public StockFragment() {
        // Required empty public constructor
    }

    public static StockFragment newInstance() {
        StockFragment stockFragment = new StockFragment();
        return stockFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStockBinding.inflate(inflater, container, false);
        setClickListeners();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String projectJsonString = (String) getArguments().get("Stock");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            itemResponse = gson.fromJson(projectJsonString, ItemResponse.class);
//            binding.title.setText(itemResponse.itemName);
            if (itemResponse.getMessage().getShortcuts().getItems() != null) {
                setShortcutsRvAdapter(itemResponse.getMessage().getShortcuts().getItems());
            }
            if (itemResponse.getMessage().getCards().getItems() != null) {
//                setCardsAdapter(itemResponse.getMessage().getCards().getItems());
            }
        }
        return binding.getRoot();
    }

    private void setShortcutsRvAdapter(List<Item> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        shortcutsItemsAdapter = new ShortcutsItemsAdapter(getContext(), itemList, this);
        binding.shortcutsSV.setLayoutManager(linearLayoutManager);
        binding.shortcutsSV.setAdapter(shortcutsItemsAdapter);
    }

    @Override
    public void onShortcutClick(Item item) {
        if (item.getLabel().equalsIgnoreCase(getString(R.string.stockentryy))) {
            ((MainActivity) getActivity()).fragmentTrx(StockEntryFragment.newInstance(), null, getString(R.string.stockentryy));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.stockbalancee))) {
            ((MainActivity) getActivity()).fragmentTrx(StockBalanceFragment.newInstance(), null, getString(R.string.stockbalancee));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.purchasereceiptt))) {
            ((MainActivity) getActivity()).fragmentTrx(PurchaseReceiptFragment.newInstance(), null, getString(R.string.purchasereceiptt));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.deliverynotee))) {
            ((MainActivity) getActivity()).fragmentTrx(DeliveryNoteFragment.newInstance(), null, getString(R.string.deliverynotee));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.stockledgerr))) {
            ((MainActivity) getActivity()).fragmentTrx(StockLedgerFragment.newInstance(), null, getString(R.string.stockledgerr));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.itempricee))) {
            ((MainActivity) getActivity()).fragmentTrx(ItemPriceFragment.newInstance(), null, getString(R.string.itempricee));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.landedcostvoucherr))) {
            ((MainActivity) getActivity()).fragmentTrx(LandedCostFragment.newInstance(), null, getString(R.string.landedcostvoucherr));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.warehousee))) {
            ((MainActivity) getActivity()).fragmentTrx(WarehouseFragment.newInstance(), null, getString(R.string.warehousee));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.itemm))) {
            ((MainActivity) getActivity()).fragmentTrx(ItemFragment.newInstance(), null, getString(R.string.itemm));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.stockreconciliationn))) {
            ((MainActivity) getActivity()).fragmentTrx(StockReconciliationFragment.newInstance(), null, getString(R.string.stockreconciliationn));
        } else if (item.getLabel().equalsIgnoreCase(getString(R.string.totalstocksummaryy))) {
            ((MainActivity) getActivity()).fragmentTrx(StockSummFragment.newInstance(), null, getString(R.string.totalstocksummaryy));
        }
    }

    private void setClickListeners() {
        binding.back.setOnClickListener(this);
        binding.stockbalance.setOnClickListener(this);
        binding.stockentry.setOnClickListener(this);
        binding.purchasereceipt.setOnClickListener(this);
        binding.deliverynote.setOnClickListener(this);
        binding.stockledger.setOnClickListener(this);
        binding.itemprice.setOnClickListener(this);
        binding.stockrecon.setOnClickListener(this);
        binding.warehousee.setOnClickListener(this);
        binding.totalstocksummary.setOnClickListener(this);
        binding.landedcost.setOnClickListener(this);
        binding.item.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.stockbalance:
                ((MainActivity) getActivity()).fragmentTrx(StockBalanceFragment.newInstance(), null, getString(R.string.stockbalancee));
                break;
            case R.id.stockentry:
                ((MainActivity) getActivity()).fragmentTrx(StockEntryFragment.newInstance(), null, getString(R.string.stockentryy));
                break;
            case R.id.purchasereceipt:
                ((MainActivity) getActivity()).fragmentTrx(PurchaseReceiptFragment.newInstance(), null, getString(R.string.purchasereceiptt));
                break;
            case R.id.deliverynote:
                ((MainActivity) getActivity()).fragmentTrx(DeliveryNoteFragment.newInstance(), null, getString(R.string.deliverynotee));
                break;
            case R.id.stockledger:
                ((MainActivity) getActivity()).fragmentTrx(StockLedgerFragment.newInstance(), null, getString(R.string.stockledgerr));
                break;
            case R.id.itemprice:
                ((MainActivity) getActivity()).fragmentTrx(ItemPriceFragment.newInstance(), null, getString(R.string.itempricee));
                break;
            case R.id.stockrecon:
                ((MainActivity) getActivity()).fragmentTrx(StockReconciliationFragment.newInstance(), null, getString(R.string.stockreconciliationn));
                break;
            case R.id.warehousee:
                ((MainActivity) getActivity()).fragmentTrx(WarehouseFragment.newInstance(), null, getString(R.string.warehousee));
                break;
            case R.id.totalstocksummary:
                ((MainActivity) getActivity()).fragmentTrx(StockSummFragment.newInstance(), null, getString(R.string.totalstocksummaryy));
                break;
            case R.id.landedcost:
                ((MainActivity) getActivity()).fragmentTrx(LandedCostFragment.newInstance(), null, getString(R.string.landedcostvoucherr));
                break;
            case R.id.item:
                ((MainActivity) getActivity()).fragmentTrx(ItemFragment.newInstance(), null, getString(R.string.itemm));
                break;
        }
    }
}