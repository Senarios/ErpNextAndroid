package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.erpnext.network.serializers.requestbody.CreateLoyaltyRequestBody;
import com.google.gson.annotations.SerializedName;

@Entity
public class PendingLoyalty {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @SerializedName("pending_loyalty")
    @Embedded
    private CreateLoyaltyRequestBody createOPERequestBody;

    public PendingLoyalty() {
    }

    public CreateLoyaltyRequestBody getCreateOPERequestBody() {
        return createOPERequestBody;
    }

    public void setCreateOPERequestBody(CreateLoyaltyRequestBody createOPERequestBody) {
        this.createOPERequestBody = createOPERequestBody;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
