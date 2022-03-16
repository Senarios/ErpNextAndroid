package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.SearchItemDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchItemDetailResponse {
    @SerializedName("message")
    @Expose
    private SearchItemDetail itemDetail;

    public SearchItemDetail getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(SearchItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }
}
