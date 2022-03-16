package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.ReportMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportResponse {
    @SerializedName("message")
    @Expose
    private ReportMessage message;

    public ReportMessage getMessage() {
        return message;
    }

    public void setMessage(ReportMessage message) {
        this.message = message;
    }
}
