package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Email {
    @SerializedName("email_id")
    @Expose
    private String email;

    @SerializedName("is_primary")
    @Expose
    private boolean isPrimary;

    public Email() {
    }

    public Email(String email, boolean isPrimary) {
        this.email = email;
        this.isPrimary = isPrimary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
