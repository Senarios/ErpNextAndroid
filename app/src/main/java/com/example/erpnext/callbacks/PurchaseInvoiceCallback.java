package com.example.erpnext.callbacks;


import com.example.erpnext.adapters.viewHolders.PurchaseInvoiceViewHolder;

import java.util.List;

public interface PurchaseInvoiceCallback {
    void onPurchaseInvoiceClick(List<String> item, PurchaseInvoiceViewHolder viewHolder, int position);
}
