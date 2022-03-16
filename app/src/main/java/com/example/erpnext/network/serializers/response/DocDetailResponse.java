package com.example.erpnext.network.serializers.response;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.erpnext.models.DocInfo;
import com.example.erpnext.models.SaveDoc;
import com.example.erpnext.roomDB.TypeConverters.SaveDocListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters(SaveDocListConverter.class)
public class DocDetailResponse {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @SerializedName("profile_name")
    @Expose
    public String profileName = null;
    @SerializedName("docs")
    @Expose
    private List<SaveDoc> docs = null;
    @SerializedName("docinfo")
    @Expose
    @Embedded
    private DocInfo docinfo;

    public DocDetailResponse() {
    }

    public List<SaveDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<SaveDoc> docs) {
        this.docs = docs;
    }

    public DocInfo getDocinfo() {
        return docinfo;
    }

    public void setDocinfo(DocInfo docinfo) {
        this.docinfo = docinfo;
    }

}
