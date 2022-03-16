package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LandedCostItem {
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
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("receipt_document_type")
    @Expose
    private String receiptDocumentType;
    @SerializedName("receipt_document")
    @Expose
    private String receiptDocument;
    @SerializedName("qty")
    @Expose
    private Double qty;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("is_fixed_asset")
    @Expose
    private Integer isFixedAsset;
    @SerializedName("applicable_charges")
    @Expose
    private Double applicableCharges;
    @SerializedName("purchase_receipt_item")
    @Expose
    private String purchaseReceiptItem;
    @SerializedName("cost_center")
    @Expose
    private String costCenter;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("__islocal")
    @Expose
    private Integer islocal;

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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getIsFixedAsset() {
        return isFixedAsset;
    }

    public void setIsFixedAsset(Integer isFixedAsset) {
        this.isFixedAsset = isFixedAsset;
    }

    public Double getApplicableCharges() {
        return applicableCharges;
    }

    public void setApplicableCharges(Double applicableCharges) {
        this.applicableCharges = applicableCharges;
    }

    public String getPurchaseReceiptItem() {
        return purchaseReceiptItem;
    }

    public void setPurchaseReceiptItem(String purchaseReceiptItem) {
        this.purchaseReceiptItem = purchaseReceiptItem;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
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

}
