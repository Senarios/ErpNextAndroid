package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.CartItemCallback;
import com.example.erpnext.fragments.PointOfSaleFragment;
import com.example.erpnext.models.ItemDetail;


public class CartItemsViewHolder extends RecyclerView.ViewHolder {

    public TextView productName, price, itemQuantity, itemDescription;
    public RecyclerView linksRV;
    public View parent;
    ImageView cross;

    public CartItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        productName = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
        itemQuantity = itemView.findViewById(R.id.item_quantity);
        cross = itemView.findViewById(R.id.cross);
        itemDescription = itemView.findViewById(R.id.description);


    }

    public void setData(Context context, ItemDetail item, CartItemCallback callback, int position) {
        float itemPrice = PointOfSaleFragment.getDiscountedAmount(item);
        productName.setText(item.getItemName());
        itemDescription.setText(item.getDescription());
        itemQuantity.setText("" + item.getQty());
        if (item.getPriceListRate() != 0.0) {
            price.setText("$ " + itemPrice);
        }
        if (position == 0) {
            PointOfSaleFragment.netTotal = 0;
            PointOfSaleFragment.grandTotal = 0;
        }
        PointOfSaleFragment.netTotal = (float) (PointOfSaleFragment.netTotal + item.getQty() * itemPrice);
        PointOfSaleFragment.grandTotal = (float) (PointOfSaleFragment.grandTotal + item.getQty() * itemPrice);
        PointOfSaleFragment.setTotal();
        parent.setOnClickListener(view -> callback.onCartItemClick(item));
        cross.setOnClickListener(v -> callback.onDeleteClick(item));

    }


}
