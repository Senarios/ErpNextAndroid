package com.example.erpnext.network.serializers.response;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.erpnext.models.ReportViewMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class ReportViewResponse {
    @PrimaryKey(autoGenerate = true)
    public int uids;
    @ColumnInfo(name = "doctype")
    public String doctype;
    @SerializedName("message")
    @Expose
    @Embedded
    private ReportViewMessage reportViewMessage;

    public ReportViewMessage getReportViewMessage() {
        return reportViewMessage;
    }

    public void setReportViewMessage(ReportViewMessage reportViewMessage) {
        this.reportViewMessage = reportViewMessage;
    }
}
