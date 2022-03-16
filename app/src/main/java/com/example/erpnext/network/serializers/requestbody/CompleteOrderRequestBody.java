package com.example.erpnext.network.serializers.requestbody;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.erpnext.models.CompleteOrderItem;
import com.example.erpnext.models.CompleteOrderPayment;
import com.example.erpnext.roomDB.TypeConverters.CompleteOrderItemListConverter;
import com.example.erpnext.roomDB.TypeConverters.CompleteOrderPaymentListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters({CompleteOrderPaymentListConverter.class, CompleteOrderItemListConverter.class})
public class CompleteOrderRequestBody {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("naming_series")
    @Expose
    private String namingSeries;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("posting_time")
    @Expose
    private String postingTime;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("outstanding_amount")
    @Expose
    private String outstandingAmount;
    @SerializedName("change_amount")
    @Expose
    private String changeAmount;
    @SerializedName("base_change_amount")
    @Expose
    private String baseChangeAmount;
    @SerializedName("base_total")
    @Expose
    private String baseTotal;
    @SerializedName("additional_discount_percentage")
    @Expose
    private float discountPercentage;
    @SerializedName("payments")
    @Expose
    private List<CompleteOrderPayment> payments = null;
    @SerializedName("items")
    @Expose
    private List<CompleteOrderItem> items = null;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;

    public CompleteOrderRequestBody() {
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(String changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getBaseChangeAmount() {
        return baseChangeAmount;
    }

    public void setBaseChangeAmount(String baseChangeAmount) {
        this.baseChangeAmount = baseChangeAmount;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(String outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getBaseTotal() {
        return baseTotal;
    }

    public void setBaseTotal(String baseTotal) {
        this.baseTotal = baseTotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamingSeries() {
        return namingSeries;
    }

    public void setNamingSeries(String namingSeries) {
        this.namingSeries = namingSeries;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public List<CompleteOrderPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<CompleteOrderPayment> payments) {
        this.payments = payments;
    }

    public List<CompleteOrderItem> getItems() {
        return items;
    }

    public void setItems(List<CompleteOrderItem> items) {
        this.items = items;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

}
