package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.Invoice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLinkedInvoicesResponse {
    @SerializedName("message")
    @Expose
    private List<Invoice> invoices = null;

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
