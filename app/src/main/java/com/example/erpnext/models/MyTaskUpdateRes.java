package com.example.erpnext.models;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyTaskUpdateRes {

    @SerializedName("info")
    @Expose
    private List<Info> info = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
