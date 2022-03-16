package com.example.erpnext.network.serializers.requestbody;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class InvoiceActionRequestBody {
    @SerializedName("doctype")
    @Expose
    String doctype;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("action")
    @Expose
    String action;

    public InvoiceActionRequestBody() {
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
