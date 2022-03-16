package com.example.erpnext.network.serializers.response;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.erpnext.models.ItemDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class GetItemDetailResponse {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @SerializedName("message")
    @Expose
    @Embedded
    private ItemDetail itemDetail;

    public ItemDetail getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }
}
