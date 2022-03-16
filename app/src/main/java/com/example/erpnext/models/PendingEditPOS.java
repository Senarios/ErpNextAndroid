package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.erpnext.network.serializers.requestbody.EditProfileRequestBody;
import com.google.gson.annotations.SerializedName;

@Entity
public class PendingEditPOS {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @SerializedName("pending_edit_pos")
    @Embedded
    private EditProfileRequestBody editProfileRequestBody;

    public PendingEditPOS() {
    }

    public EditProfileRequestBody getEditProfileRequestBody() {
        return editProfileRequestBody;
    }

    public void setEditProfileRequestBody(EditProfileRequestBody editProfileRequestBody) {
        this.editProfileRequestBody = editProfileRequestBody;
    }

    int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
