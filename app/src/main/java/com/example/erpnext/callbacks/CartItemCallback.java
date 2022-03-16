package com.example.erpnext.callbacks;


import com.example.erpnext.models.CartItem;
import com.example.erpnext.models.ItemDetail;

public interface CartItemCallback {
    void onItemClick(CartItem cartItem);

    void onCartItemClick(ItemDetail cartItem);

    void onDeleteClick(ItemDetail cartItem);
}
