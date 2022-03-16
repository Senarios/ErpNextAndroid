package com.example.erpnext.roomDB.TypeConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.erpnext.models.SearchResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SearchResultListConverter {
    @TypeConverter
    public static String ListToJson(List<SearchResult> list) {
        if (list == null) return null;
        Type type = new TypeToken<List<SearchResult>>() {
        }.getType();
        String json = new Gson().toJson(list, type);
        Log.i("JSON", "toJson: " + json);
        return list.isEmpty() ? null : json;
    }

    @TypeConverter
    public static List<SearchResult> JsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<SearchResult>>() {
        }.getType();
        List<SearchResult> list = gson.fromJson(json, type);
        return list;
    }
}
