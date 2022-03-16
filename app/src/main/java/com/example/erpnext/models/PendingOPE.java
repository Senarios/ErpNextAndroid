package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.erpnext.network.serializers.requestbody.CreateOPERequestBody;
import com.google.gson.annotations.SerializedName;

@Entity
public class PendingOPE {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @SerializedName("pending_ope")
    @Embedded
    private CreateOPERequestBody createOPERequestBody;

    public PendingOPE() {
    }

    public CreateOPERequestBody getCreateOPERequestBody() {
        return createOPERequestBody;
    }

    public void setCreateOPERequestBody(CreateOPERequestBody createOPERequestBody) {
        this.createOPERequestBody = createOPERequestBody;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
