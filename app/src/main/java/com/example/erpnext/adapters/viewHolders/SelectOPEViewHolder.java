package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.CheckOPECallback;
import com.example.erpnext.models.CheckOpeningEntry;


public class SelectOPEViewHolder extends RecyclerView.ViewHolder {

    public TextView posProfile, openingEntry;
    public RecyclerView linksRV;

    public View parent;

    public SelectOPEViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        posProfile = itemView.findViewById(R.id.profileName);
        openingEntry = itemView.findViewById(R.id.opening_entry);


    }

    public void setData(Context context, CheckOpeningEntry item, CheckOPECallback callback) {
        posProfile.setText(item.getPosProfile());
        openingEntry.setText(item.getName());
        parent.setOnClickListener(view -> callback.onEntryClick(item));
    }
}
