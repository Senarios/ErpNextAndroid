package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RunDocMessage {
    @SerializedName("print_format")
    @Expose
    private String printFormat;
    @SerializedName("campaign")
    @Expose
    private Object campaign;
    @SerializedName("allow_print_before_pay")
    @Expose
    private Object allowPrintBeforePay;

    public String getPrintFormat() {
        return printFormat;
    }

    public void setPrintFormat(String printFormat) {
        this.printFormat = printFormat;
    }

    public Object getCampaign() {
        return campaign;
    }

    public void setCampaign(Object campaign) {
        this.campaign = campaign;
    }

    public Object getAllowPrintBeforePay() {
        return allowPrintBeforePay;
    }

    public void setAllowPrintBeforePay(Object allowPrintBeforePay) {
        this.allowPrintBeforePay = allowPrintBeforePay;
    }
}
