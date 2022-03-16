package com.example.erpnext.models;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.erpnext.roomDB.TypeConverters.CartItemListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters(CartItemListConverter.class)
public class ItemMessage {
    @SerializedName("items")
    @Expose
    private List<CartItem> cartItemList = null;

    public ItemMessage() {
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
