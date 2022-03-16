package com.example.erpnext.network.serializers.response;

import com.example.erpnext.models.AttachmentMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachmentResponse {
    @SerializedName("message")
    @Expose
    private AttachmentMessage attachmentMessage;

    public AttachmentMessage getAttachmentMessage() {
        return attachmentMessage;
    }

    public void setAttachmentMessage(AttachmentMessage attachmentMessage) {
        this.attachmentMessage = attachmentMessage;
    }
}
