package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.SectionTableViewHolder;
import com.example.erpnext.callbacks.SectionBreaksCallback;
import com.example.erpnext.models.Field;

import java.util.ArrayList;
import java.util.List;

public class SectionTableAdapter extends RecyclerView.Adapter<SectionTableViewHolder> {

    private final Context context;
    private final SectionBreaksCallback callback;
    private final boolean isEditMode;
    RelativeLayout editLayout;
    private List<Field> fieldList;

    public SectionTableAdapter(Context context, List<Field> linkList, SectionBreaksCallback callback, boolean isEditMode) {
        this.context = context;
        this.fieldList = linkList;
        this.callback = callback;
        this.isEditMode = isEditMode;
    }

    @NonNull
    @Override
    public SectionTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.section_table_item_list, parent, false);
        return new SectionTableViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull SectionTableViewHolder holder, int position) {
        holder.setData(context, fieldList.get(position), callback, isEditMode);
    }

    @Override
    public int getItemCount() {
        return fieldList.size();
    }

    public void filteredList(ArrayList<Field> filteredList) {
        this.fieldList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(Field field) {
        this.fieldList.add(field);
        notifyDataSetChanged();
    }
}