package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockSummDatum {
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("projected_qty")
    @Expose
    private Double projectedQty;
    @SerializedName("reserved_qty")
    @Expose
    private Double reservedQty;
    @SerializedName("reserved_qty_for_production")
    @Expose
    private Double reservedQtyForProduction;
    @SerializedName("reserved_qty_for_sub_contract")
    @Expose
    private Double reservedQtyForSubContract;
    @SerializedName("actual_qty")
    @Expose
    private Double actualQty;
    @SerializedName("valuation_rate")
    @Expose
    private Double valuationRate;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("disable_quick_entry")
    @Expose
    private Integer disableQuickEntry;

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

    public Double getProjectedQty() {
        return projectedQty;
    }

    public void setProjectedQty(Double projectedQty) {
        this.projectedQty = projectedQty;
    }

    public Double getReservedQty() {
        return reservedQty;
    }

    public void setReservedQty(Double reservedQty) {
        this.reservedQty = reservedQty;
    }

    public Double getReservedQtyForProduction() {
        return reservedQtyForProduction;
    }

    public void setReservedQtyForProduction(Double reservedQtyForProduction) {
        this.reservedQtyForProduction = reservedQtyForProduction;
    }

    public Double getReservedQtyForSubContract() {
        return reservedQtyForSubContract;
    }

    public void setReservedQtyForSubContract(Double reservedQtyForSubContract) {
        this.reservedQtyForSubContract = reservedQtyForSubContract;
    }

    public Double getActualQty() {
        return actualQty;
    }

    public void setActualQty(Double actualQty) {
        this.actualQty = actualQty;
    }

    public Double getValuationRate() {
        return valuationRate;
    }

    public void setValuationRate(Double valuationRate) {
        this.valuationRate = valuationRate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getDisableQuickEntry() {
        return disableQuickEntry;
    }

    public void setDisableQuickEntry(Integer disableQuickEntry) {
        this.disableQuickEntry = disableQuickEntry;
    }
}
