package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.DocInfo;
import com.example.erpnext.models.SaveDoc;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveDocResponse {
    @SerializedName("docs")
    @Expose
    private List<SaveDoc> docs = null;
    @SerializedName("docinfo")
    @Expose
    private DocInfo docinfo;
    @SerializedName("_server_messages")
    @Expose
    private String serverMessages;

    public List<SaveDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<SaveDoc> docs) {
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
