package com.example.erpnext.utils;

public class MessageEvent {

    public String mMessage;
    public String type;

    public MessageEvent(String mMessage, String type) {
        this.mMessage = mMessage;
        this.type = type;
    }

    public MessageEvent() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return mMessage;
    }
}