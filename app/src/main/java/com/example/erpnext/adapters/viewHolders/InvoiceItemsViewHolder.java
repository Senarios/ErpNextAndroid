package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.CartItemCallback;
import com.example.erpnext.models.InvoiceItem;


public class InvoiceItemsViewHolder extends RecyclerView.ViewHolder {

    public TextView productName, price, itemQuantity, itemDescription, discount_percent;
    public RecyclerView linksRV;

    public View parent;

    public InvoiceItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        productName = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
        itemQuantity = itemView.findViewById(R.id.item_quantity);
        itemDescription = itemView.findViewById(R.id.description);
        discount_percent = itemView.findViewById(R.id.discount_percent);


    }

    public void setData(Context context, InvoiceItem item, CartItemCallback callback, int position) {
        productName.setText(item.getItemName());
        itemQuantity.setText("" + item.getQty());
        itemDescription.setVisibility(View.GONE);
        if (item.getDiscountPercentage() != null) {
            if (item.getDiscountPercentage() > 0) {
                discount_percent.setVisibility(View.VISIBLE);
                discount_percent.setText("(" + item.getDiscountPercentage() + "% off)");
            }
        }
        if (item.getPriceListRate() != 0.0) {
            price.setText("$ " + item.getPriceListRate());
        }

    }

}
