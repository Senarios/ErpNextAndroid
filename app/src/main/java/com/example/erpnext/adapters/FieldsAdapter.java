package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.FieldsViewHolder;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.models.Field;

import java.util.ArrayList;
import java.util.List;

public class FieldsAdapter extends RecyclerView.Adapter<FieldsViewHolder> {

    private final Context context;
    private final boolean isEditMode;
    private List<Field> itemList;
    private ProfilesCallback callback;

    public FieldsAdapter(Context context, List<Field> itemList, boolean isEditMode) {
        this.context = context;
        this.itemList = itemList;
        this.isEditMode = isEditMode;
    }

    @NonNull
    @Override
    public FieldsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.fileds_list_item, parent, false);
        return new FieldsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull FieldsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, isEditMode);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<Field> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }
}