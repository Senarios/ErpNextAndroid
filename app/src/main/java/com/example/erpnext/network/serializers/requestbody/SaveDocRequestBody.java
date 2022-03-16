package com.example.erpnext.network.serializers.requestbody;

import com.example.erpnext.models.Payment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveDocRequestBody {
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
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("disabled")
    @Expose
    private Integer disabled;
    @SerializedName("hide_images")
    @Expose
    private Integer hideImages;
    @SerializedName("hide_unavailable_items")
    @Expose
    private Integer hideUnavailableItems;
    @SerializedName("auto_add_item_to_cart")
    @Expose
    private Integer autoAddItemToCart;
    @SerializedName("update_stock")
    @Expose
    private Integer updateStock;
    @SerializedName("ignore_pricing_rule")
    @Expose
    private Integer ignorePricingRule;
    @SerializedName("allow_rate_change")
    @Expose
    private Integer allowRateChange;
    @SerializedName("allow_discount_change")
    @Expose
    private Integer allowDiscountChange;
    @SerializedName("letter_head")
    @Expose
    private String letterHead;
    @SerializedName("selling_price_list")
    @Expose
    private String sellingPriceList;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("apply_discount_on")
    @Expose
    private String applyDiscountOn;
    @SerializedName("payments")
    @Expose
    private List<Payment> payments = null;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("__newname")
    @Expose
    private String newname;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("write_off_account")
    @Expose
    private String writeOffAccount;
    @SerializedName("write_off_cost_center")
    @Expose
    private String writeOffCostCenter;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getHideImages() {
        return hideImages;
    }

    public void setHideImages(Integer hideImages) {
        this.hideImages = hideImages;
    }

    public Integer getHideUnavailableItems() {
        return hideUnavailableItems;
    }

    public void setHideUnavailableItems(Integer hideUnavailableItems) {
        this.hideUnavailableItems = hideUnavailableItems;
    }

    public Integer getAutoAddItemToCart() {
        return autoAddItemToCart;
    }

    public void setAutoAddItemToCart(Integer autoAddItemToCart) {
        this.autoAddItemToCart = autoAddItemToCart;
    }

    public Integer getUpdateStock() {
        return updateStock;
    }

    public void setUpdateStock(Integer updateStock) {
        this.updateStock = updateStock;
    }

    public Integer getIgnorePricingRule() {
        return ignorePricingRule;
    }

    public void setIgnorePricingRule(Integer ignorePricingRule) {
        this.ignorePricingRule = ignorePricingRule;
    }

    public Integer getAllowRateChange() {
        return allowRateChange;
    }

    public void setAllowRateChange(Integer allowRateChange) {
        this.allowRateChange = allowRateChange;
    }

    public Integer getAllowDiscountChange() {
        return allowDiscountChange;
    }

    public void setAllowDiscountChange(Integer allowDiscountChange) {
        this.allowDiscountChange = allowDiscountChange;
    }

    public String getLetterHead() {
        return letterHead;
    }

    public void setLetterHead(String letterHead) {
        this.letterHead = letterHead;
    }

    public String getSellingPriceList() {
        return sellingPriceList;
    }

    public void setSellingPriceList(String sellingPriceList) {
        this.sellingPriceList = sellingPriceList;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getApplyDiscountOn() {
        return applyDiscountOn;
    }

    public void setApplyDiscountOn(String applyDiscountOn) {
        this.applyDiscountOn = applyDiscountOn;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNewname() {
        return newname;
    }

    public void setNewname(String newname) {
        this.newname = newname;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
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
}
