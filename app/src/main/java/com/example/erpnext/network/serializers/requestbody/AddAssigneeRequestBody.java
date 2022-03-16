package com.example.erpnext.network.serializers.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

public class AddAssigneeRequestBody {
    @SerializedName("assign_to_me")
    @Expose
    private Integer assign_to_me;
    @SerializedName("assign_to")
    @Expose
    private JSONArray jsonArray;
    @SerializedName("description")
    @Expose
    private String comment;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("bulk_assign")
    @Expose
    private boolean bulk_assign;
    @SerializedName("bulk_assign")
    @Expose
    private boolean re_assign;

    public AddAssigneeRequestBody(Integer assign_to_me, JSONArray jsonArray, String comment, String date, String priority, String doctype, boolean bulk_assign, boolean re_assign) {
        this.assign_to_me = assign_to_me;
        this.jsonArray = jsonArray;
        this.comment = comment;
        this.date = date;
        this.priority = priority;
        this.doctype = doctype;
        this.bulk_assign = bulk_assign;
        this.re_assign = re_assign;
    }

    public Integer getAssign_to_me() {
        return assign_to_me;
    }

    public void setAssign_to_me(Integer assign_to_me) {
        this.assign_to_me = assign_to_me;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isRe_assign() {
        return re_assign;
    }

    public void setRe_assign(boolean re_assign) {
        this.re_assign = re_assign;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public boolean isBulk_assign() {
        return bulk_assign;
    }

    public void setBulk_assign(boolean bulk_assign) {
        this.bulk_assign = bulk_assign;
    }
}
