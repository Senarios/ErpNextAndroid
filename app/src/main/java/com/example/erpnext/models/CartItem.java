package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class CartItem {
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("stock_uom")
    @Expose
    private String stockUom;
    @SerializedName("item_image")
    @Expose
    @Embedded
    private Object itemImage;
    @SerializedName("is_stock_item")
    @Expose
    private Integer isStockItem;
    @SerializedName("price_list_rate")
    @Expose
    private float priceListRate;
    @SerializedName("currency")
    @Expose
    @Embedded
    private Object currency;
    @SerializedName("actual_qty")
    @Expose
    private float actualQty;
    @SerializedName("discount_percentage")
    @Expose
    private float discountPercentage;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getStockUom() {
        return stockUom;
    }

    public void setStockUom(String stockUom) {
        this.stockUom = stockUom;
    }

    public Object getItemImage() {
        return itemImage;
    }

    public void setItemImage(Object itemImage) {
        this.itemImage = itemImage;
    }

    public Integer getIsStockItem() {
        return isStockItem;
    }

    public void setIsStockItem(Integer isStockItem) {
        this.isStockItem = isStockItem;
    }

    public float getPriceListRate() {
        return priceListRate;
    }

    public void setPriceListRate(float priceListRate) {
        this.priceListRate = priceListRate;
    }

    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    public Integer getActualQty() {
        return (int) actualQty;
    }

    public void setActualQty(float actualQty) {
        this.actualQty = actualQty;
    }
}
