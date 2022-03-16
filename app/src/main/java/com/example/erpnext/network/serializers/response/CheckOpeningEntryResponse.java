package com.example.erpnext.network.serializers.response;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.erpnext.models.CheckOpeningEntry;
import com.example.erpnext.roomDB.TypeConverters.CheckOpeningEntryListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters(CheckOpeningEntryListConverter.class)
public class CheckOpeningEntryResponse {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @SerializedName("message")
    @Expose
    private List<CheckOpeningEntry> checkOpeningEntryList = null;

    public List<CheckOpeningEntry> getCheckOpeningEntryList() {
        return checkOpeningEntryList;
    }

    public void setCheckOpeningEntryList(List<CheckOpeningEntry> checkOpeningEntryList) {
        this.checkOpeningEntryList = checkOpeningEntryList;
    }
}
