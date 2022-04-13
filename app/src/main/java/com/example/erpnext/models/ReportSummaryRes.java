package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportSummaryRes {
    @SerializedName("message")
    @Expose
    private MessageSummary message;

    public MessageSummary getMessage() {
        return message;
    }

    public void setMessage(MessageSummary message) {
        this.message = message;
    }
}
