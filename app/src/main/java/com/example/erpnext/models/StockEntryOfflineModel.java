package com.example.erpnext.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class StockEntryOfflineModel {
    @SerializedName("entryId")
    @Expose
    @NonNull
    @PrimaryKey
    private String entryId;
    @SerializedName("data")
    @Expose
    private String data;

    @NonNull
    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(@NonNull String entryId) {
        this.entryId = entryId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
