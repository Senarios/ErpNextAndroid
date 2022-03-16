package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.Assignment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RemoveAssigneeResponse {
    @SerializedName("message")
    @Expose
    private List<Assignment> assignmentList = null;

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }
}
