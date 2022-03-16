package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.WarehouseItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WarehouseItemsResponse {
    @SerializedName("message")
    @Expose
    private List<WarehouseItem> warehouseList = null;

    public List<WarehouseItem> getWarehouseList() {
        return warehouseList;
    }

    public void setWarehouseList(List<WarehouseItem> warehouseList) {
        this.warehouseList = warehouseList;
    }
}
