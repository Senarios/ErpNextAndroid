package com.example.erpnext.network.serializers.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffListRequest {
    @SerializedName("id")
    @Expose
    private int projectId;
    @SerializedName("staff_ids")
    @Expose
    private List<String> staffIds;

    public StaffListRequest(int projectId, List<String> staffIds) {
        this.projectId = projectId;
        this.staffIds = staffIds;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public List<String> getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(List<String> staffIds) {
        this.staffIds = staffIds;
    }
}
