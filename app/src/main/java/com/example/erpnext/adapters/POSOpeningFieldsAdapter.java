package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.POSOpeningFieldsViewHolder;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.models.Field;

import java.util.ArrayList;
import java.util.List;

public class POSOpeningFieldsAdapter extends RecyclerView.Adapter<POSOpeningFieldsViewHolder> {

    private final Context context;
    private final boolean isEditMode;
    private List<Field> itemList;
    private ProfilesCallback callback;

    public POSOpeningFieldsAdapter(Context context, List<Field> itemList, boolean isEditMode) {
        this.context = context;
        this.itemList = itemList;
        this.isEditMode = isEditMode;
    }

    @NonNull
    @Override
    public POSOpeningFieldsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pos_opening_fileds_list_item, parent, false);
        return new POSOpeningFieldsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull POSOpeningFieldsViewHolder holder, int position) {
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