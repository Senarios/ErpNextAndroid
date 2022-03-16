package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportMessage {
    @SerializedName("columns")
    @Expose
    private List<Column> columns = null;
    @SerializedName("result")
    @Expose
    private Object[] result = null;
    @SerializedName("prepared_report")
    @Expose
    private Boolean preparedReport;
    @SerializedName("doc")
    @Expose
    private Doc doc;
//    @SerializedName("add_total_row")
//    @Expose
//    private Boolean addTotalRow;

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }


    public Object[] getResult() {
        return result;
    }

    public void setResult(Object[] result) {
        this.result = result;
    }

    public Boolean getPreparedReport() {
        return preparedReport;
    }

    public void setPreparedReport(Boolean preparedReport) {
        this.preparedReport = preparedReport;
    }

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

//    public Boolean getAddTotalRow() {
//        return addTotalRow;
//    }
//
//    public void setAddTotalRow(Boolean addTotalRow) {
//        this.addTotalRow = addTotalRow;
//    }
}
