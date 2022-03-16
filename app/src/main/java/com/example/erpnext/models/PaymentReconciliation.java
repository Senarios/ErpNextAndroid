package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentReconciliation {
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("__islocal")
    @Expose
    private Integer islocal;
    @SerializedName("__unsaved")
    @Expose
    private Integer unsaved;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("closing_amount")
    @Expose
    private float closingAmount;
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
    @SerializedName("mode_of_payment")
    @Expose
    private String modeOfPayment;
    @SerializedName("opening_amount")
    @Expose
    private String openingAmount;
    @SerializedName("expected_amount")
    @Expose
    private Double expectedAmount;
    @SerializedName("difference")
    @Expose
    private Double difference;

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public float getClosingAmount() {
        return closingAmount;
    }

    public void setClosingAmount(float closingAmount) {
        this.closingAmount = closingAmount;
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

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getOpeningAmount() {
        return openingAmount;
    }

    public void setOpeningAmount(String openingAmount) {
        this.openingAmount = openingAmount;
    }

    public Double getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(Double expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

}
