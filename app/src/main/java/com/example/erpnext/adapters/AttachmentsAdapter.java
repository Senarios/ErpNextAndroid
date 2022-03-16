package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.AttachmentsViewHolder;
import com.example.erpnext.callbacks.AssigneeCallback;
import com.example.erpnext.models.Attachment;

import java.util.ArrayList;
import java.util.List;

public class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsViewHolder> {

    private final Context context;
    private final AssigneeCallback callback;
    private List<Attachment> itemList;

    public AttachmentsAdapter(Context context, List<Attachment> itemList, AssigneeCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public AttachmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.assignees_list_item, parent, false);
        return new AttachmentsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull AttachmentsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), holder, position, callback);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<Attachment> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }
}