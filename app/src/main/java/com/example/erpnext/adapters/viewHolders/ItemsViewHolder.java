package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.CartItemCallback;
import com.example.erpnext.models.CartItem;


public class ItemsViewHolder extends RecyclerView.ViewHolder {

    public TextView productName, price, itemQuantity;
    public RecyclerView linksRV;

    public View parent;

    public ItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        productName = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
        itemQuantity = itemView.findViewById(R.id.item_quantity);


    }

    public void setData(Context context, CartItem item, CartItemCallback callback) {
        productName.setText(item.getItemName());
        itemQuantity.setText("" + item.getActualQty());
//        if (item.getPriceListRate() != 0.0) {
        price.setText("$ " + item.getPriceListRate());
//        }

        if (item.getActualQty() > 0) {
            itemQuantity.setTextColor(context.getResources().getColor(R.color.green));
            Drawable drawableLeft = context.getResources().getDrawable(R.drawable.ic_green_circle);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemQuantity.setCompoundDrawables(context.getResources().getDrawable(R.drawable.ic_green_circle), null, null, null);
            }
            itemQuantity.setBackgroundResource(R.drawable.round_bg_green);
            itemQuantity.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
        } else {
            Drawable drawableLeft = context.getResources().getDrawable(R.drawable.ic_red_circle);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                drawableLeft.setTint(context.getResources().getColor(R.color.red));
//            }
            itemQuantity.setBackgroundResource(R.drawable.round_bg_red);
            itemQuantity.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
        }
        parent.setOnClickListener(view -> callback.onItemClick(item));

    }


}
