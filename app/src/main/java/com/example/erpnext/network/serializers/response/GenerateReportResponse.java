package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.GenerateReport;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateReportResponse {
    @SerializedName("message")
    @Expose
    private GenerateReport report;

    public GenerateReport getReport() {
        return report;
    }

    public void setReport(GenerateReport report) {
        this.report = report;
    }
}
