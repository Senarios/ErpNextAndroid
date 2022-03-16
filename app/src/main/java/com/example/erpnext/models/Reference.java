package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reference {
    @SerializedName("linkDocType")
    @Expose
    private String linkDocType;
    @SerializedName("link_name")
    @Expose
    private String linkName;
    @SerializedName("link_title")
    @Expose
    private String linkTitle;

    public Reference() {
    }

    public Reference(String linkDocType, String linkName, String linkTitle) {
        this.linkDocType = linkDocType;
        this.linkName = linkName;
        this.linkTitle = linkTitle;
    }

    public String getLinkDocType() {
        return linkDocType;
    }

    public void setLinkDocType(String linkDocType) {
        this.linkDocType = linkDocType;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }
}
