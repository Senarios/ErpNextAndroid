package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.DocInfo;
import com.example.erpnext.models.Invoice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PosInvoiceResponse {
    @SerializedName("docs")
    @Expose
    private List<Invoice> invoices = null;
    @SerializedName("docinfo")
    @Expose
    private DocInfo docinfo;

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public DocInfo getDocinfo() {
        return docinfo;
    }

    public void setDocinfo(DocInfo docinfo) {
        this.docinfo = docinfo;
    }
}
