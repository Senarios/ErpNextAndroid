package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesTeam {

    @SerializedName("sales_person")
    @Expose
    private String salesPerson;
    @SerializedName("allocated_percentage")
    @Expose
    private Double allocatedPercentage;

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public Double getAllocatedPercentage() {
        return allocatedPercentage;
    }

    public void setAllocatedPercentage(Double allocatedPercentage) {
        this.allocatedPercentage = allocatedPercentage;
    }
}
