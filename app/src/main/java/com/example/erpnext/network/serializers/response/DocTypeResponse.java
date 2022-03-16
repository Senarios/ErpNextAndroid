package com.example.erpnext.network.serializers.response;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.erpnext.models.Doc;
import com.example.erpnext.roomDB.TypeConverters.DocListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters(DocListConverter.class)
public class DocTypeResponse {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "doctype")
    public String doctype;
    @SerializedName("docs")
    @Expose
    private List<Doc> docs = null;
    @SerializedName("user_settings")
    @Expose
    private String userSettings;

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public String getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(String userSettings) {
        this.userSettings = userSettings;
    }
}
