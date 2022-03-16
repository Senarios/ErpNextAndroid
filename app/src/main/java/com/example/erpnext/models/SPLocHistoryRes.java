package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPLocHistoryRes {
    @SerializedName("data")
    @Expose
    private List<SPLocHistoryDatum> data = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<SPLocHistoryDatum> getData() {
        return data;
    }

    public void setData(List<SPLocHistoryDatum> data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
