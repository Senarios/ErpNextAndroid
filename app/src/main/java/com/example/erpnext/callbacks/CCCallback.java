package com.example.erpnext.callbacks;

import com.example.erpnext.adapters.viewHolders.CCViewHolder;

import java.util.List;

public interface CCCallback {
    void onDeleteCC(String item, CCViewHolder viewHolder, int position);

    void onLongClick(String list, int position);
}
