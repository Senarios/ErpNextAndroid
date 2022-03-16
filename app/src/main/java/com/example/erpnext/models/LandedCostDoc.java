package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LandedCostDoc {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("naming_series")
    @Expose
    private String namingSeries;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("total_taxes_and_charges")
    @Expose
    private Double totalTaxesAndCharges;
    @SerializedName("distribute_charges_based_on")
    @Expose
    private String distributeChargesBasedOn;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("purchase_receipts")
    @Expose
    private List<PurchaseReceipt> purchaseReceipts = null;
    @SerializedName("items")
    @Expose
    private List<LandedCostItem> items = null;
    @SerializedName("taxes")
    @Expose
    private List<Tax> taxes = null;
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

    public String getNamingSeries() {
        return namingSeries;
    }

    public void setNamingSeries(String namingSeries) {
        this.namingSeries = namingSeries;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public Double getTotalTaxesAndCharges() {
        return totalTaxesAndCharges;
    }

    public void setTotalTaxesAndCharges(Double totalTaxesAndCharges) {
        this.totalTaxesAndCharges = totalTaxesAndCharges;
    }

    public String getDistributeChargesBasedOn() {
        return distributeChargesBasedOn;
    }

    public void setDistributeChargesBasedOn(String distributeChargesBasedOn) {
        this.distributeChargesBasedOn = distributeChargesBasedOn;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public List<PurchaseReceipt> getPurchaseReceipts() {
        return purchaseReceipts;
    }

    public void setPurchaseReceipts(List<PurchaseReceipt> purchaseReceipts) {
        this.purchaseReceipts = purchaseReceipts;
    }


    public List<LandedCostItem> getItems() {
        return items;
    }

    public void setItems(List<LandedCostItem> items) {
        this.items = items;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
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
