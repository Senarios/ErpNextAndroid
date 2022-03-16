package com.example.erpnext.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.erpnext.roomDB.TypeConverters.ObjectListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters({ObjectListConverter.class})
public class Charts {

    @SerializedName("label")
    @Expose
    @Embedded
    private Object label;
    @SerializedName("items")
    @Expose
    @ColumnInfo(name = "chart_items")
    private List<Object> items = null;

    public Object getLabel() {
        return label;
    }

    public void setLabel(Object label) {
        this.label = label;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

}
