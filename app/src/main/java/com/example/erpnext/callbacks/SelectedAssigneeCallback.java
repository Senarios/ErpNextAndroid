package com.example.erpnext.callbacks;

import com.example.erpnext.adapters.viewHolders.SelectedAssigneesViewHolder;

public interface SelectedAssigneeCallback {
    void onDeleteClick(String item, SelectedAssigneesViewHolder viewHolder, int position);
}
