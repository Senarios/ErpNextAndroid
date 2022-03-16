package com.example.erpnext.network.serializers.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupWithFbPojo {

    @SerializedName("email")
    @Expose
    private final String email;
    @SerializedName("authId")
    @Expose
    private final String authId;
    @SerializedName("doctorName")
    @Expose
    private final String name;

    public SignupWithFbPojo(String email, String authId, String name) {
        this.email = email;
        this.authId = authId;
        this.name = name;
    }
}
