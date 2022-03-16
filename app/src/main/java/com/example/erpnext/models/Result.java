package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("bal_qty")
    @Expose
    private Double balQty;
    @SerializedName("bal_val")
    @Expose
    private Double balVal;
    @SerializedName("stock_value")
    @Expose
    private Double stockValue;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("voucher_no")
    @Expose
    private String voucherNo;
    @SerializedName("voucher_type")
    @Expose
    private String voucherType;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("in_qty")
    @Expose
    private Double inQty;
    @SerializedName("in_val")
    @Expose
    private Double inVal;
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("item_group")
    @Expose
    private String itemGroup;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("opening_qty")
    @Expose
    private Double openingQty;
    @SerializedName("opening_val")
    @Expose
    private Double openingVal;
    @SerializedName("out_qty")
    @Expose
    private Double outQty;
    @SerializedName("qty_after_transaction")
    @Expose
    private Double qtyAfterTransaction;
    @SerializedName("out_val")
    @Expose
    private Double outVal;
    @SerializedName("incoming_rate")
    @Expose
    private Double incomingRate;
    @SerializedName("reorder_level")
    @Expose
    private Integer reorderLevel;
    @SerializedName("reorder_qty")
    @Expose
    private Integer reorderQty;
    @SerializedName("stock_uom")
    @Expose
    private String stockUom;
    @SerializedName("batch_no")
    @Expose
    private String batchNo;


    @SerializedName("serial_no")
    @Expose
    private String serialNo;
    @SerializedName("project")
    @Expose
    private String project;
    @SerializedName("val_rate")
    @Expose
    private Double valRate;
    @SerializedName("valuation_rate")
    @Expose
    private Double valuationRate;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;

    public Double getBalQty() {
        return balQty;
    }

    public void setBalQty(Double balQty) {
        this.balQty = balQty;
    }

    public Double getBalVal() {
        return balVal;
    }

    public void setBalVal(Double balVal) {
        this.balVal = balVal;
    }

    public Double getIncomingRate() {
        return incomingRate;
    }

    public void setIncomingRate(Double incomingRate) {
        this.incomingRate = incomingRate;
    }

    public Double getValuationRate() {
        return valuationRate;
    }

    public void setValuationRate(Double valuationRate) {
        this.valuationRate = valuationRate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getInQty() {
        return inQty;
    }

    public void setInQty(Double inQty) {
        this.inQty = inQty;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Double getStockValue() {
        return stockValue;
    }

    public void setStockValue(Double stockValue) {
        this.stockValue = stockValue;
    }

    public Double getInVal() {
        return inVal;
    }

    public void setInVal(Double inVal) {
        this.inVal = inVal;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOpeningQty() {
        return openingQty;
    }

    public void setOpeningQty(Double openingQty) {
        this.openingQty = openingQty;
    }

    public Double getOpeningVal() {
        return openingVal;
    }

    public void setOpeningVal(Double openingVal) {
        this.openingVal = openingVal;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Double getQtyAfterTransaction() {
        return qtyAfterTransaction;
    }

    public void setQtyAfterTransaction(Double qtyAfterTransaction) {
        this.qtyAfterTransaction = qtyAfterTransaction;
    }

    public Double getOutQty() {
        return outQty;
    }

    public void setOutQty(Double outQty) {
        this.outQty = outQty;
    }

    public Double getOutVal() {
        return outVal;
    }

    public void setOutVal(Double outVal) {
        this.outVal = outVal;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Integer getReorderQty() {
        return reorderQty;
    }

    public void setReorderQty(Integer reorderQty) {
        this.reorderQty = reorderQty;
    }

    public String getStockUom() {
        return stockUom;
    }

    public void setStockUom(String stockUom) {
        this.stockUom = stockUom;
    }

    public Double getValRate() {
        return valRate;
    }

    public void setValRate(Double valRate) {
        this.valRate = valRate;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }


}
