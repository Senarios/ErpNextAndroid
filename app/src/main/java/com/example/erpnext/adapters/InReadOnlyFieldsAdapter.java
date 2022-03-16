package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.InReadOnlyViewHolder;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.models.Field;

import java.util.List;

public class InReadOnlyFieldsAdapter extends RecyclerView.Adapter<InReadOnlyViewHolder> {

    private final Context context;
    boolean isEditMode;
    private List<Field> itemList;
    private ProfilesCallback callback;

    public InReadOnlyFieldsAdapter(Context context, List<Field> itemList, boolean isEditMode) {
        this.context = context;
        this.itemList = itemList;
        this.isEditMode = isEditMode;
//        this.callback = callback;
    }

    @NonNull
    @Override
    public InReadOnlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.in_read_only_list_item, parent, false);
        return new InReadOnlyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull InReadOnlyViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, isEditMode);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(List<Field> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }
}