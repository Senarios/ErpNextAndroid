package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.DocClosing;
import com.example.erpnext.models.DocInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveClosingEntryResponse {
    @SerializedName("docs")
    @Expose
    private List<DocClosing> docs = null;
    @SerializedName("docinfo")
    @Expose
    private DocInfo docinfo;
    @SerializedName("_server_messages")
    @Expose
    private String serverMessages;

    public List<DocClosing> getDocs() {
        return docs;
    }

    public void setDocs(List<DocClosing> docs) {
        this.docs = docs;
    }

    public DocInfo getDocinfo() {
        return docinfo;
    }

    public void setDocinfo(DocInfo docinfo) {
        this.docinfo = docinfo;
    }

    public String getServerMessages() {
        return serverMessages;
    }

    public void setServerMessages(String serverMessages) {
        this.serverMessages = serverMessages;
    }
}
