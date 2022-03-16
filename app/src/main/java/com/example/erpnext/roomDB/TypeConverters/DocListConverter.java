package com.example.erpnext.roomDB.TypeConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.erpnext.models.Doc;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DocListConverter {
    @TypeConverter
    public static String ListToJson(List<Doc> list) {
        if (list == null) return null;
        Type type = new TypeToken<List<Doc>>() {
        }.getType();
        String json = new Gson().toJson(list, type);
        Log.i("JSON", "toJson: " + json);
        return list.isEmpty() ? null : json;
    }

    @TypeConverter
    public static List<Doc> JsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Doc>>() {
        }.getType();
        List<Doc> list = gson.fromJson(json, type);
        return list;
    }
}
