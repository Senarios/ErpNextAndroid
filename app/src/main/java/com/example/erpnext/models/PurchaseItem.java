package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PurchaseItem {
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
    @SerializedName("stock_uom")
    @Expose
    private String stockUom;
    @SerializedName("retain_sample")
    @Expose
    private Integer retainSample;
    @SerializedName("margin_type")
    @Expose
    private String marginType;
    @SerializedName("is_free_item")
    @Expose
    private Integer isFreeItem;
    @SerializedName("is_fixed_asset")
    @Expose
    private Integer isFixedAsset;
    @SerializedName("allow_zero_valuation_rate")
    @Expose
    private Integer allowZeroValuationRate;
    @SerializedName("include_exploded_items")
    @Expose
    private Integer includeExplodedItems;
    @SerializedName("cost_center")
    @Expose
    private String costCenter;
    @SerializedName("page_break")
    @Expose
    private Integer pageBreak;
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
    @SerializedName("__unedited")
    @Expose
    private Boolean unedited;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("sample_quantity")
    @Expose
    private Integer sampleQuantity;
    @SerializedName("received_qty")
    @Expose
    private Integer receivedQty;
    @SerializedName("rejected_qty")
    @Expose
    private Integer rejectedQty;
    @SerializedName("received_stock_qty")
    @Expose
    private Integer receivedStockQty;
    @SerializedName("conversion_factor")
    @Expose
    private Integer conversionFactor;
    @SerializedName("stock_qty")
    @Expose
    private Integer stockQty;
    @SerializedName("total_weight")
    @Expose
    private float totalWeight;
    @SerializedName("stock_uom_rate")
    @Expose
    private Double stockUomRate;
    @SerializedName("returned_qty")
    @Expose
    private Integer returnedQty;
    @SerializedName("price_list_rate")
    @Expose
    private Double priceListRate;
    @SerializedName("base_price_list_rate")
    @Expose
    private Double basePriceListRate;
    @SerializedName("margin_rate_or_amount")
    @Expose
    private Integer marginRateOrAmount;
    @SerializedName("rate_with_margin")
    @Expose
    private Integer rateWithMargin;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("base_rate_with_margin")
    @Expose
    private Integer baseRateWithMargin;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("base_rate")
    @Expose
    private Double baseRate;
    @SerializedName("base_amount")
    @Expose
    private Double baseAmount;
    @SerializedName("net_rate")
    @Expose
    private Double netRate;
    @SerializedName("net_amount")
    @Expose
    private Double netAmount;
    @SerializedName("base_net_rate")
    @Expose
    private Double baseNetRate;
    @SerializedName("base_net_amount")
    @Expose
    private Double baseNetAmount;
    @SerializedName("valuation_rate")
    @Expose
    private Double valuationRate;
    @SerializedName("item_tax_amount")
    @Expose
    private Integer itemTaxAmount;
    @SerializedName("rm_supp_cost")
    @Expose
    private Integer rmSuppCost;
    @SerializedName("landed_cost_voucher_amount")
    @Expose
    private Integer landedCostVoucherAmount;
    @SerializedName("billed_amt")
    @Expose
    private Integer billedAmt;
    @SerializedName("weight_per_unit")
    @Expose
    private double weightPerUnit;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("income_account")
    @Expose
    private String incomeAccount;
    @SerializedName("expense_account")
    @Expose
    private String expenseAccount;
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
    @SerializedName("discount_percentage")
    @Expose
    private Integer discountPercentage;
    @SerializedName("supplier")
    @Expose
    private Object supplier;
    @SerializedName("update_stock")
    @Expose
    private Integer updateStock;
    @SerializedName("delivered_by_supplier")
    @Expose
    private Integer deliveredBySupplier;
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
    @SerializedName("weight_uom")
    @Expose
    private String weightUom;
    @SerializedName("item_group")
    @Expose
    private String itemGroup;
    @SerializedName("barcodes")
    @Expose
    private List<Object> barcodes = null;
    @SerializedName("brand")
    @Expose
    private Object brand;
    @SerializedName("manufacturer")
    @Expose
    private Object manufacturer;
    @SerializedName("manufacturer_part_no")
    @Expose
    private Object manufacturerPartNo;
    @SerializedName("item_tax_rate")
    @Expose
    private String itemTaxRate;
    @SerializedName("supplier_part_no")
    @Expose
    private Object supplierPartNo;
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
    @SerializedName("has_margin")
    @Expose
    private Boolean hasMargin;
    @SerializedName("free_item_data")
    @Expose
    private String freeItemData;
    @SerializedName("child_docname")
    @Expose
    private String childDocname;
    @SerializedName("gross_profit")
    @Expose
    private Double grossProfit;
    @SerializedName("serial_no")
    @Expose
    private Object serialNo;

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

    public String getStockUom() {
        return stockUom;
    }

    public void setStockUom(String stockUom) {
        this.stockUom = stockUom;
    }

    public Integer getRetainSample() {
        return retainSample;
    }

    public void setRetainSample(Integer retainSample) {
        this.retainSample = retainSample;
    }

    public String getMarginType() {
        return marginType;
    }

    public void setMarginType(String marginType) {
        this.marginType = marginType;
    }

    public Integer getIsFreeItem() {
        return isFreeItem;
    }

    public void setIsFreeItem(Integer isFreeItem) {
        this.isFreeItem = isFreeItem;
    }

    public Integer getIsFixedAsset() {
        return isFixedAsset;
    }

    public void setIsFixedAsset(Integer isFixedAsset) {
        this.isFixedAsset = isFixedAsset;
    }

    public Integer getAllowZeroValuationRate() {
        return allowZeroValuationRate;
    }

    public void setAllowZeroValuationRate(Integer allowZeroValuationRate) {
        this.allowZeroValuationRate = allowZeroValuationRate;
    }

    public Integer getIncludeExplodedItems() {
        return includeExplodedItems;
    }

    public void setIncludeExplodedItems(Integer includeExplodedItems) {
        this.includeExplodedItems = includeExplodedItems;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public Integer getPageBreak() {
        return pageBreak;
    }

    public void setPageBreak(Integer pageBreak) {
        this.pageBreak = pageBreak;
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

    public Boolean getUnedited() {
        return unedited;
    }

    public void setUnedited(Boolean unedited) {
        this.unedited = unedited;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getSampleQuantity() {
        return sampleQuantity;
    }

    public void setSampleQuantity(Integer sampleQuantity) {
        this.sampleQuantity = sampleQuantity;
    }

    public Integer getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(Integer receivedQty) {
        this.receivedQty = receivedQty;
    }

    public Integer getRejectedQty() {
        return rejectedQty;
    }

    public void setRejectedQty(Integer rejectedQty) {
        this.rejectedQty = rejectedQty;
    }

    public Integer getReceivedStockQty() {
        return receivedStockQty;
    }

    public void setReceivedStockQty(Integer receivedStockQty) {
        this.receivedStockQty = receivedStockQty;
    }

    public Integer getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(Integer conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Double getStockUomRate() {
        return stockUomRate;
    }

    public void setStockUomRate(Double stockUomRate) {
        this.stockUomRate = stockUomRate;
    }

    public Integer getReturnedQty() {
        return returnedQty;
    }

    public void setReturnedQty(Integer returnedQty) {
        this.returnedQty = returnedQty;
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

    public Integer getMarginRateOrAmount() {
        return marginRateOrAmount;
    }

    public void setMarginRateOrAmount(Integer marginRateOrAmount) {
        this.marginRateOrAmount = marginRateOrAmount;
    }

    public Integer getRateWithMargin() {
        return rateWithMargin;
    }

    public void setRateWithMargin(Integer rateWithMargin) {
        this.rateWithMargin = rateWithMargin;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getBaseRateWithMargin() {
        return baseRateWithMargin;
    }

    public void setBaseRateWithMargin(Integer baseRateWithMargin) {
        this.baseRateWithMargin = baseRateWithMargin;
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

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
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

    public Double getBaseNetRate() {
        return baseNetRate;
    }

    public void setBaseNetRate(Double baseNetRate) {
        this.baseNetRate = baseNetRate;
    }

    public Double getBaseNetAmount() {
        return baseNetAmount;
    }

    public void setBaseNetAmount(Double baseNetAmount) {
        this.baseNetAmount = baseNetAmount;
    }

    public Double getValuationRate() {
        return valuationRate;
    }

    public void setValuationRate(Double valuationRate) {
        this.valuationRate = valuationRate;
    }

    public Integer getItemTaxAmount() {
        return itemTaxAmount;
    }

    public void setItemTaxAmount(Integer itemTaxAmount) {
        this.itemTaxAmount = itemTaxAmount;
    }

    public Integer getRmSuppCost() {
        return rmSuppCost;
    }

    public void setRmSuppCost(Integer rmSuppCost) {
        this.rmSuppCost = rmSuppCost;
    }

    public Integer getLandedCostVoucherAmount() {
        return landedCostVoucherAmount;
    }

    public void setLandedCostVoucherAmount(Integer landedCostVoucherAmount) {
        this.landedCostVoucherAmount = landedCostVoucherAmount;
    }

    public Integer getBilledAmt() {
        return billedAmt;
    }

    public void setBilledAmt(Integer billedAmt) {
        this.billedAmt = billedAmt;
    }

    public double getWeightPerUnit() {
        return weightPerUnit;
    }

    public void setWeightPerUnit(double weightPerUnit) {
        this.weightPerUnit = weightPerUnit;
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

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
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

    public String getWeightUom() {
        return weightUom;
    }

    public void setWeightUom(String weightUom) {
        this.weightUom = weightUom;
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

    public Boolean getHasMargin() {
        return hasMargin;
    }

    public void setHasMargin(Boolean hasMargin) {
        this.hasMargin = hasMargin;
    }

    public String getFreeItemData() {
        return freeItemData;
    }

    public void setFreeItemData(String freeItemData) {
        this.freeItemData = freeItemData;
    }

    public String getChildDocname() {
        return childDocname;
    }

    public void setChildDocname(String childDocname) {
        this.childDocname = childDocname;
    }

    public Double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(Double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Object getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Object serialNo) {
        this.serialNo = serialNo;
    }

}
