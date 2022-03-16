package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewClosingActivity;
import com.example.erpnext.callbacks.SelectedInvoiceCallback;
import com.example.erpnext.models.Invoice;


public class POSInvoiceItemsViewHolder extends RecyclerView.ViewHolder {

    public TextView name, amount, date, counter;
    public RecyclerView linksRV;
    public View parent;
    ImageView cross;

    public POSInvoiceItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        name = itemView.findViewById(R.id.invoice_name);
        amount = itemView.findViewById(R.id.amount);
        counter = itemView.findViewById(R.id.number);
        cross = itemView.findViewById(R.id.cross);
        date = itemView.findViewById(R.id.date);


    }

    public void setData(Context context, Invoice item, SelectedInvoiceCallback callback, int position, POSInvoiceItemsViewHolder holder) {
        name.setText(item.getName());
        date.setText(item.getCreation());
        amount.setText("$ " + item.getTotal());
        counter.setText(String.valueOf(position + 1));
        cross.setOnClickListener(v -> {
            callback.onDeleteClick(item, holder, position);
        });

        if (position == 0) {
            AddNewClosingActivity.netTotal = 0;
            AddNewClosingActivity.grandTotal = 0;
            AddNewClosingActivity.totalQuantity = 0;
        }
        AddNewClosingActivity.netTotal = AddNewClosingActivity.netTotal + Float.parseFloat(String.valueOf(item.getNetTotal()));
        AddNewClosingActivity.grandTotal = AddNewClosingActivity.grandTotal + Float.parseFloat(String.valueOf(item.getGrandTotal()));
        AddNewClosingActivity.totalQuantity = AddNewClosingActivity.totalQuantity + Float.parseFloat(String.valueOf(item.getTotalQty()));


        AddNewClosingActivity.setTotal();
    }

}
