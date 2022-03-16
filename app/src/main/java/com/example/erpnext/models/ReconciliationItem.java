package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReconciliationItem {
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
    @SerializedName("allow_zero_valuation_rate")
    @Expose
    private Integer allowZeroValuationRate;
    @SerializedName("current_qty")
    @Expose
    private Integer currentQty;
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
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("valuation_rate")
    @Expose
    private Double valuationRate;
    @SerializedName("current_valuation_rate")
    @Expose
    private Integer currentValuationRate;
    @SerializedName("current_serial_no")
    @Expose
    private String currentSerialNo;
    @SerializedName("serial_no")
    @Expose
    private String serialNo;

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

    public Integer getAllowZeroValuationRate() {
        return allowZeroValuationRate;
    }

    public void setAllowZeroValuationRate(Integer allowZeroValuationRate) {
        this.allowZeroValuationRate = allowZeroValuationRate;
    }

    public Integer getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(Integer currentQty) {
        this.currentQty = currentQty;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getValuationRate() {
        return valuationRate;
    }

    public void setValuationRate(Double valuationRate) {
        this.valuationRate = valuationRate;
    }

    public Integer getCurrentValuationRate() {
        return currentValuationRate;
    }

    public void setCurrentValuationRate(Integer currentValuationRate) {
        this.currentValuationRate = currentValuationRate;
    }

    public String getCurrentSerialNo() {
        return currentSerialNo;
    }

    public void setCurrentSerialNo(String currentSerialNo) {
        this.currentSerialNo = currentSerialNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
