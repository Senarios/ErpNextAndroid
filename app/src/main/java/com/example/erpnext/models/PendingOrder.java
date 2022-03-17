package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.erpnext.network.serializers.requestbody.CompleteOrderRequestBody;
import com.example.erpnext.roomDB.TypeConverters.CompleteOrderItemListConverter;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@TypeConverters(CompleteOrderItemListConverter.class)
public class PendingOrder {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @SerializedName("pending_order")
    @Embedded
    private CompleteOrderRequestBody oreder;
    @SerializedName("item_group")
    @Expose
    private String item_group;

    public PendingOrder() {
    }

    public CompleteOrderRequestBody getOreder() {
        return oreder;
    }

    public void setOreder(CompleteOrderRequestBody oreder) {
        this.oreder = oreder;
    }

    public String getItem_group() {
        return item_group;
    }

    public void setItem_group(String item_group) {
        this.item_group = item_group;
    }

    //    @ColumnInfo(name = "pending_orders")
//    List<CompleteOrderRequestBody> orders;
//
//    public PendingOrders() {
//    }
//
//    public List<CompleteOrderRequestBody> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<CompleteOrderRequestBody> orders) {
//        this.orders = orders;
//    }
}
