package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.Value;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetValuesResponse {
    @SerializedName("message")
    @Expose
    private Value value;

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
