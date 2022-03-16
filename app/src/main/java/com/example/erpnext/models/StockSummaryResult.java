package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockSummaryResult {

    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("current_qty")
    @Expose
    private Double currentQty;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(Double currentQty) {
        this.currentQty = currentQty;
    }

}