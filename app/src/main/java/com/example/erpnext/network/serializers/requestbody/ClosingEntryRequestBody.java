package com.example.erpnext.network.serializers.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class ClosingEntryRequestBody {
    @SerializedName("doc")
    @Expose
    JSONObject doc;
    @SerializedName("action")
    @Expose
    String action;

    public ClosingEntryRequestBody() {
    }

    public ClosingEntryRequestBody(JSONObject doc, String action) {
        this.doc = doc;
        this.action = action;
    }

    public JSONObject getDoc() {
        return doc;
    }

    public void setDoc(JSONObject doc) {
        this.doc = doc;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
