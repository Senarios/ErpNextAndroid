package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.SelectedAssigneesViewHolder;
import com.example.erpnext.callbacks.SelectedAssigneeCallback;

import java.util.ArrayList;
import java.util.List;

public class SelectedAssigneesAdapter extends RecyclerView.Adapter<SelectedAssigneesViewHolder> {

    private final Context context;
    private final SelectedAssigneeCallback callback;
    private List<String> itemList;

    public SelectedAssigneesAdapter(Context context, List<String> itemList, SelectedAssigneeCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public SelectedAssigneesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.selected_assignees_list_item, parent, false);
        return new SelectedAssigneesViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull SelectedAssigneesViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), holder, position, callback);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<String> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(String item) {
        itemList.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        if (itemList != null && !itemList.isEmpty()) {
            int size = itemList.size();
            itemList.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    public List<String> getAllUsers() {
        return itemList;
    }
}