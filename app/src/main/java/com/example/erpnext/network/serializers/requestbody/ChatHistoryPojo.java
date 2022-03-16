package com.example.erpnext.network.serializers.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatHistoryPojo {
    @SerializedName("page")
    @Expose
    private int pageNo;
    @SerializedName("id")
    @Expose
    private int userId;
    @SerializedName("conversation_id")
    @Expose
    private int roomId;

    public ChatHistoryPojo(int pageNo, int userId, int roomId) {
        this.pageNo = pageNo;
        this.userId = userId;
        this.roomId = roomId;
    }


    public ChatHistoryPojo() {
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

}
