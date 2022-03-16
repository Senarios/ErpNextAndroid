package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WarehouseItem {
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("qty")
    @Expose
    private Double qty;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("valuation_rate")
    @Expose
    private Double valuationRate;
    @SerializedName("current_qty")
    @Expose
    private Double currentQty;
    @SerializedName("current_valuation_rate")
    @Expose
    private Double currentValuationRate;
    @SerializedName("current_serial_no")
    @Expose
    private String currentSerialNo;
    @SerializedName("serial_no")
    @Expose
    private String serialNo;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getValuationRate() {
        return valuationRate;
    }

    public void setValuationRate(Double valuationRate) {
        this.valuationRate = valuationRate;
    }

    public Double getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(Double currentQty) {
        this.currentQty = currentQty;
    }

    public Double getCurrentValuationRate() {
        return currentValuationRate;
    }

    public void setCurrentValuationRate(Double currentValuationRate) {
        this.currentValuationRate = currentValuationRate;
    }

    public String getCurrentSerialNo() {
        return currentSerialNo;
    }

    public void setCurrentSerialNo(String currentSerialNo) {
        this.currentSerialNo = currentSerialNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
