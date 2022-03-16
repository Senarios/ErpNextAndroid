package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.LinksViewHolder;
import com.example.erpnext.callbacks.LinksCallback;
import com.example.erpnext.models.Link;

import java.util.ArrayList;
import java.util.List;

public class LinksAdapter extends RecyclerView.Adapter<LinksViewHolder> {

    private final Context context;
    private final LinksCallback callback;
    private List<Link> linkList;

    public LinksAdapter(Context context, List<Link> linkList, LinksCallback callback) {
        this.context = context;
        this.linkList = linkList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public LinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.links_list_item, parent, false);
        return new LinksViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull LinksViewHolder holder, int position) {
        holder.setData(context, linkList.get(position), callback);
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    public void filteredList(ArrayList<Link> filteredList) {
        this.linkList = filteredList;
        notifyDataSetChanged();
    }
}