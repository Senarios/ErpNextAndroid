package com.example.erpnext.network.serializers.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPurchaseInvoiceResponse {
    @SerializedName("values")
    @Expose
    private List<List<String>> values = null;

    public List<List<String>> getValues() {
        return values;
    }

    public void setValues(List<List<String>> values) {
        this.values = values;
    }

}
