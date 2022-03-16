package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditLimit {
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("name")
    @Expose
    private String creditLimit;
    @SerializedName("bypass")
    @Expose
    private boolean isByPass;

    public CreditLimit() {
    }

    public CreditLimit(String company, String creditLimit, boolean isByPass) {
        this.company = company;
        this.creditLimit = creditLimit;
        this.isByPass = isByPass;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public boolean isByPass() {
        return isByPass;
    }

    public void setByPass(boolean byPass) {
        isByPass = byPass;
    }
}
