package com.example.erpnext.callbacks;


import com.example.erpnext.adapters.viewHolders.CollectionTierViewHolder;
import com.example.erpnext.models.CollectionTier;

public interface CollectionTierCallback {
    void onDeleteTierClick(CollectionTier item, CollectionTierViewHolder viewHolder, int position);
}
