package com.example.erpnext.network.serializers.response;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.erpnext.models.ProfileDoc;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class GetProfileDocResponse {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    //   @ColumnInfo(name = "doc_name")
//    public String name;
    @SerializedName("message")
    @Expose
    @Embedded
    private ProfileDoc ProfileDoc;

    public GetProfileDocResponse() {
    }

    public com.example.erpnext.models.ProfileDoc getProfileDoc() {
        return ProfileDoc;
    }

    public void setProfileDoc(com.example.erpnext.models.ProfileDoc profileDoc) {
        ProfileDoc = profileDoc;
    }
}