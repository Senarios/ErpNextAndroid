package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.DocLoyalty;
import com.example.erpnext.models.LoyaltyDocInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveLoyaltyProgramResponse extends BaseResponse {
    @SerializedName("docs")
    @Expose
    private List<DocLoyalty> docs = null;
    @SerializedName("docinfo")
    @Expose
    private LoyaltyDocInfo docinfo;


    public List<DocLoyalty> getDocs() {
        return docs;
    }

    public void setDocs(List<DocLoyalty> docs) {
        this.docs = docs;
    }

    public LoyaltyDocInfo getDocinfo() {
        return docinfo;
    }

    public void setDocinfo(LoyaltyDocInfo docinfo) {
        this.docinfo = docinfo;
    }
}
