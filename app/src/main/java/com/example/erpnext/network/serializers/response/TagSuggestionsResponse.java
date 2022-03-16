package com.example.erpnext.network.serializers.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TagSuggestionsResponse {
    @SerializedName("message")
    @Expose
    private List<String> tags = null;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
