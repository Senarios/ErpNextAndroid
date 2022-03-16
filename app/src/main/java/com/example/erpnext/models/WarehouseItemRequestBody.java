package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WarehouseItemRequestBody {

    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("posting_time")
    @Expose
    private String postingTime;
    @SerializedName("company")
    @Expose
    private String company;

    public WarehouseItemRequestBody(String warehouse, String postingDate, String postingTime, String company) {
        this.warehouse = warehouse;
        this.postingDate = postingDate;
        this.postingTime = postingTime;
        this.company = company;
    }

    public WarehouseItemRequestBody() {
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(String postingTime) {
        this.postingTime = postingTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
