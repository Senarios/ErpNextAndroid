package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PurchaseInvoice {
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
    @SerializedName("supplier")
    @Expose
    private String supplier;
    @SerializedName("supplier_name")
    @Expose
    private String supplierName;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("posting_time")
    @Expose
    private String postingTime;
    @SerializedName("set_posting_time")
    @Expose
    private Integer setPostingTime;
    @SerializedName("apply_putaway_rule")
    @Expose
    private Integer applyPutawayRule;
    @SerializedName("is_return")
    @Expose
    private Integer isReturn;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("conversion_rate")
    @Expose
    private Double conversionRate;
    @SerializedName("buying_price_list")
    @Expose
    private String buyingPriceList;
    @SerializedName("price_list_currency")
    @Expose
    private String priceListCurrency;
    @SerializedName("plc_conversion_rate")
    @Expose
    private Double plcConversionRate;
    @SerializedName("ignore_pricing_rule")
    @Expose
    private Integer ignorePricingRule;
    @SerializedName("is_subcontracted")
    @Expose
    private String isSubcontracted;
    @SerializedName("total_qty")
    @Expose
    private Double totalQty;
    @SerializedName("base_total")
    @Expose
    private Double baseTotal;
    @SerializedName("base_net_total")
    @Expose
    private Double baseNetTotal;
    @SerializedName("total_net_weight")
    @Expose
    private Double totalNetWeight;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("net_total")
    @Expose
    private Double netTotal;
    @SerializedName("base_taxes_and_charges_added")
    @Expose
    private Double baseTaxesAndChargesAdded;
    @SerializedName("base_taxes_and_charges_deducted")
    @Expose
    private Double baseTaxesAndChargesDeducted;
    @SerializedName("base_total_taxes_and_charges")
    @Expose
    private Double baseTotalTaxesAndCharges;
    @SerializedName("taxes_and_charges_added")
    @Expose
    private Double taxesAndChargesAdded;
    @SerializedName("taxes_and_charges_deducted")
    @Expose
    private Double taxesAndChargesDeducted;
    @SerializedName("total_taxes_and_charges")
    @Expose
    private Double totalTaxesAndCharges;
    @SerializedName("apply_discount_on")
    @Expose
    private String applyDiscountOn;
    @SerializedName("base_discount_amount")
    @Expose
    private Double baseDiscountAmount;
    @SerializedName("additional_discount_percentage")
    @Expose
    private Double additionalDiscountPercentage;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("base_grand_total")
    @Expose
    private Double baseGrandTotal;
    @SerializedName("base_rounding_adjustment")
    @Expose
    private Double baseRoundingAdjustment;
    @SerializedName("base_in_words")
    @Expose
    private String baseInWords;
    @SerializedName("base_rounded_total")
    @Expose
    private Double baseRoundedTotal;
    @SerializedName("grand_total")
    @Expose
    private Double grandTotal;
    @SerializedName("rounding_adjustment")
    @Expose
    private Double roundingAdjustment;
    @SerializedName("rounded_total")
    @Expose
    private Double roundedTotal;
    @SerializedName("in_words")
    @Expose
    private String inWords;
    @SerializedName("disable_rounded_total")
    @Expose
    private Integer disableRoundedTotal;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("per_billed")
    @Expose
    private Double perBilled;
    @SerializedName("per_returned")
    @Expose
    private Double perReturned;
    @SerializedName("is_internal_supplier")
    @Expose
    private Integer isInternalSupplier;
    @SerializedName("letter_head")
    @Expose
    private String letterHead;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("group_same_items")
    @Expose
    private Integer groupSameItems;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("items")
    @Expose
    private List<SearchItemDetail> items = null;
    @SerializedName("pricing_rules")
    @Expose
    private List<Object> pricingRules = null;
    @SerializedName("supplied_items")
    @Expose
    private List<Object> suppliedItems = null;
    @SerializedName("taxes")
    @Expose
    private List<Object> taxes = null;
    @SerializedName("__islocal")
    @Expose
    private Integer islocal;
    @SerializedName("__onload")
    @Expose
    private Object onload;
    @SerializedName("__unsaved")
    @Expose
    private Integer unsaved;

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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(String postingTime) {
        this.postingTime = postingTime;
    }

    public Integer getSetPostingTime() {
        return setPostingTime;
    }

    public void setSetPostingTime(Integer setPostingTime) {
        this.setPostingTime = setPostingTime;
    }

    public Integer getApplyPutawayRule() {
        return applyPutawayRule;
    }

    public void setApplyPutawayRule(Integer applyPutawayRule) {
        this.applyPutawayRule = applyPutawayRule;
    }

    public Integer getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public String getBuyingPriceList() {
        return buyingPriceList;
    }

    public void setBuyingPriceList(String buyingPriceList) {
        this.buyingPriceList = buyingPriceList;
    }

    public String getPriceListCurrency() {
        return priceListCurrency;
    }

    public void setPriceListCurrency(String priceListCurrency) {
        this.priceListCurrency = priceListCurrency;
    }

    public Double getPlcConversionRate() {
        return plcConversionRate;
    }

    public void setPlcConversionRate(Double plcConversionRate) {
        this.plcConversionRate = plcConversionRate;
    }

    public Integer getIgnorePricingRule() {
        return ignorePricingRule;
    }

    public void setIgnorePricingRule(Integer ignorePricingRule) {
        this.ignorePricingRule = ignorePricingRule;
    }

    public String getIsSubcontracted() {
        return isSubcontracted;
    }

    public void setIsSubcontracted(String isSubcontracted) {
        this.isSubcontracted = isSubcontracted;
    }

    public Double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Double totalQty) {
        this.totalQty = totalQty;
    }

    public Double getBaseTotal() {
        return baseTotal;
    }

    public void setBaseTotal(Double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public Double getBaseNetTotal() {
        return baseNetTotal;
    }

    public void setBaseNetTotal(Double baseNetTotal) {
        this.baseNetTotal = baseNetTotal;
    }

    public Double getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(Double totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(Double netTotal) {
        this.netTotal = netTotal;
    }

    public Double getBaseTaxesAndChargesAdded() {
        return baseTaxesAndChargesAdded;
    }

    public void setBaseTaxesAndChargesAdded(Double baseTaxesAndChargesAdded) {
        this.baseTaxesAndChargesAdded = baseTaxesAndChargesAdded;
    }

    public Double getBaseTaxesAndChargesDeducted() {
        return baseTaxesAndChargesDeducted;
    }

    public void setBaseTaxesAndChargesDeducted(Double baseTaxesAndChargesDeducted) {
        this.baseTaxesAndChargesDeducted = baseTaxesAndChargesDeducted;
    }

    public Double getBaseTotalTaxesAndCharges() {
        return baseTotalTaxesAndCharges;
    }

    public void setBaseTotalTaxesAndCharges(Double baseTotalTaxesAndCharges) {
        this.baseTotalTaxesAndCharges = baseTotalTaxesAndCharges;
    }

    public Double getTaxesAndChargesAdded() {
        return taxesAndChargesAdded;
    }

    public void setTaxesAndChargesAdded(Double taxesAndChargesAdded) {
        this.taxesAndChargesAdded = taxesAndChargesAdded;
    }

    public Double getTaxesAndChargesDeducted() {
        return taxesAndChargesDeducted;
    }

    public void setTaxesAndChargesDeducted(Double taxesAndChargesDeducted) {
        this.taxesAndChargesDeducted = taxesAndChargesDeducted;
    }

    public Double getTotalTaxesAndCharges() {
        return totalTaxesAndCharges;
    }

    public void setTotalTaxesAndCharges(Double totalTaxesAndCharges) {
        this.totalTaxesAndCharges = totalTaxesAndCharges;
    }

    public String getApplyDiscountOn() {
        return applyDiscountOn;
    }

    public void setApplyDiscountOn(String applyDiscountOn) {
        this.applyDiscountOn = applyDiscountOn;
    }

    public Double getBaseDiscountAmount() {
        return baseDiscountAmount;
    }

    public void setBaseDiscountAmount(Double baseDiscountAmount) {
        this.baseDiscountAmount = baseDiscountAmount;
    }

    public Double getAdditionalDiscountPercentage() {
        return additionalDiscountPercentage;
    }

    public void setAdditionalDiscountPercentage(Double additionalDiscountPercentage) {
        this.additionalDiscountPercentage = additionalDiscountPercentage;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getBaseGrandTotal() {
        return baseGrandTotal;
    }

    public void setBaseGrandTotal(Double baseGrandTotal) {
        this.baseGrandTotal = baseGrandTotal;
    }

    public Double getBaseRoundingAdjustment() {
        return baseRoundingAdjustment;
    }

    public void setBaseRoundingAdjustment(Double baseRoundingAdjustment) {
        this.baseRoundingAdjustment = baseRoundingAdjustment;
    }

    public String getBaseInWords() {
        return baseInWords;
    }

    public void setBaseInWords(String baseInWords) {
        this.baseInWords = baseInWords;
    }

    public Double getBaseRoundedTotal() {
        return baseRoundedTotal;
    }

    public void setBaseRoundedTotal(Double baseRoundedTotal) {
        this.baseRoundedTotal = baseRoundedTotal;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Double getRoundingAdjustment() {
        return roundingAdjustment;
    }

    public void setRoundingAdjustment(Double roundingAdjustment) {
        this.roundingAdjustment = roundingAdjustment;
    }

    public Double getRoundedTotal() {
        return roundedTotal;
    }

    public void setRoundedTotal(Double roundedTotal) {
        this.roundedTotal = roundedTotal;
    }

    public String getInWords() {
        return inWords;
    }

    public void setInWords(String inWords) {
        this.inWords = inWords;
    }

    public Integer getDisableRoundedTotal() {
        return disableRoundedTotal;
    }

    public void setDisableRoundedTotal(Integer disableRoundedTotal) {
        this.disableRoundedTotal = disableRoundedTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPerBilled() {
        return perBilled;
    }

    public void setPerBilled(Double perBilled) {
        this.perBilled = perBilled;
    }

    public Double getPerReturned() {
        return perReturned;
    }

    public void setPerReturned(Double perReturned) {
        this.perReturned = perReturned;
    }

    public Integer getIsInternalSupplier() {
        return isInternalSupplier;
    }

    public void setIsInternalSupplier(Integer isInternalSupplier) {
        this.isInternalSupplier = isInternalSupplier;
    }

    public String getLetterHead() {
        return letterHead;
    }

    public void setLetterHead(String letterHead) {
        this.letterHead = letterHead;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getGroupSameItems() {
        return groupSameItems;
    }

    public void setGroupSameItems(Integer groupSameItems) {
        this.groupSameItems = groupSameItems;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public List<SearchItemDetail> getItems() {
        return items;
    }

    public void setItems(List<SearchItemDetail> items) {
        this.items = items;
    }

    public Object getOnload() {
        return onload;
    }

    public void setOnload(Object onload) {
        this.onload = onload;
    }

    public List<Object> getPricingRules() {
        return pricingRules;
    }

    public void setPricingRules(List<Object> pricingRules) {
        this.pricingRules = pricingRules;
    }

    public List<Object> getSuppliedItems() {
        return suppliedItems;
    }

    public void setSuppliedItems(List<Object> suppliedItems) {
        this.suppliedItems = suppliedItems;
    }

    public List<Object> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Object> taxes) {
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
