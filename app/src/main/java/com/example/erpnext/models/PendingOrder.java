package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.erpnext.network.serializers.requestbody.CompleteOrderRequestBody;
import com.example.erpnext.roomDB.TypeConverters.CompleteOrderItemListConverter;
import com.google.gson.annotations.SerializedName;

@Entity
@TypeConverters(CompleteOrderItemListConverter.class)
public class PendingOrder {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @SerializedName("pending_order")
    @Embedded
    private CompleteOrderRequestBody oreder;

    public PendingOrder() {
    }

    public CompleteOrderRequestBody getOreder() {
        return oreder;
    }

    public void setOreder(CompleteOrderRequestBody oreder) {
        this.oreder = oreder;
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
