package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.SearchItemsViewHolder;
import com.example.erpnext.callbacks.SearchItesmCallback;

import java.util.ArrayList;
import java.util.List;

public class SearchItemsAdapter extends RecyclerView.Adapter<SearchItemsViewHolder> {

    private final Context context;
    private final SearchItesmCallback callback;
    private List<List<String>> lists;

    public SearchItemsAdapter(Context context, List<List<String>> lists, SearchItesmCallback callback) {
        this.context = context;
        this.lists = lists;
        this.callback = callback;
    }

    @NonNull
    @Override
    public SearchItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.search_item_list_item, parent, false);
        return new SearchItemsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemsViewHolder holder, int position) {
        holder.setData(context, lists.get(position), callback);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void filteredList(ArrayList<List<String>> filteredList) {
        this.lists = filteredList;
        notifyDataSetChanged();
    }
}