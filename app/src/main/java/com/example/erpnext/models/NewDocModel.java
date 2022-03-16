package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewDocModel {
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
    @SerializedName("naming_series")
    @Expose
    private String namingSeries;
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
    @SerializedName("selling_price_list")
    @Expose
    private String sellingPriceList;
    @SerializedName("price_list_currency")
    @Expose
    private String priceListCurrency;
    @SerializedName("ignore_pricing_rule")
    @Expose
    private Integer ignorePricingRule;
    @SerializedName("update_stock")
    @Expose
    private Integer updateStock;
    @SerializedName("total_billing_amount")
    @Expose
    private Integer totalBillingAmount;
    @SerializedName("redeem_loyalty_points")
    @Expose
    private Integer redeemLoyaltyPoints;
    @SerializedName("apply_discount_on")
    @Expose
    private String applyDiscountOn;
    @SerializedName("allocate_advances_automatically")
    @Expose
    private Integer allocateAdvancesAutomatically;
    @SerializedName("write_off_outstanding_amount_automatically")
    @Expose
    private Integer writeOffOutstandingAmountAutomatically;
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
    @SerializedName("party_account_currency")
    @Expose
    private String partyAccountCurrency;
    @SerializedName("is_opening")
    @Expose
    private String isOpening;
    @SerializedName("c_form_applicable")
    @Expose
    private String cFormApplicable;
    @SerializedName("items")
    @Expose
    private List<Object> items = null;
    @SerializedName("__run_link_triggers")
    @Expose
    private Integer runLinkTriggers;
    @SerializedName("pos_profile")
    @Expose
    private String posProfile;

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

    public String getNamingSeries() {
        return namingSeries;
    }

    public void setNamingSeries(String namingSeries) {
        this.namingSeries = namingSeries;
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

    public Integer getIgnorePricingRule() {
        return ignorePricingRule;
    }

    public void setIgnorePricingRule(Integer ignorePricingRule) {
        this.ignorePricingRule = ignorePricingRule;
    }

    public Integer getUpdateStock() {
        return updateStock;
    }

    public void setUpdateStock(Integer updateStock) {
        this.updateStock = updateStock;
    }

    public Integer getTotalBillingAmount() {
        return totalBillingAmount;
    }

    public void setTotalBillingAmount(Integer totalBillingAmount) {
        this.totalBillingAmount = totalBillingAmount;
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

    public Integer getAllocateAdvancesAutomatically() {
        return allocateAdvancesAutomatically;
    }

    public void setAllocateAdvancesAutomatically(Integer allocateAdvancesAutomatically) {
        this.allocateAdvancesAutomatically = allocateAdvancesAutomatically;
    }

    public Integer getWriteOffOutstandingAmountAutomatically() {
        return writeOffOutstandingAmountAutomatically;
    }

    public void setWriteOffOutstandingAmountAutomatically(Integer writeOffOutstandingAmountAutomatically) {
        this.writeOffOutstandingAmountAutomatically = writeOffOutstandingAmountAutomatically;
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

    public String getPartyAccountCurrency() {
        return partyAccountCurrency;
    }

    public void setPartyAccountCurrency(String partyAccountCurrency) {
        this.partyAccountCurrency = partyAccountCurrency;
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

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public Integer getRunLinkTriggers() {
        return runLinkTriggers;
    }

    public void setRunLinkTriggers(Integer runLinkTriggers) {
        this.runLinkTriggers = runLinkTriggers;
    }

    public String getPosProfile() {
        return posProfile;
    }

    public void setPosProfile(String posProfile) {
        this.posProfile = posProfile;
    }

}
