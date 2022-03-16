package com.example.erpnext.callbacks;


import com.example.erpnext.adapters.viewHolders.POSInvoiceItemsViewHolder;
import com.example.erpnext.models.Invoice;

public interface SelectedInvoiceCallback {
    void onDeleteClick(Invoice item, POSInvoiceItemsViewHolder viewHolder, int position);
}
