package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.SectionBreakFieldsViewHolder;
import com.example.erpnext.callbacks.SectionBreaksCallback;
import com.example.erpnext.models.Field;

import java.util.List;

public class SectionsBreaksFieldsAdapter extends RecyclerView.Adapter<SectionBreakFieldsViewHolder> {

    private final Context context;
    boolean isEditMode;
    private List<List<Field>> itemList;
    private SectionBreaksCallback callback;

    public SectionsBreaksFieldsAdapter(Context context, List<List<Field>> itemList, boolean isEditMode) {
        this.context = context;
        this.itemList = itemList;
        this.isEditMode = isEditMode;
    }

    @NonNull
    @Override
    public SectionBreakFieldsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.section_break_list_item, parent, false);
        return new SectionBreakFieldsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull SectionBreakFieldsViewHolder holder, int position) {
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