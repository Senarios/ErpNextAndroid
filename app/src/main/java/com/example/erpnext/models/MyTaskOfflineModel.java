package com.example.erpnext.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class MyTaskOfflineModel {
//    @PrimaryKey(autoGenerate = true)
//    public int uid;
    @SerializedName("emailName")
    @Expose
    private String emailName;
    @SerializedName("taskName")
    @Expose
    @NonNull
    @PrimaryKey
    private String taskName;
    @SerializedName("shopName")
    @Expose
    private String shopName;
    @SerializedName("shopStat")
    @Expose
    private String shopStat;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopStat() {
        return shopStat;
    }

    public void setShopStat(String shopStat) {
        this.shopStat = shopStat;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
