package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocLoyalty {
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
    private Object parent;
    @SerializedName("parentfield")
    @Expose
    private Object parentfield;
    @SerializedName("parenttype")
    @Expose
    private Object parenttype;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("loyalty_program_name")
    @Expose
    private String loyaltyProgramName;
    @SerializedName("loyalty_program_type")
    @Expose
    private String loyaltyProgramType;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("customer_group")
    @Expose
    private String customerGroup;
    @SerializedName("customer_territory")
    @Expose
    private String customerTerritory;
    @SerializedName("auto_opt_in")
    @Expose
    private Integer autoOptIn;
    @SerializedName("conversion_factor")
    @Expose
    private Double conversionFactor;
    @SerializedName("expiry_duration")
    @Expose
    private Integer expiryDuration;
    @SerializedName("expense_account")
    @Expose
    private String expenseAccount;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("cost_center")
    @Expose
    private String costCenter;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("collection_rules")
    @Expose
    private List<CollectionRule> collectionRules = null;
    @SerializedName("localname")
    @Expose
    private String localname;

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

    public String getLoyaltyProgramName() {
        return loyaltyProgramName;
    }

    public void setLoyaltyProgramName(String loyaltyProgramName) {
        this.loyaltyProgramName = loyaltyProgramName;
    }

    public String getLoyaltyProgramType() {
        return loyaltyProgramType;
    }

    public void setLoyaltyProgramType(String loyaltyProgramType) {
        this.loyaltyProgramType = loyaltyProgramType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public String getCustomerTerritory() {
        return customerTerritory;
    }

    public void setCustomerTerritory(String customerTerritory) {
        this.customerTerritory = customerTerritory;
    }

    public Integer getAutoOptIn() {
        return autoOptIn;
    }

    public void setAutoOptIn(Integer autoOptIn) {
        this.autoOptIn = autoOptIn;
    }

    public Double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(Double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public Integer getExpiryDuration() {
        return expiryDuration;
    }

    public void setExpiryDuration(Integer expiryDuration) {
        this.expiryDuration = expiryDuration;
    }

    public String getExpenseAccount() {
        return expenseAccount;
    }

    public void setExpenseAccount(String expenseAccount) {
        this.expenseAccount = expenseAccount;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public List<CollectionRule> getCollectionRules() {
        return collectionRules;
    }

    public void setCollectionRules(List<CollectionRule> collectionRules) {
        this.collectionRules = collectionRules;
    }

    public String getLocalname() {
        return localname;
    }

    public void setLocalname(String localname) {
        this.localname = localname;
    }
}
