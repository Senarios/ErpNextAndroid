package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.ReadOnlyFieldsViewHolder;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.models.Field;

import java.util.List;

public class ReadOnlyFieldsAdapter extends RecyclerView.Adapter<ReadOnlyFieldsViewHolder> {

    private final Context context;
    boolean isEditMode;
    private List<List<Field>> itemList;
    private ProfilesCallback callback;

    public ReadOnlyFieldsAdapter(Context context, List<List<Field>> itemList, boolean isEditMode) {
        this.context = context;
        this.itemList = itemList;
        this.isEditMode = isEditMode;
    }

    @NonNull
    @Override
    public ReadOnlyFieldsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.read_only_fileds_list_item, parent, false);
        return new ReadOnlyFieldsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ReadOnlyFieldsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, isEditMode);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(List<List<Field>> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }
}