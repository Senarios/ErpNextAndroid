package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.DocInfo;
import com.example.erpnext.models.DocOPE;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveOpeningEntryResponse {
    @SerializedName("docs")
    @Expose
    private List<DocOPE> docs = null;
    @SerializedName("docinfo")
    @Expose
    private DocInfo docinfo;
    @SerializedName("_server_messages")
    @Expose
    private String serverMessages;

    public List<DocOPE> getDocs() {
        return docs;
    }

    public void setDocs(List<DocOPE> docs) {
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
