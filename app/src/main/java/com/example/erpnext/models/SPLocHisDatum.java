package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SPLocHisDatum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sales_person")
    @Expose
    private String salesPerson;
    @SerializedName("sales_officer")
    @Expose
    private String salesOfficer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getSalesOfficer() {
        return salesOfficer;
    }

    public void setSalesOfficer(String salesOfficer) {
        this.salesOfficer = salesOfficer;
    }
}
