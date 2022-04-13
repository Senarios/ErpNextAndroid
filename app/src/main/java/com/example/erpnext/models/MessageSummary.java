package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageSummary {
    @SerializedName("result")
    @Expose
    private Object[] result = null;
    @SerializedName("columns")
    @Expose
    private List<Column> columns = null;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("chart")
    @Expose
    private Object chart;
    @SerializedName("report_summary")
    @Expose
    private Object reportSummary;
    @SerializedName("skip_total_row")
    @Expose
    private Integer skipTotalRow;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("execution_time")
    @Expose
    private Double executionTime;
    @SerializedName("add_total_row")
    @Expose
    private Boolean addTotalRow;

    public Object[] getResult() {
        return result;
    }

    public void setResult(Object[] result) {
        this.result = result;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getChart() {
        return chart;
    }

    public void setChart(Object chart) {
        this.chart = chart;
    }

    public Object getReportSummary() {
        return reportSummary;
    }

    public void setReportSummary(Object reportSummary) {
        this.reportSummary = reportSummary;
    }

    public Integer getSkipTotalRow() {
        return skipTotalRow;
    }

    public void setSkipTotalRow(Integer skipTotalRow) {
        this.skipTotalRow = skipTotalRow;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Double executionTime) {
        this.executionTime = executionTime;
    }

    public Boolean getAddTotalRow() {
        return addTotalRow;
    }

    public void setAddTotalRow(Boolean addTotalRow) {
        this.addTotalRow = addTotalRow;
    }
}
