package com.example.erpnext.network.serializers.response;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.erpnext.models.Message;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class ItemResponse {
    @ColumnInfo(name = "item_name")
    public String itemName;
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @SerializedName("message")
    @Expose
    @Embedded
    private Message message;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
