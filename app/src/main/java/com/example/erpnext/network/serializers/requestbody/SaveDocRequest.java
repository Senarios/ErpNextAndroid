package com.example.erpnext.network.serializers.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class SaveDocRequest {

    @SerializedName("doc")
    @Expose
    private JSONObject doc;
    @SerializedName("action")
    @Expose
    private String action;

    public SaveDocRequest(JSONObject doc, String action) {
        this.doc = doc;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public JSONObject getDoc() {

        return doc;
    }

    public void setDoc(JSONObject doc) {
        this.doc = doc;
    }
}
