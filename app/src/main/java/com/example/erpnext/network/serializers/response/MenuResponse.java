package com.example.erpnext.network.serializers.response;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.erpnext.models.MenuMessage;
import com.example.erpnext.roomDB.TypeConverters.MenuMessageConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters(MenuMessageConverter.class)
public class MenuResponse {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @SerializedName("message")
    @Expose
    private List<MenuMessage> message = null;

    public MenuResponse() {
    }

    public List<MenuMessage> getMessage() {
        return message;
    }

    public void setMessage(List<MenuMessage> message) {
        this.message = message;
    }
}
