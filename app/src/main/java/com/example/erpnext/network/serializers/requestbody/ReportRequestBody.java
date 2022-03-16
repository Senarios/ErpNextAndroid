package com.example.erpnext.network.serializers.requestbody;

import com.example.erpnext.models.StockBalanceFilter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportRequestBody {
    @SerializedName("report_name")
    @Expose
    String report_name;
    @SerializedName("filters")
    @Expose
    StockBalanceFilter filters;

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public StockBalanceFilter getFilters() {
        return filters;
    }

    public void setFilters(StockBalanceFilter filters) {
        this.filters = filters;
    }
}
