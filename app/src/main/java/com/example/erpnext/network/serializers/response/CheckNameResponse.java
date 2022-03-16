package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.NameMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckNameResponse {

    @SerializedName("message")
    @Expose
    private NameMessage message;

    public NameMessage getMessage() {
        return message;
    }

    public void setMessage(NameMessage message) {
        this.message = message;
    }

}
