package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockSummRes {

    @SerializedName("message")
    @Expose
    private List<StockSummDatum> message = null;

    public List<StockSummDatum> getMessage() {
        return message;
    }

    public void setMessage(List<StockSummDatum> message) {
        this.message = message;
    }
}
