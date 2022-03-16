package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.AssigneesViewHolder;
import com.example.erpnext.callbacks.AssigneeCallback;
import com.example.erpnext.models.Assignment;

import java.util.ArrayList;
import java.util.List;

public class AssigneesAdapter extends RecyclerView.Adapter<AssigneesViewHolder> {

    private final Context context;
    private final AssigneeCallback callback;
    private List<Assignment> itemList;

    public AssigneesAdapter(Context context, List<Assignment> itemList, AssigneeCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public AssigneesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.assignees_list_item, parent, false);
        return new AssigneesViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull AssigneesViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), holder, position, callback);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<Assignment> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }
}