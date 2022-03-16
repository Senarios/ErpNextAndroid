package com.example.erpnext.network.serializers.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DepartmentResponse {
    @SerializedName("fetch_values")
    @Expose
    private List<String> fetchValues = null;
    @SerializedName("valid_value")
    @Expose
    private String validValue;
    @SerializedName("message")
    @Expose
    private String message;

    public List<String> getFetchValues() {
        return fetchValues;
    }

    public void setFetchValues(List<String> fetchValues) {
        this.fetchValues = fetchValues;
    }

    public String getValidValue() {
        return validValue;
    }

    public void setValidValue(String validValue) {
        this.validValue = validValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
