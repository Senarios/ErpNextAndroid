package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LandedCostReceipt {
    @SerializedName("receipt_doc_type")
    @Expose
    private String receiptDocType;
    @SerializedName("invoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("supplier")
    @Expose
    private String supplier;
    @SerializedName("grand_total")
    @Expose
    private String grand_total;

    public LandedCostReceipt(String receiptDocType, String invoiceNo, String supplier, String grand_total) {
        this.receiptDocType = receiptDocType;
        this.invoiceNo = invoiceNo;
        this.supplier = supplier;
        this.grand_total = grand_total;
    }

    public String getReceiptDocType() {
        return receiptDocType;
    }

    public void setReceiptDocType(String receiptDocType) {
        this.receiptDocType = receiptDocType;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }
}
