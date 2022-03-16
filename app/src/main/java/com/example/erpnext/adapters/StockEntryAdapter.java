package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.StockEntryViewHolder;
import com.example.erpnext.callbacks.ProfilesCallback;

import java.util.List;

public class StockEntryAdapter extends RecyclerView.Adapter<StockEntryViewHolder> {

    private final Context context;
    private final List<List<String>> stockEntryList;
    private final ProfilesCallback callback;


    public StockEntryAdapter(Context context, List<List<String>> stockEntryList, ProfilesCallback callback) {
        this.context = context;
        this.stockEntryList = stockEntryList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public StockEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.stock_entry_list_item, parent, false);
        return new StockEntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockEntryViewHolder holder, int position) {
        holder.setData(context, stockEntryList.get(position).get(18), stockEntryList.get(position).get(0), stockEntryList.get(position).get(3), stockEntryList.get(position).get(14), stockEntryList.get(position).get(15), stockEntryList.get(position).get(9));
    }

    @Override
    public int getItemCount() {
        return stockEntryList.size();
    }

    public List<List<String>> getAllItems() {
        return stockEntryList;
    }

    public void addItems(List<List<String>> items) {
        stockEntryList.addAll(items);
    }
}
