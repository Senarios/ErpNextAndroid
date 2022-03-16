package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchItemDetail {
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("income_account")
    @Expose
    private String incomeAccount;
    @SerializedName("expense_account")
    @Expose
    private String expenseAccount;
    @SerializedName("cost_center")
    @Expose
    private String costCenter;
    @SerializedName("has_serial_no")
    @Expose
    private Integer hasSerialNo;
    @SerializedName("has_batch_no")
    @Expose
    private Integer hasBatchNo;
    @SerializedName("batch_no")
    @Expose
    private Object batchNo;
    @SerializedName("uom")
    @Expose
    private String uom;
    @SerializedName("min_order_qty")
    @Expose
    private String minOrderQty;
    @SerializedName("qty")
    @Expose
    private Double qty;
    @SerializedName("stock_qty")
    @Expose
    private Double stockQty;
    @SerializedName("price_list_rate")
    @Expose
    private Double priceListRate;
    @SerializedName("base_price_list_rate")
    @Expose
    private Double basePriceListRate;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("base_rate")
    @Expose
    private Double baseRate;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("base_amount")
    @Expose
    private Double baseAmount;
    @SerializedName("net_rate")
    @Expose
    private Double netRate;
    @SerializedName("net_amount")
    @Expose
    private Double netAmount;
    @SerializedName("discount_percentage")
    @Expose
    private Double discountPercentage;
    @SerializedName("supplier")
    @Expose
    private Object supplier;
    @SerializedName("update_stock")
    @Expose
    private Integer updateStock;
    @SerializedName("delivered_by_supplier")
    @Expose
    private Integer deliveredBySupplier;
    @SerializedName("is_fixed_asset")
    @Expose
    private Integer isFixedAsset;
    @SerializedName("last_purchase_rate")
    @Expose
    private Double lastPurchaseRate;
    @SerializedName("transaction_date")
    @Expose
    private String transactionDate;
    @SerializedName("against_blanket_order")
    @Expose
    private Object againstBlanketOrder;
    @SerializedName("bom_no")
    @Expose
    private Object bomNo;
    @SerializedName("weight_per_unit")
    @Expose
    private Double weightPerUnit;
    @SerializedName("weight_uom")
    @Expose
    private String weightUom;
    @SerializedName("conversion_factor")
    @Expose
    private Double conversionFactor;
    @SerializedName("item_group")
    @Expose
    private String itemGroup;
    @SerializedName("barcodes")
    @Expose
    private List<Object> barcodes = null;
    @SerializedName("brand")
    @Expose
    private Object brand;
    @SerializedName("stock_uom")
    @Expose
    private String stockUom;
    @SerializedName("manufacturer")
    @Expose
    private Object manufacturer;
    @SerializedName("manufacturer_part_no")
    @Expose
    private Object manufacturerPartNo;
    @SerializedName("total_weight")
    @Expose
    private Double totalWeight;
    @SerializedName("item_tax_rate")
    @Expose
    private String itemTaxRate;
    @SerializedName("supplier_part_no")
    @Expose
    private Object supplierPartNo;
    @SerializedName("valuation_rate")
    @Expose
    private double valuationRate;
    @SerializedName("projected_qty")
    @Expose
    private Integer projectedQty;
    @SerializedName("actual_qty")
    @Expose
    private Integer actualQty;
    @SerializedName("reserved_qty")
    @Expose
    private Integer reservedQty;
    @SerializedName("company_total_stock")
    @Expose
    private Double companyTotalStock;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("has_margin")
    @Expose
    private Boolean hasMargin;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("free_item_data")
    @Expose
    private List<Object> freeItemData = null;
    @SerializedName("parent")
    @Expose
    private Object parent;
    @SerializedName("parenttype")
    @Expose
    private Object parenttype;
    @SerializedName("child_docname")
    @Expose
    private Object childDocname;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getIncomeAccount() {
        return incomeAccount;
    }

    public void setIncomeAccount(String incomeAccount) {
        this.incomeAccount = incomeAccount;
    }

    public String getExpenseAccount() {
        return expenseAccount;
    }

    public void setExpenseAccount(String expenseAccount) {
        this.expenseAccount = expenseAccount;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public Integer getHasSerialNo() {
        return hasSerialNo;
    }

    public void setHasSerialNo(Integer hasSerialNo) {
        this.hasSerialNo = hasSerialNo;
    }

    public Integer getHasBatchNo() {
        return hasBatchNo;
    }

    public void setHasBatchNo(Integer hasBatchNo) {
        this.hasBatchNo = hasBatchNo;
    }

    public Object getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Object batchNo) {
        this.batchNo = batchNo;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(String minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getStockQty() {
        return stockQty;
    }

    public void setStockQty(Double stockQty) {
        this.stockQty = stockQty;
    }

    public Double getPriceListRate() {
        return priceListRate;
    }

    public void setPriceListRate(Double priceListRate) {
        this.priceListRate = priceListRate;
    }

    public Double getBasePriceListRate() {
        return basePriceListRate;
    }

    public void setBasePriceListRate(Double basePriceListRate) {
        this.basePriceListRate = basePriceListRate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Double getNetRate() {
        return netRate;
    }

    public void setNetRate(Double netRate) {
        this.netRate = netRate;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Object getSupplier() {
        return supplier;
    }

    public void setSupplier(Object supplier) {
        this.supplier = supplier;
    }

    public Integer getUpdateStock() {
        return updateStock;
    }

    public void setUpdateStock(Integer updateStock) {
        this.updateStock = updateStock;
    }

    public Integer getDeliveredBySupplier() {
        return deliveredBySupplier;
    }

    public void setDeliveredBySupplier(Integer deliveredBySupplier) {
        this.deliveredBySupplier = deliveredBySupplier;
    }

    public Integer getIsFixedAsset() {
        return isFixedAsset;
    }

    public void setIsFixedAsset(Integer isFixedAsset) {
        this.isFixedAsset = isFixedAsset;
    }

    public Double getLastPurchaseRate() {
        return lastPurchaseRate;
    }

    public void setLastPurchaseRate(Double lastPurchaseRate) {
        this.lastPurchaseRate = lastPurchaseRate;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Object getAgainstBlanketOrder() {
        return againstBlanketOrder;
    }

    public void setAgainstBlanketOrder(Object againstBlanketOrder) {
        this.againstBlanketOrder = againstBlanketOrder;
    }

    public Object getBomNo() {
        return bomNo;
    }

    public void setBomNo(Object bomNo) {
        this.bomNo = bomNo;
    }

    public Double getWeightPerUnit() {
        return weightPerUnit;
    }

    public void setWeightPerUnit(Double weightPerUnit) {
        this.weightPerUnit = weightPerUnit;
    }

    public String getWeightUom() {
        return weightUom;
    }

    public void setWeightUom(String weightUom) {
        this.weightUom = weightUom;
    }

    public Double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(Double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public List<Object> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(List<Object> barcodes) {
        this.barcodes = barcodes;
    }

    public Object getBrand() {
        return brand;
    }

    public void setBrand(Object brand) {
        this.brand = brand;
    }

    public String getStockUom() {
        return stockUom;
    }

    public void setStockUom(String stockUom) {
        this.stockUom = stockUom;
    }

    public Object getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Object manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Object getManufacturerPartNo() {
        return manufacturerPartNo;
    }

    public void setManufacturerPartNo(Object manufacturerPartNo) {
        this.manufacturerPartNo = manufacturerPartNo;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getItemTaxRate() {
        return itemTaxRate;
    }

    public void setItemTaxRate(String itemTaxRate) {
        this.itemTaxRate = itemTaxRate;
    }

    public Object getSupplierPartNo() {
        return supplierPartNo;
    }

    public void setSupplierPartNo(Object supplierPartNo) {
        this.supplierPartNo = supplierPartNo;
    }

    public double getValuationRate() {
        return valuationRate;
    }

    public void setValuationRate(double valuationRate) {
        this.valuationRate = valuationRate;
    }

    public Integer getProjectedQty() {
        return projectedQty;
    }

    public void setProjectedQty(Integer projectedQty) {
        this.projectedQty = projectedQty;
    }

    public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }

    public Integer getReservedQty() {
        return reservedQty;
    }

    public void setReservedQty(Integer reservedQty) {
        this.reservedQty = reservedQty;
    }

    public Double getCompanyTotalStock() {
        return companyTotalStock;
    }

    public void setCompanyTotalStock(Double companyTotalStock) {
        this.companyTotalStock = companyTotalStock;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public Boolean getHasMargin() {
        return hasMargin;
    }

    public void setHasMargin(Boolean hasMargin) {
        this.hasMargin = hasMargin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getFreeItemData() {
        return freeItemData;
    }

    public void setFreeItemData(List<Object> freeItemData) {
        this.freeItemData = freeItemData;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Object getParenttype() {
        return parenttype;
    }

    public void setParenttype(Object parenttype) {
        this.parenttype = parenttype;
    }

    public Object getChildDocname() {
        return childDocname;
    }

    public void setChildDocname(Object childDocname) {
        this.childDocname = childDocname;
    }

}
