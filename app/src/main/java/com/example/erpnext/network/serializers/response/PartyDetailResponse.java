package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.PartyDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartyDetailResponse {

    @SerializedName("message")
    @Expose
    private PartyDetail partyDetail;

    public PartyDetail getMessage() {
        return partyDetail;
    }

    public void setMessage(PartyDetail partyDetail) {
        this.partyDetail = partyDetail;
    }
}
