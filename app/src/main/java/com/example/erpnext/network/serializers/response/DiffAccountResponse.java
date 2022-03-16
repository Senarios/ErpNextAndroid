package com.example.erpnext.network.serializers.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiffAccountResponse {
    @SerializedName("message")
    @Expose
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
