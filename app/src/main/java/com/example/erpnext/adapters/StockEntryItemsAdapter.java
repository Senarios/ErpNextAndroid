package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.StockEntryItemViewHolder;
import com.example.erpnext.callbacks.StockEntryCallback;
import com.example.erpnext.models.StockEntryItem;

import java.util.List;

public class StockEntryItemsAdapter extends RecyclerView.Adapter<StockEntryItemViewHolder> {

    private final Context context;
    private final List<StockEntryItem> stockEntryItemsList;
    private final StockEntryCallback callback;

    public StockEntryItemsAdapter(Context context, List<StockEntryItem> stockEntryItemsList, StockEntryCallback callback) {
        this.context = context;
        this.stockEntryItemsList = stockEntryItemsList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public StockEntryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.new_stock_entry_list_item, parent, false);
        return new StockEntryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockEntryItemViewHolder holder, int position) {
        holder.setData(stockEntryItemsList.get(position), position, callback);
    }

    @Override
    public int getItemCount() {
        return stockEntryItemsList.size();
    }

    public void addItem(StockEntryItem item) {
        stockEntryItemsList.add(item);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        stockEntryItemsList.remove(position);
        notifyDataSetChanged();
    }


}
