package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.Assignment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddAssigneesResponse {
    @SerializedName("message")
    @Expose
    private List<Assignment> assigneeMessages = null;
    @SerializedName("_server_messages")
    @Expose
    private String serverMessage;

    public List<Assignment> getAssigneeMessages() {
        return assigneeMessages;
    }

    public void setAssigneeMessages(List<Assignment> assigneeMessages) {
        this.assigneeMessages = assigneeMessages;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }
}
