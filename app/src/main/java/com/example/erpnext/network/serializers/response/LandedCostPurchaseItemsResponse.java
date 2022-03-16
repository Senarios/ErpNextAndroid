package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.LandedCostDoc;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LandedCostPurchaseItemsResponse {
    @SerializedName("docs")
    @Expose
    private List<LandedCostDoc> docs = null;

    public List<LandedCostDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<LandedCostDoc> docs) {
        this.docs = docs;
    }
}
