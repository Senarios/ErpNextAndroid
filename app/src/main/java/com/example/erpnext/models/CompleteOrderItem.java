package com.example.erpnext.models;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class CompleteOrderItem {
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("rate")
    @Expose
    private float rate;
    @SerializedName("discount_percentage")
    @Expose
    private float discountPercentage;

    public CompleteOrderItem() {
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
