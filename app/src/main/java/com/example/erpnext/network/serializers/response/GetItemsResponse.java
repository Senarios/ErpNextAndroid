package com.example.erpnext.network.serializers.response;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.erpnext.models.ItemMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class GetItemsResponse {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "item_group")
    public String itemGroup;
    @ColumnInfo(name = "pos_profile")
    public String posProfile;
    @SerializedName("message")
    @Expose
    @Embedded
    private ItemMessage itemMessage;

    public GetItemsResponse() {
    }

    public ItemMessage getItemMessage() {
        return itemMessage;
    }

    public void setItemMessage(ItemMessage itemMessage) {
        this.itemMessage = itemMessage;
    }
}
