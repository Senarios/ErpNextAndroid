package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.PurchaseInvoice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseInvoiceDetailResponse {
    @SerializedName("message")
    @Expose
    private PurchaseInvoice purchaseInvoice;

    public PurchaseInvoice getPurchaseInvoice() {
        return purchaseInvoice;
    }

    public void setPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
        this.purchaseInvoice = purchaseInvoice;
    }
}
