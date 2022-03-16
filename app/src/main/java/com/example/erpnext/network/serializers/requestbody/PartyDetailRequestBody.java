package com.example.erpnext.network.serializers.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartyDetailRequestBody {

    @SerializedName("party")
    @Expose
    private String party;

    @SerializedName("party_type")
    @Expose
    private String partyType;

    @SerializedName("price_list")
    @Expose
    private String priceList;

    @SerializedName("posting_date")
    @Expose
    private String postingDate;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("doctype")
    @Expose
    private String docType;

    public PartyDetailRequestBody(String party, String partyType, String priceList, String postingDate, String currency, String company, String docType) {
        this.party = party;
        this.partyType = partyType;
        this.priceList = priceList;
        this.postingDate = postingDate;
        this.currency = currency;
        this.company = company;
        this.docType = docType;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getPriceList() {
        return priceList;
    }

    public void setPriceList(String priceList) {
        this.priceList = priceList;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

}
