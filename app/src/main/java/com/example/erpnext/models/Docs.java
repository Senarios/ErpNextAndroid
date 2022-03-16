package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Docs {
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
    @SerializedName("pos_profile")
    @Expose
    private String posProfile;
    @SerializedName("is_pos")
    @Expose
    private Integer isPos;
    @SerializedName("is_return")
    @Expose
    private Integer isReturn;
    @SerializedName("update_billed_amount_in_sales_order")
    @Expose
    private Integer updateBilledAmountInSalesOrder;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("set_posting_time")
    @Expose
    private Integer setPostingTime;
    @SerializedName("territory")
    @Expose
    private String territory;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("conversion_rate")
    @Expose
    private Double conversionRate;
    @SerializedName("selling_price_list")
    @Expose
    private String sellingPriceList;
    @SerializedName("price_list_currency")
    @Expose
    private String priceListCurrency;
    @SerializedName("plc_conversion_rate")
    @Expose
    private Double plcConversionRate;
    @SerializedName("ignore_pricing_rule")
    @Expose
    private Integer ignorePricingRule;
    @SerializedName("set_warehouse")
    @Expose
    private String setWarehouse;
    @SerializedName("update_stock")
    @Expose
    private Integer updateStock;
    @SerializedName("total_billing_amount")
    @Expose
    private Double totalBillingAmount;
    @SerializedName("total_qty")
    @Expose
    private Double totalQty;
    @SerializedName("base_total")
    @Expose
    private Double baseTotal;
    @SerializedName("base_net_total")
    @Expose
    private Double baseNetTotal;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("net_total")
    @Expose
    private Double netTotal;
    @SerializedName("total_net_weight")
    @Expose
    private Double totalNetWeight;
    @SerializedName("base_total_taxes_and_charges")
    @Expose
    private Double baseTotalTaxesAndCharges;
    @SerializedName("total_taxes_and_charges")
    @Expose
    private Double totalTaxesAndCharges;
    @SerializedName("loyalty_points")
    @Expose
    private Integer loyaltyPoints;
    @SerializedName("loyalty_amount")
    @Expose
    private Double loyaltyAmount;
    @SerializedName("redeem_loyalty_points")
    @Expose
    private Integer redeemLoyaltyPoints;
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
    @SerializedName("total_advance")
    @Expose
    private Double totalAdvance;
    @SerializedName("outstanding_amount")
    @Expose
    private Double outstandingAmount;
    @SerializedName("allocate_advances_automatically")
    @Expose
    private Integer allocateAdvancesAutomatically;
    @SerializedName("base_paid_amount")
    @Expose
    private Double basePaidAmount;
    @SerializedName("paid_amount")
    @Expose
    private Double paidAmount;
    @SerializedName("base_change_amount")
    @Expose
    private Double baseChangeAmount;
    @SerializedName("change_amount")
    @Expose
    private Double changeAmount;
    @SerializedName("account_for_change_amount")
    @Expose
    private String accountForChangeAmount;
    @SerializedName("write_off_amount")
    @Expose
    private Double writeOffAmount;
    @SerializedName("base_write_off_amount")
    @Expose
    private Double baseWriteOffAmount;
    @SerializedName("write_off_outstanding_amount_automatically")
    @Expose
    private Integer writeOffOutstandingAmountAutomatically;
    @SerializedName("write_off_account")
    @Expose
    private String writeOffAccount;
    @SerializedName("write_off_cost_center")
    @Expose
    private String writeOffCostCenter;
    @SerializedName("letter_head")
    @Expose
    private String letterHead;
    @SerializedName("group_same_items")
    @Expose
    private Integer groupSameItems;
    @SerializedName("customer_group")
    @Expose
    private String customerGroup;
    @SerializedName("is_discounted")
    @Expose
    private Integer isDiscounted;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_opening")
    @Expose
    private String isOpening;
    @SerializedName("c_form_applicable")
    @Expose
    private String cFormApplicable;
    @SerializedName("commission_rate")
    @Expose
    private Double commissionRate;
    @SerializedName("total_commission")
    @Expose
    private Double totalCommission;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("items")
    @Expose
    private List<Object> items = null;
    @SerializedName("pricing_rules")
    @Expose
    private List<Object> pricingRules = null;
    @SerializedName("packed_items")
    @Expose
    private List<Object> packedItems = null;
    @SerializedName("timesheets")
    @Expose
    private List<Object> timesheets = null;
    @SerializedName("taxes")
    @Expose
    private List<Object> taxes = null;
    @SerializedName("advances")
    @Expose
    private List<Object> advances = null;
    @SerializedName("payment_schedule")
    @Expose
    private List<Object> paymentSchedule = null;
    @SerializedName("payments")
    @Expose
    private List<Payments> payments = null;
    @SerializedName("sales_team")
    @Expose
    private List<Object> salesTeam = null;
    @SerializedName("__islocal")
    @Expose
    private Integer islocal;
    @SerializedName("__run_link_triggers")
    @Expose
    private Integer runLinkTriggers;
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

    public String getPosProfile() {
        return posProfile;
    }

    public void setPosProfile(String posProfile) {
        this.posProfile = posProfile;
    }

    public Integer getIsPos() {
        return isPos;
    }

    public void setIsPos(Integer isPos) {
        this.isPos = isPos;
    }

    public Integer getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public Integer getUpdateBilledAmountInSalesOrder() {
        return updateBilledAmountInSalesOrder;
    }

    public void setUpdateBilledAmountInSalesOrder(Integer updateBilledAmountInSalesOrder) {
        this.updateBilledAmountInSalesOrder = updateBilledAmountInSalesOrder;
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

    public Integer getSetPostingTime() {
        return setPostingTime;
    }

    public void setSetPostingTime(Integer setPostingTime) {
        this.setPostingTime = setPostingTime;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
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

    public String getSellingPriceList() {
        return sellingPriceList;
    }

    public void setSellingPriceList(String sellingPriceList) {
        this.sellingPriceList = sellingPriceList;
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

    public String getSetWarehouse() {
        return setWarehouse;
    }

    public void setSetWarehouse(String setWarehouse) {
        this.setWarehouse = setWarehouse;
    }

    public Integer getUpdateStock() {
        return updateStock;
    }

    public void setUpdateStock(Integer updateStock) {
        this.updateStock = updateStock;
    }

    public Double getTotalBillingAmount() {
        return totalBillingAmount;
    }

    public void setTotalBillingAmount(Double totalBillingAmount) {
        this.totalBillingAmount = totalBillingAmount;
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

    public Double getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(Double totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public Double getBaseTotalTaxesAndCharges() {
        return baseTotalTaxesAndCharges;
    }

    public void setBaseTotalTaxesAndCharges(Double baseTotalTaxesAndCharges) {
        this.baseTotalTaxesAndCharges = baseTotalTaxesAndCharges;
    }

    public Double getTotalTaxesAndCharges() {
        return totalTaxesAndCharges;
    }

    public void setTotalTaxesAndCharges(Double totalTaxesAndCharges) {
        this.totalTaxesAndCharges = totalTaxesAndCharges;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Double getLoyaltyAmount() {
        return loyaltyAmount;
    }

    public void setLoyaltyAmount(Double loyaltyAmount) {
        this.loyaltyAmount = loyaltyAmount;
    }

    public Integer getRedeemLoyaltyPoints() {
        return redeemLoyaltyPoints;
    }

    public void setRedeemLoyaltyPoints(Integer redeemLoyaltyPoints) {
        this.redeemLoyaltyPoints = redeemLoyaltyPoints;
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

    public Double getTotalAdvance() {
        return totalAdvance;
    }

    public void setTotalAdvance(Double totalAdvance) {
        this.totalAdvance = totalAdvance;
    }

    public Double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(Double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Integer getAllocateAdvancesAutomatically() {
        return allocateAdvancesAutomatically;
    }

    public void setAllocateAdvancesAutomatically(Integer allocateAdvancesAutomatically) {
        this.allocateAdvancesAutomatically = allocateAdvancesAutomatically;
    }

    public Double getBasePaidAmount() {
        return basePaidAmount;
    }

    public void setBasePaidAmount(Double basePaidAmount) {
        this.basePaidAmount = basePaidAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getBaseChangeAmount() {
        return baseChangeAmount;
    }

    public void setBaseChangeAmount(Double baseChangeAmount) {
        this.baseChangeAmount = baseChangeAmount;
    }

    public Double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getAccountForChangeAmount() {
        return accountForChangeAmount;
    }

    public void setAccountForChangeAmount(String accountForChangeAmount) {
        this.accountForChangeAmount = accountForChangeAmount;
    }

    public Double getWriteOffAmount() {
        return writeOffAmount;
    }

    public void setWriteOffAmount(Double writeOffAmount) {
        this.writeOffAmount = writeOffAmount;
    }

    public Double getBaseWriteOffAmount() {
        return baseWriteOffAmount;
    }

    public void setBaseWriteOffAmount(Double baseWriteOffAmount) {
        this.baseWriteOffAmount = baseWriteOffAmount;
    }

    public Integer getWriteOffOutstandingAmountAutomatically() {
        return writeOffOutstandingAmountAutomatically;
    }

    public void setWriteOffOutstandingAmountAutomatically(Integer writeOffOutstandingAmountAutomatically) {
        this.writeOffOutstandingAmountAutomatically = writeOffOutstandingAmountAutomatically;
    }

    public String getWriteOffAccount() {
        return writeOffAccount;
    }

    public void setWriteOffAccount(String writeOffAccount) {
        this.writeOffAccount = writeOffAccount;
    }

    public String getWriteOffCostCenter() {
        return writeOffCostCenter;
    }

    public void setWriteOffCostCenter(String writeOffCostCenter) {
        this.writeOffCostCenter = writeOffCostCenter;
    }

    public String getLetterHead() {
        return letterHead;
    }

    public void setLetterHead(String letterHead) {
        this.letterHead = letterHead;
    }

    public Integer getGroupSameItems() {
        return groupSameItems;
    }

    public void setGroupSameItems(Integer groupSameItems) {
        this.groupSameItems = groupSameItems;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public Integer getIsDiscounted() {
        return isDiscounted;
    }

    public void setIsDiscounted(Integer isDiscounted) {
        this.isDiscounted = isDiscounted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsOpening() {
        return isOpening;
    }

    public void setIsOpening(String isOpening) {
        this.isOpening = isOpening;
    }

    public String getcFormApplicable() {
        return cFormApplicable;
    }

    public void setcFormApplicable(String cFormApplicable) {
        this.cFormApplicable = cFormApplicable;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public List<Object> getPricingRules() {
        return pricingRules;
    }

    public void setPricingRules(List<Object> pricingRules) {
        this.pricingRules = pricingRules;
    }

    public List<Object> getPackedItems() {
        return packedItems;
    }

    public void setPackedItems(List<Object> packedItems) {
        this.packedItems = packedItems;
    }

    public List<Object> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<Object> timesheets) {
        this.timesheets = timesheets;
    }

    public List<Object> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Object> taxes) {
        this.taxes = taxes;
    }

    public List<Object> getAdvances() {
        return advances;
    }

    public void setAdvances(List<Object> advances) {
        this.advances = advances;
    }

    public List<Object> getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(List<Object> paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public List<Payments> getPayments() {
        return payments;
    }

    public void setPayments(List<Payments> payments) {
        this.payments = payments;
    }

    public List<Object> getSalesTeam() {
        return salesTeam;
    }

    public void setSalesTeam(List<Object> salesTeam) {
        this.salesTeam = salesTeam;
    }

    public Integer getIslocal() {
        return islocal;
    }

    public void setIslocal(Integer islocal) {
        this.islocal = islocal;
    }

    public Integer getRunLinkTriggers() {
        return runLinkTriggers;
    }

    public void setRunLinkTriggers(Integer runLinkTriggers) {
        this.runLinkTriggers = runLinkTriggers;
    }

    public Integer getUnsaved() {
        return unsaved;
    }

    public void setUnsaved(Integer unsaved) {
        this.unsaved = unsaved;
    }
}
