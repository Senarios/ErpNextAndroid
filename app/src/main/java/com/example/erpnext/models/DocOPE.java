package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocOPE {
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
    @SerializedName("opening_amount")
    @Expose
    private float opening_amount;
    @SerializedName("period_start_date")
    @Expose
    private String periodStartDate;
    @SerializedName("period_end_date")
    @Expose
    private Object periodEndDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("set_posting_date")
    @Expose
    private Integer setPostingDate;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("pos_profile")
    @Expose
    private String posProfile;
    @SerializedName("pos_closing_entry")
    @Expose
    private Object posClosingEntry;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("amended_from")
    @Expose
    private Object amendedFrom;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("balance_details")
    @Expose
    private List<BalanceDetail> balanceDetails = null;
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

    public Object getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(Object periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public Integer getSetPostingDate() {
        return setPostingDate;
    }

    public void setSetPostingDate(Integer setPostingDate) {
        this.setPostingDate = setPostingDate;
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

    public Object getPosClosingEntry() {
        return posClosingEntry;
    }

    public void setPosClosingEntry(Object posClosingEntry) {
        this.posClosingEntry = posClosingEntry;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public List<BalanceDetail> getBalanceDetails() {
        return balanceDetails;
    }

    public void setBalanceDetails(List<BalanceDetail> balanceDetails) {
        this.balanceDetails = balanceDetails;
    }

    public String getLocalname() {
        return localname;
    }

    public void setLocalname(String localname) {
        this.localname = localname;
    }

    public float getOpening_amount() {
        return opening_amount;
    }

    public void setOpening_amount(float opening_amount) {
        this.opening_amount = opening_amount;
    }
}
