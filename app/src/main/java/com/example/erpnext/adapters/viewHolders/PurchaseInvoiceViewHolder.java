package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.PurchaseInvoiceCallback;

import java.util.List;


public class PurchaseInvoiceViewHolder extends RecyclerView.ViewHolder {

    public TextView invoice_name, supplier;
    public RecyclerView linksRV;
    public View parent;
    ImageView cancel;

    public PurchaseInvoiceViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        invoice_name = itemView.findViewById(R.id.invoice_name);
        supplier = itemView.findViewById(R.id.supplier);


    }

    public void setData(Context context, List<String> item, PurchaseInvoiceCallback callback, PurchaseInvoiceViewHolder holder, int position) {
        invoice_name.setText(item.get(1));
        supplier.setText(item.get(4));
        parent.setOnClickListener(v -> {
            callback.onPurchaseInvoiceClick(item, holder, position);
        });

    }
}
