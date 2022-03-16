package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.erpnext.roomDB.TypeConverters.AplicableForUsersListConverter;
import com.example.erpnext.roomDB.TypeConverters.ObjectsListConverter;
import com.example.erpnext.roomDB.TypeConverters.PaymentsListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters({AplicableForUsersListConverter.class, PaymentsListConverter.class, ObjectsListConverter.class})
public class ProfileDoc {
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
    @Embedded
    private Object parent;
    @SerializedName("parentfield")
    @Expose
    @Embedded
    private Object parentfield;
    @SerializedName("parenttype")
    @Expose
    @Embedded
    private Object parenttype;
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
    @Embedded
    private Object customer;
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
    @Embedded
    private Object campaign;
    @SerializedName("company_address")
    @Expose
    @Embedded
    private Object companyAddress;
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
    @Embedded
    private Object printFormat;
    @SerializedName("letter_head")
    @Expose
    private String letterHead;
    @SerializedName("tc_name")
    @Expose
    @Embedded
    private Object tcName;
    @SerializedName("select_print_heading")
    @Expose
    @Embedded
    private Object selectPrintHeading;
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
    @Embedded
    private Object accountForChangeAmount;
    @SerializedName("income_account")
    @Expose
    @Embedded
    private Object incomeAccount;
    @SerializedName("expense_account")
    @Expose
    @Embedded
    private Object expenseAccount;
    @SerializedName("taxes_and_charges")
    @Expose
    @Embedded
    private Object taxesAndCharges;
    @SerializedName("tax_category")
    @Expose
    @Embedded
    private Object taxCategory;
    @SerializedName("apply_discount_on")
    @Expose
    private String applyDiscountOn;
    @SerializedName("cost_center")
    @Expose
    @Embedded
    private Object costCenter;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("applicable_for_users")
    @Expose
    private List<ApplicableForUser> applicableForUsers = null;
    @SerializedName("payments")
    @Expose
    private List<Payments> payments = null;
    @SerializedName("item_groups")
    @Expose
    private List<Object> itemGroups = null;
    @SerializedName("customer_groups")
    @Expose

    private List<Object> customerGroups = null;

    public ProfileDoc() {
    }


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

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Object getParentfield() {
        return parentfield;
    }

    public void setParentfield(Object parentfield) {
        this.parentfield = parentfield;
    }

    public Object getParenttype() {
        return parenttype;
    }

    public void setParenttype(Object parenttype) {
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Object getCustomer() {
        return customer;
    }

    public void setCustomer(Object customer) {
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

    public Object getCampaign() {
        return campaign;
    }

    public void setCampaign(Object campaign) {
        this.campaign = campaign;
    }

    public Object getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(Object companyAddress) {
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

    public Object getPrintFormat() {
        return printFormat;
    }

    public void setPrintFormat(Object printFormat) {
        this.printFormat = printFormat;
    }

    public String getLetterHead() {
        return letterHead;
    }

    public void setLetterHead(String letterHead) {
        this.letterHead = letterHead;
    }

    public Object getTcName() {
        return tcName;
    }

    public void setTcName(Object tcName) {
        this.tcName = tcName;
    }

    public Object getSelectPrintHeading() {
        return selectPrintHeading;
    }

    public void setSelectPrintHeading(Object selectPrintHeading) {
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

    public Object getAccountForChangeAmount() {
        return accountForChangeAmount;
    }

    public void setAccountForChangeAmount(Object accountForChangeAmount) {
        this.accountForChangeAmount = accountForChangeAmount;
    }

    public Object getIncomeAccount() {
        return incomeAccount;
    }

    public void setIncomeAccount(Object incomeAccount) {
        this.incomeAccount = incomeAccount;
    }

    public Object getExpenseAccount() {
        return expenseAccount;
    }

    public void setExpenseAccount(Object expenseAccount) {
        this.expenseAccount = expenseAccount;
    }

    public Object getTaxesAndCharges() {
        return taxesAndCharges;
    }

    public void setTaxesAndCharges(Object taxesAndCharges) {
        this.taxesAndCharges = taxesAndCharges;
    }

    public Object getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(Object taxCategory) {
        this.taxCategory = taxCategory;
    }

    public String getApplyDiscountOn() {
        return applyDiscountOn;
    }

    public void setApplyDiscountOn(String applyDiscountOn) {
        this.applyDiscountOn = applyDiscountOn;
    }

    public Object getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(Object costCenter) {
        this.costCenter = costCenter;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public List<ApplicableForUser> getApplicableForUsers() {
        return applicableForUsers;
    }

    public void setApplicableForUsers(List<ApplicableForUser> applicableForUsers) {
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
