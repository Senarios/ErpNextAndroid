package com.example.erpnext.network.serializers.response;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.erpnext.models.SearchResult;
import com.example.erpnext.roomDB.TypeConverters.SearchResultListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters(SearchResultListConverter.class)
public class SearchLinkResponse {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "doctype")
    public String doctype;
    @ColumnInfo(name = "filters")
    public String filters;
    @ColumnInfo(name = "query")
    public String query;
    @ColumnInfo(name = "reference")
    public String reference;
    @SerializedName("results")
    @ColumnInfo(name = "searchResultList")

    private List<SearchResult> searchResultList = null;
    @SerializedName("request_code")
    @Expose
    private int requestCode;


    public SearchLinkResponse() {
    }

    public List<SearchResult> getResults() {
        return searchResultList;
    }

    public void setResults(List<SearchResult> results) {
        this.searchResultList = results;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public List<SearchResult> getSearchResultList() {
        return searchResultList;
    }

    public void setSearchResultList(List<SearchResult> searchResultList) {
        this.searchResultList = searchResultList;
    }
}
