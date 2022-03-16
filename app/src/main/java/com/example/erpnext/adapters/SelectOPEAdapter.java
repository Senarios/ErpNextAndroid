package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.SelectOPEViewHolder;
import com.example.erpnext.callbacks.CheckOPECallback;
import com.example.erpnext.models.CheckOpeningEntry;

import java.util.ArrayList;
import java.util.List;

public class SelectOPEAdapter extends RecyclerView.Adapter<SelectOPEViewHolder> {

    private final Context context;
    private final CheckOPECallback callback;
    private List<CheckOpeningEntry> linkList;

    public SelectOPEAdapter(Context context, List<CheckOpeningEntry> linkList, CheckOPECallback callback) {
        this.context = context;
        this.linkList = linkList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public SelectOPEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.ope_list_item, parent, false);
        return new SelectOPEViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull SelectOPEViewHolder holder, int position) {
        holder.setData(context, linkList.get(position), callback);
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    public void filteredList(ArrayList<CheckOpeningEntry> filteredList) {
        this.linkList = filteredList;
        notifyDataSetChanged();
    }
}