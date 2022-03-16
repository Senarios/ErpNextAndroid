package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.erpnext.network.serializers.requestbody.InvoiceActionRequestBody;
import com.google.gson.annotations.SerializedName;

@Entity
public class PendingInvoiceAction {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @SerializedName("pending_cancel_invoice")
    @Embedded
    private InvoiceActionRequestBody invoiceActionRequestBody;

    public PendingInvoiceAction() {
    }

    public InvoiceActionRequestBody getCancelInvoiceRequestBody() {
        return invoiceActionRequestBody;
    }

    public void setCancelInvoiceRequestBody(InvoiceActionRequestBody invoiceActionRequestBody) {
        this.invoiceActionRequestBody = invoiceActionRequestBody;
    }

    public InvoiceActionRequestBody getInvoiceActionRequestBody() {
        return invoiceActionRequestBody;
    }

    public void setInvoiceActionRequestBody(InvoiceActionRequestBody invoiceActionRequestBody) {
        this.invoiceActionRequestBody = invoiceActionRequestBody;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
