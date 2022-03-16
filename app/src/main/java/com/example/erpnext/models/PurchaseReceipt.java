package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseReceipt {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("parent")
    @Expose
    private String parent;
    @SerializedName("parentfield")
    @Expose
    private String parentfield;
    @SerializedName("parenttype")
    @Expose
    private String parenttype;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("receipt_document_type")
    @Expose
    private String receiptDocumentType;
    @SerializedName("receipt_document")
    @Expose
    private String receiptDocument;
    @SerializedName("supplier")
    @Expose
    private String supplier;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("grand_total")
    @Expose
    private Double grandTotal;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("__islocal")
    @Expose
    private Integer islocal;
    @SerializedName("__unsaved")
    @Expose
    private Integer unsaved;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentfield() {
        return parentfield;
    }

    public void setParentfield(String parentfield) {
        this.parentfield = parentfield;
    }

    public String getParenttype() {
        return parenttype;
    }

    public void setParenttype(String parenttype) {
        this.parenttype = parenttype;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getReceiptDocumentType() {
        return receiptDocumentType;
    }

    public void setReceiptDocumentType(String receiptDocumentType) {
        this.receiptDocumentType = receiptDocumentType;
    }

    public String getReceiptDocument() {
        return receiptDocument;
    }

    public void setReceiptDocument(String receiptDocument) {
        this.receiptDocument = receiptDocument;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public Integer getIslocal() {
        return islocal;
    }

    public void setIslocal(Integer islocal) {
        this.islocal = islocal;
    }

    public Integer getUnsaved() {
        return unsaved;
    }

    public void setUnsaved(Integer unsaved) {
        this.unsaved = unsaved;
    }
}
