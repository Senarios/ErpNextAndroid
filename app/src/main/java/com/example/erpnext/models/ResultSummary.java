package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultSummary {

    @SerializedName("Delivery Note")
    @Expose
    private String deliveryNote;
    @SerializedName("Sales Order")
    @Expose
    private String salesOrder;
    @SerializedName("Sales Invoice")
    @Expose
    private String salesInvoice;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("territory")
    @Expose
    private String territory;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("sales_person")
    @Expose
    private String salesPerson;
    @SerializedName("contribution_percentage")
    @Expose
    private String contributionPercentage;
    @SerializedName("commission_rate")
    @Expose
    private String commissionRate;
    @SerializedName("contribution_amount")
    @Expose
    private String contributionAmount;
    @SerializedName("incentives")
    @Expose
    private String incentives;

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getSalesInvoice() {
        return salesInvoice;
    }

    public void setSalesInvoice(String salesInvoice) {
        this.salesInvoice = salesInvoice;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getContributionPercentage() {
        return contributionPercentage;
    }

    public void setContributionPercentage(String contributionPercentage) {
        this.contributionPercentage = contributionPercentage;
    }

    public String getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(String contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    public String getIncentives() {
        return incentives;
    }

    public void setIncentives(String incentives) {
        this.incentives = incentives;
    }
}
