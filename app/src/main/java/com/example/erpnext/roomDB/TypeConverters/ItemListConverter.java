package com.example.erpnext.roomDB.TypeConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.erpnext.models.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ItemListConverter {
    @TypeConverter
    public static String ListToJson(List<Item> list) {
        if (list == null) return null;
        Type type = new TypeToken<List<Item>>() {
        }.getType();
        String json = new Gson().toJson(list, type);
        Log.i("JSON", "toJson: " + json);
        return list.isEmpty() ? null : json;
    }

    @TypeConverter
    public static List<Item> JsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Object>>() {
        }.getType();
        List<Item> list = gson.fromJson(json, type);
        return list;
    }
}
