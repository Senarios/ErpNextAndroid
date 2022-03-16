package com.example.erpnext.models;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.erpnext.roomDB.TypeConverters.ObjectsListConverter;
import com.example.erpnext.roomDB.TypeConverters.PaymentsListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters({ObjectsListConverter.class, PaymentsListConverter.class})
public class SaveDoc {
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
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("disabled")
    @Expose
    private Integer disabled;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("campaign")
    @Expose
    private String campaign;
    @SerializedName("company_address")
    @Expose
    private String companyAddress;
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
    @SerializedName("print_format")
    @Expose
    private String printFormat;
    @SerializedName("letter_head")
    @Expose
    private String letterHead;
    @SerializedName("tc_name")
    @Expose
    private String tcName;
    @SerializedName("select_print_heading")
    @Expose
    private String selectPrintHeading;
    @SerializedName("selling_price_list")
    @Expose
    private String sellingPriceList;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("write_off_account")
    @Expose
    private String writeOffAccount;
    @SerializedName("write_off_cost_center")
    @Expose
    private String writeOffCostCenter;
    @SerializedName("account_for_change_amount")
    @Expose
    private String accountForChangeAmount;
    @SerializedName("income_account")
    @Expose
    private String incomeAccount;
    @SerializedName("expense_account")
    @Expose
    private String expenseAccount;
    @SerializedName("taxes_and_charges")
    @Expose
    private String taxesAndCharges;
    @SerializedName("tax_category")
    @Expose
    private String taxCategory;
    @SerializedName("apply_discount_on")
    @Expose
    private String applyDiscountOn;
    @SerializedName("cost_center")
    @Expose
    private String costCenter;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("applicable_for_users")
    @Expose
    private List<Object> applicableForUsers = null;
    @SerializedName("payments")
    @Expose
    private List<Payments> payments = null;
    @SerializedName("item_groups")
    @Expose
    private List<Object> itemGroups = null;
    @SerializedName("customer_groups")
    @Expose
    private List<Object> customerGroups = null;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
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

    public String getPrintFormat() {
        return printFormat;
    }

    public void setPrintFormat(String printFormat) {
        this.printFormat = printFormat;
    }

    public String getLetterHead() {
        return letterHead;
    }

    public void setLetterHead(String letterHead) {
        this.letterHead = letterHead;
    }

    public String getTcName() {
        return tcName;
    }

    public void setTcName(String tcName) {
        this.tcName = tcName;
    }

    public String getSelectPrintHeading() {
        return selectPrintHeading;
    }

    public void setSelectPrintHeading(String selectPrintHeading) {
        this.selectPrintHeading = selectPrintHeading;
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

    public String getAccountForChangeAmount() {
        return accountForChangeAmount;
    }

    public void setAccountForChangeAmount(String accountForChangeAmount) {
        this.accountForChangeAmount = accountForChangeAmount;
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

    public String getTaxesAndCharges() {
        return taxesAndCharges;
    }

    public void setTaxesAndCharges(String taxesAndCharges) {
        this.taxesAndCharges = taxesAndCharges;
    }

    public String getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    public String getApplyDiscountOn() {
        return applyDiscountOn;
    }

    public void setApplyDiscountOn(String applyDiscountOn) {
        this.applyDiscountOn = applyDiscountOn;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public List<Object> getApplicableForUsers() {
        return applicableForUsers;
    }

    public void setApplicableForUsers(List<Object> applicableForUsers) {
        this.applicableForUsers = applicableForUsers;
    }

    public List<Payments> getPayments() {
        return payments;
    }

    public void setPayments(List<Payments> payments) {
        this.payments = payments;
    }

    public List<Object> getItemGroups() {
        return itemGroups;
    }

    public void setItemGroups(List<Object> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public List<Object> getCustomerGroups() {
        return customerGroups;
    }

    public void setCustomerGroups(List<Object> customerGroups) {
        this.customerGroups = customerGroups;
    }
}
