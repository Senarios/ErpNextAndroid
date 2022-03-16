package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.StokeDoc;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStokeDoctypeResponse {

    @SerializedName("docs")
    @Expose
    private List<StokeDoc> docs = null;
    @SerializedName("user_settings")
    @Expose
    private String userSettings;
    @SerializedName("_error_message")
    @Expose
    private String errorMessage;

    public List<StokeDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<StokeDoc> docs) {
        this.docs = docs;
    }

    public String getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(String userSettings) {
        this.userSettings = userSettings;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
