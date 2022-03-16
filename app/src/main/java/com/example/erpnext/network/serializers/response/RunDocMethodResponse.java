package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.Docs;
import com.example.erpnext.models.RunDocMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RunDocMethodResponse {
    @SerializedName("docs")
    @Expose
    private List<Docs> docsList = null;
    @SerializedName("message")
    @Expose
    private RunDocMessage runDocMessage;

    public List<Docs> getDocsList() {
        return docsList;
    }

    public void setDocsList(List<Docs> docsList) {
        this.docsList = docsList;
    }

    public RunDocMessage getRunDocMessage() {
        return runDocMessage;
    }

    public void setRunDocMessage(RunDocMessage runDocMessage) {
        this.runDocMessage = runDocMessage;
    }
}
