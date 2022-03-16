package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.CommentMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCommentResponse {

    @SerializedName("message")
    @Expose
    private CommentMessage commentMessage;

    public CommentMessage getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(CommentMessage commentMessage) {
        this.commentMessage = commentMessage;
    }
}