package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.Invoice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteOrderResponse {
    @SerializedName("data")
    @Expose
    private Invoice invoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
