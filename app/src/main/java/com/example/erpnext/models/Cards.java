package com.example.erpnext.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.TypeConverters;

import com.example.erpnext.roomDB.TypeConverters.ItemListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@TypeConverters({ItemListConverter.class})
public class Cards {

    @SerializedName("label")
    @Expose
    @Embedded
    private Object label;
    @SerializedName("items")
    @ColumnInfo(name = "card_items")
    @Expose
    private List<Item> items = null;

    public Object getLabel() {
        return label;
    }

    public void setLabel(Object label) {
        this.label = label;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
