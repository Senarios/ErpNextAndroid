package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceItem {
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
    @SerializedName("barcode")
    @Expose
    private Object barcode;
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("customer_item_code")
    @Expose
    private Object customerItemCode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("item_group")
    @Expose
    private String itemGroup;
    @SerializedName("brand")
    @Expose
    private Object brand;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("qty")
    @Expose
    private Double qty;
    @SerializedName("stock_uom")
    @Expose
    private String stockUom;
    @SerializedName("uom")
    @Expose
    private String uom;
    @SerializedName("conversion_factor")
    @Expose
    private Double conversionFactor;
    @SerializedName("stock_qty")
    @Expose
    private Double stockQty;
    @SerializedName("price_list_rate")
    @Expose
    private Double priceListRate;
    @SerializedName("base_price_list_rate")
    @Expose
    private Double basePriceListRate;
    @SerializedName("margin_type")
    @Expose
    private String marginType;
    @SerializedName("margin_rate_or_amount")
    @Expose
    private Double marginRateOrAmount;
    @SerializedName("rate_with_margin")
    @Expose
    private Double rateWithMargin;
    @SerializedName("discount_percentage")
    @Expose
    private Double discountPercentage;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("base_rate_with_margin")
    @Expose
    private Double baseRateWithMargin;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("item_tax_template")
    @Expose
    private Object itemTaxTemplate;
    @SerializedName("base_rate")
    @Expose
    private Double baseRate;
    @SerializedName("base_amount")
    @Expose
    private Double baseAmount;
    @SerializedName("pricing_rules")
    @Expose
    private Object pricingRules;
    @SerializedName("is_free_item")
    @Expose
    private Integer isFreeItem;
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
    @SerializedName("delivered_by_supplier")
    @Expose
    private Integer deliveredBySupplier;
    @SerializedName("income_account")
    @Expose
    private String incomeAccount;
    @SerializedName("is_fixed_asset")
    @Expose
    private Integer isFixedAsset;
    @SerializedName("asset")
    @Expose
    private Object asset;
    @SerializedName("finance_book")
    @Expose
    private Object financeBook;
    @SerializedName("expense_account")
    @Expose
    private String expenseAccount;
    @SerializedName("deferred_revenue_account")
    @Expose
    private Object deferredRevenueAccount;
    @SerializedName("service_stop_date")
    @Expose
    private Object serviceStopDate;
    @SerializedName("enable_deferred_revenue")
    @Expose
    private Integer enableDeferredRevenue;
    @SerializedName("service_start_date")
    @Expose
    private Object serviceStartDate;
    @SerializedName("service_end_date")
    @Expose
    private Object serviceEndDate;
    @SerializedName("weight_per_unit")
    @Expose
    private Double weightPerUnit;
    @SerializedName("total_weight")
    @Expose
    private Double totalWeight;
    @SerializedName("weight_uom")
    @Expose
    private Object weightUom;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("target_warehouse")
    @Expose
    private Object targetWarehouse;
    @SerializedName("quality_inspection")
    @Expose
    private Object qualityInspection;
    @SerializedName("batch_no")
    @Expose
    private Object batchNo;
    @SerializedName("allow_zero_valuation_rate")
    @Expose
    private Integer allowZeroValuationRate;
    @SerializedName("serial_no")
    @Expose
    private Object serialNo;
    @SerializedName("item_tax_rate")
    @Expose
    private String itemTaxRate;
    @SerializedName("actual_batch_qty")
    @Expose
    private Double actualBatchQty;
    @SerializedName("actual_qty")
    @Expose
    private Double actualQty;
    @SerializedName("sales_order")
    @Expose
    private Object salesOrder;
    @SerializedName("so_detail")
    @Expose
    private Object soDetail;
    @SerializedName("pos_invoice_item")
    @Expose
    private Object posInvoiceItem;
    @SerializedName("delivery_note")
    @Expose
    private Object deliveryNote;
    @SerializedName("dn_detail")
    @Expose
    private Object dnDetail;
    @SerializedName("delivered_qty")
    @Expose
    private Double deliveredQty;
    @SerializedName("cost_center")
    @Expose
    private String costCenter;
    @SerializedName("project")
    @Expose
    private Object project;
    @SerializedName("page_break")
    @Expose
    private Integer pageBreak;
    @SerializedName("doctype")
    @Expose
    private String doctype;
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

    public Object getBarcode() {
        return barcode;
    }

    public void setBarcode(Object barcode) {
        this.barcode = barcode;
    }

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

    public Object getCustomerItemCode() {
        return customerItemCode;
    }

    public void setCustomerItemCode(Object customerItemCode) {
        this.customerItemCode = customerItemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public Object getBrand() {
        return brand;
    }

    public void setBrand(Object brand) {
        this.brand = brand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getStockUom() {
        return stockUom;
    }

    public void setStockUom(String stockUom) {
        this.stockUom = stockUom;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(Double conversionFactor) {
        this.conversionFactor = conversionFactor;
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

    public String getMarginType() {
        return marginType;
    }

    public void setMarginType(String marginType) {
        this.marginType = marginType;
    }

    public Double getMarginRateOrAmount() {
        return marginRateOrAmount;
    }

    public void setMarginRateOrAmount(Double marginRateOrAmount) {
        this.marginRateOrAmount = marginRateOrAmount;
    }

    public Double getRateWithMargin() {
        return rateWithMargin;
    }

    public void setRateWithMargin(Double rateWithMargin) {
        this.rateWithMargin = rateWithMargin;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getBaseRateWithMargin() {
        return baseRateWithMargin;
    }

    public void setBaseRateWithMargin(Double baseRateWithMargin) {
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

    public Object getItemTaxTemplate() {
        return itemTaxTemplate;
    }

    public void setItemTaxTemplate(Object itemTaxTemplate) {
        this.itemTaxTemplate = itemTaxTemplate;
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

    public Object getPricingRules() {
        return pricingRules;
    }

    public void setPricingRules(Object pricingRules) {
        this.pricingRules = pricingRules;
    }

    public Integer getIsFreeItem() {
        return isFreeItem;
    }

    public void setIsFreeItem(Integer isFreeItem) {
        this.isFreeItem = isFreeItem;
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

    public Integer getDeliveredBySupplier() {
        return deliveredBySupplier;
    }

    public void setDeliveredBySupplier(Integer deliveredBySupplier) {
        this.deliveredBySupplier = deliveredBySupplier;
    }

    public String getIncomeAccount() {
        return incomeAccount;
    }

    public void setIncomeAccount(String incomeAccount) {
        this.incomeAccount = incomeAccount;
    }

    public Integer getIsFixedAsset() {
        return isFixedAsset;
    }

    public void setIsFixedAsset(Integer isFixedAsset) {
        this.isFixedAsset = isFixedAsset;
    }

    public Object getAsset() {
        return asset;
    }

    public void setAsset(Object asset) {
        this.asset = asset;
    }

    public Object getFinanceBook() {
        return financeBook;
    }

    public void setFinanceBook(Object financeBook) {
        this.financeBook = financeBook;
    }

    public String getExpenseAccount() {
        return expenseAccount;
    }

    public void setExpenseAccount(String expenseAccount) {
        this.expenseAccount = expenseAccount;
    }

    public Object getDeferredRevenueAccount() {
        return deferredRevenueAccount;
    }

    public void setDeferredRevenueAccount(Object deferredRevenueAccount) {
        this.deferredRevenueAccount = deferredRevenueAccount;
    }

    public Object getServiceStopDate() {
        return serviceStopDate;
    }

    public void setServiceStopDate(Object serviceStopDate) {
        this.serviceStopDate = serviceStopDate;
    }

    public Integer getEnableDeferredRevenue() {
        return enableDeferredRevenue;
    }

    public void setEnableDeferredRevenue(Integer enableDeferredRevenue) {
        this.enableDeferredRevenue = enableDeferredRevenue;
    }

    public Object getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Object serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public Object getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(Object serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public Double getWeightPerUnit() {
        return weightPerUnit;
    }

    public void setWeightPerUnit(Double weightPerUnit) {
        this.weightPerUnit = weightPerUnit;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Object getWeightUom() {
        return weightUom;
    }

    public void setWeightUom(Object weightUom) {
        this.weightUom = weightUom;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Object getTargetWarehouse() {
        return targetWarehouse;
    }

    public void setTargetWarehouse(Object targetWarehouse) {
        this.targetWarehouse = targetWarehouse;
    }

    public Object getQualityInspection() {
        return qualityInspection;
    }

    public void setQualityInspection(Object qualityInspection) {
        this.qualityInspection = qualityInspection;
    }

    public Object getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Object batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getAllowZeroValuationRate() {
        return allowZeroValuationRate;
    }

    public void setAllowZeroValuationRate(Integer allowZeroValuationRate) {
        this.allowZeroValuationRate = allowZeroValuationRate;
    }

    public Object getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Object serialNo) {
        this.serialNo = serialNo;
    }

    public String getItemTaxRate() {
        return itemTaxRate;
    }

    public void setItemTaxRate(String itemTaxRate) {
        this.itemTaxRate = itemTaxRate;
    }

    public Double getActualBatchQty() {
        return actualBatchQty;
    }

    public void setActualBatchQty(Double actualBatchQty) {
        this.actualBatchQty = actualBatchQty;
    }

    public Double getActualQty() {
        return actualQty;
    }

    public void setActualQty(Double actualQty) {
        this.actualQty = actualQty;
    }

    public Object getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(Object salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Object getSoDetail() {
        return soDetail;
    }

    public void setSoDetail(Object soDetail) {
        this.soDetail = soDetail;
    }

    public Object getPosInvoiceItem() {
        return posInvoiceItem;
    }

    public void setPosInvoiceItem(Object posInvoiceItem) {
        this.posInvoiceItem = posInvoiceItem;
    }

    public Object getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(Object deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public Object getDnDetail() {
        return dnDetail;
    }

    public void setDnDetail(Object dnDetail) {
        this.dnDetail = dnDetail;
    }

    public Double getDeliveredQty() {
        return deliveredQty;
    }

    public void setDeliveredQty(Double deliveredQty) {
        this.deliveredQty = deliveredQty;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public Object getProject() {
        return project;
    }

    public void setProject(Object project) {
        this.project = project;
    }

    public Integer getPageBreak() {
        return pageBreak;
    }

    public void setPageBreak(Integer pageBreak) {
        this.pageBreak = pageBreak;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public Integer getUnsaved() {
        return unsaved;
    }

    public void setUnsaved(Integer unsaved) {
        this.unsaved = unsaved;
    }

}
