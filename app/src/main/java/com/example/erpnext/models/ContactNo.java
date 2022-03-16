package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactNo {
    @SerializedName("Number")
    @Expose
    private String number;

    @SerializedName("is_primary")
    @Expose
    private boolean isPrimary;

    public ContactNo() {
    }

    public ContactNo(String number, boolean isPrimary) {
        this.number = number;
        this.isPrimary = isPrimary;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
