package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocClosing {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("creation")
    @Expose
    private String creation;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("parent")
    @Expose
    private Object parent;
    @SerializedName("parentfield")
    @Expose
    private Object parentfield;
    @SerializedName("parenttype")
    @Expose
    private Object parenttype;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("period_start_date")
    @Expose
    private String periodStartDate;
    @SerializedName("period_end_date")
    @Expose
    private String periodEndDate;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("pos_opening_entry")
    @Expose
    private String posOpeningEntry;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("pos_profile")
    @Expose
    private String posProfile;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("grand_total")
    @Expose
    private Double grandTotal;
    @SerializedName("net_total")
    @Expose
    private Double netTotal;
    @SerializedName("total_quantity")
    @Expose
    private Double totalQuantity;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    @SerializedName("amended_from")
    @Expose
    private Object amendedFrom;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("pos_transactions")
    @Expose
    private List<PosTransaction> posTransactions = null;
    @SerializedName("payment_reconciliation")
    @Expose
    private List<PaymentReconciliation> paymentReconciliation = null;
    @SerializedName("taxes")
    @Expose
    private List<Object> taxes = null;
    @SerializedName("localname")
    @Expose
    private String localname;

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

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Object getParentfield() {
        return parentfield;
    }

    public void setParentfield(Object parentfield) {
        this.parentfield = parentfield;
    }

    public Object getParenttype() {
        return parenttype;
    }

    public void setParenttype(Object parenttype) {
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

    public String getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodStartDate(String periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    public String getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(String periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getPosOpeningEntry() {
        return posOpeningEntry;
    }

    public void setPosOpeningEntry(String posOpeningEntry) {
        this.posOpeningEntry = posOpeningEntry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosProfile() {
        return posProfile;
    }

    public void setPosProfile(String posProfile) {
        this.posProfile = posProfile;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(Double netTotal) {
        this.netTotal = netTotal;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getAmendedFrom() {
        return amendedFrom;
    }

    public void setAmendedFrom(Object amendedFrom) {
        this.amendedFrom = amendedFrom;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public List<PosTransaction> getPosTransactions() {
        return posTransactions;
    }

    public void setPosTransactions(List<PosTransaction> posTransactions) {
        this.posTransactions = posTransactions;
    }

    public List<PaymentReconciliation> getPaymentReconciliation() {
        return paymentReconciliation;
    }

    public void setPaymentReconciliation(List<PaymentReconciliation> paymentReconciliation) {
        this.paymentReconciliation = paymentReconciliation;
    }

    public List<Object> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Object> taxes) {
        this.taxes = taxes;
    }

    public String getLocalname() {
        return localname;
    }

    public void setLocalname(String localname) {
        this.localname = localname;
    }
}
