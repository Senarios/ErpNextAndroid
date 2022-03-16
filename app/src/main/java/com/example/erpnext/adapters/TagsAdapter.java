package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.TagsViewHolder;
import com.example.erpnext.callbacks.AssigneeCallback;

import java.util.ArrayList;
import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsViewHolder> {

    private final Context context;
    private final AssigneeCallback callback;
    private List<String> itemList;

    public TagsAdapter(Context context, List<String> itemList, AssigneeCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.assignees_list_item, parent, false);
        return new TagsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull TagsViewHolder holder, int position) {
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

    public void removeItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    public List<String> getAllTags() {
        return itemList;
    }

    public void addItem(String tag) {
        this.itemList.add(tag);
        notifyDataSetChanged();
    }
}