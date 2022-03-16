package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PartyDetail {

    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("customer_address")
    @Expose
    private Object customerAddress;
    @SerializedName("address_display")
    @Expose
    private Object addressDisplay;
    @SerializedName("shipping_address_name")
    @Expose
    private String shippingAddressName;
    @SerializedName("shipping_address")
    @Expose
    private String shippingAddress;
    @SerializedName("company_address")
    @Expose
    private Object companyAddress;
    @SerializedName("company_address_display")
    @Expose
    private String companyAddressDisplay;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("contact_display")
    @Expose
    private String contactDisplay;
    @SerializedName("contact_email")
    @Expose
    private String contactEmail;
    @SerializedName("contact_mobile")
    @Expose
    private String contactMobile;
    @SerializedName("contact_phone")
    @Expose
    private String contactPhone;
    @SerializedName("contact_designation")
    @Expose
    private Object contactDesignation;
    @SerializedName("contact_department")
    @Expose
    private Object contactDepartment;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_group")
    @Expose
    private String customerGroup;
    @SerializedName("territory")
    @Expose
    private String territory;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("price_list_currency")
    @Expose
    private String priceListCurrency;
    @SerializedName("selling_price_list")
    @Expose
    private String sellingPriceList;
    @SerializedName("tax_category")
    @Expose
    private String taxCategory;
    @SerializedName("taxes_and_charges")
    @Expose
    private Object taxesAndCharges;
    @SerializedName("payment_terms_template")
    @Expose
    private Object paymentTermsTemplate;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("sales_team")
    @Expose
    private List<SalesTeam> salesTeam = null;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Object getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Object customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Object getAddressDisplay() {
        return addressDisplay;
    }

    public void setAddressDisplay(Object addressDisplay) {
        this.addressDisplay = addressDisplay;
    }

    public String getShippingAddressName() {
        return shippingAddressName;
    }

    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Object getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(Object companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyAddressDisplay() {
        return companyAddressDisplay;
    }

    public void setCompanyAddressDisplay(String companyAddressDisplay) {
        this.companyAddressDisplay = companyAddressDisplay;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactDisplay() {
        return contactDisplay;
    }

    public void setContactDisplay(String contactDisplay) {
        this.contactDisplay = contactDisplay;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Object getContactDesignation() {
        return contactDesignation;
    }

    public void setContactDesignation(Object contactDesignation) {
        this.contactDesignation = contactDesignation;
    }

    public Object getContactDepartment() {
        return contactDepartment;
    }

    public void setContactDepartment(Object contactDepartment) {
        this.contactDepartment = contactDepartment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPriceListCurrency() {
        return priceListCurrency;
    }

    public void setPriceListCurrency(String priceListCurrency) {
        this.priceListCurrency = priceListCurrency;
    }

    public String getSellingPriceList() {
        return sellingPriceList;
    }

    public void setSellingPriceList(String sellingPriceList) {
        this.sellingPriceList = sellingPriceList;
    }

    public String getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    public Object getTaxesAndCharges() {
        return taxesAndCharges;
    }

    public void setTaxesAndCharges(Object taxesAndCharges) {
        this.taxesAndCharges = taxesAndCharges;
    }

    public Object getPaymentTermsTemplate() {
        return paymentTermsTemplate;
    }

    public void setPaymentTermsTemplate(Object paymentTermsTemplate) {
        this.paymentTermsTemplate = paymentTermsTemplate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<SalesTeam> getSalesTeam() {
        return salesTeam;
    }

    public void setSalesTeam(List<SalesTeam> salesTeam) {
        this.salesTeam = salesTeam;
    }

}
