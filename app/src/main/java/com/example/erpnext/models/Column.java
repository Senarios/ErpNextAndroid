package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Column {
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("fieldname")
    @Expose
    private String fieldname;
    @SerializedName("fieldtype")
    @Expose
    private String fieldtype;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("width")
    @Expose
    private Integer width;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

}
