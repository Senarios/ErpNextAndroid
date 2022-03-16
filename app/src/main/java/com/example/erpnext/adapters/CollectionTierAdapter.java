package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.CollectionTierViewHolder;
import com.example.erpnext.callbacks.CollectionTierCallback;
import com.example.erpnext.models.CollectionTier;

import java.util.ArrayList;
import java.util.List;

public class CollectionTierAdapter extends RecyclerView.Adapter<CollectionTierViewHolder> {

    private final Context context;
    private final CollectionTierCallback callback;
    private List<CollectionTier> itemList;

    public CollectionTierAdapter(Context context, List<CollectionTier> itemList, CollectionTierCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CollectionTierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.tier_list_item, parent, false);
        return new CollectionTierViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CollectionTierViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, holder);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<CollectionTier> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }


    public List<CollectionTier> getAllItems() {
        return this.itemList;
    }

    public void addItem(CollectionTier selectedInvoice) {
        if (itemList.size() > 0) {
            List<CollectionTier> list = new ArrayList<>();
            list.addAll(itemList);
            for (CollectionTier item : list) {
                if (!item.getName().equalsIgnoreCase(selectedInvoice.getName())) {
                    this.itemList.add(selectedInvoice);
                    notifyDataSetChanged();
                }
            }
        } else {
            this.itemList.add(selectedInvoice);
            notifyDataSetChanged();
        }
    }

    public void removeItem(CollectionTier item, int position) {
        this.itemList.remove(position);
        notifyDataSetChanged();
    }
}