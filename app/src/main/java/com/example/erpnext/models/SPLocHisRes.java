package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPLocHisRes {

    @SerializedName("info")
    @Expose
    private List<SPLocHisDatum> info = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<SPLocHisDatum> getInfo() {
        return info;
    }

    public void setInfo(List<SPLocHisDatum> info) {
        this.info = info;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
