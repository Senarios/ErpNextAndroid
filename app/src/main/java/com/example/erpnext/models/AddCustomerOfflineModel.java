package com.example.erpnext.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class AddCustomerOfflineModel {
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("phoneNo")
    @Expose
    @NonNull
    @PrimaryKey
    private String phoneNo;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("lattitude")
    @Expose
    private double lattitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @NonNull
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(@NonNull String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
