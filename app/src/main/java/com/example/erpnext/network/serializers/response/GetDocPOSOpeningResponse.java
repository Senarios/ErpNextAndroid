package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.DocOPE;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDocPOSOpeningResponse {
    @SerializedName("message")
    @Expose
    private DocOPE docOPE;

    public DocOPE getDocOPE() {
        return docOPE;
    }

    public void setDocOPE(DocOPE docOPE) {
        this.docOPE = docOPE;
    }
}
