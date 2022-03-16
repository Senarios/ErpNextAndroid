package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.ShortcutsItemsViewHolder;
import com.example.erpnext.callbacks.ShortcutsCallback;
import com.example.erpnext.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ShortcutsItemsAdapter extends RecyclerView.Adapter<ShortcutsItemsViewHolder> {

    private final Context context;
    private final ShortcutsCallback callback;
    private List<Item> itemList;

    public ShortcutsItemsAdapter(Context context, List<Item> itemList, ShortcutsCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ShortcutsItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cards_list_item, parent, false);
        return new ShortcutsItemsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ShortcutsItemsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<Item> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }
}