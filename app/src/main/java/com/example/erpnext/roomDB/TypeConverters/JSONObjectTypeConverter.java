package com.example.erpnext.roomDB.TypeConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class JSONObjectTypeConverter {
    @TypeConverter
    public static String ListToJson(JSONObject list) {
        if (list == null) return null;
        Type type = new TypeToken<JSONObject>() {
        }.getType();
        String json = new Gson().toJson(list, type);
        Log.i("JSON", "toJson: " + json);
        return json;
    }

    @TypeConverter
    public static JSONObject JsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<JSONObject>() {
        }.getType();
        JSONObject list = gson.fromJson(json, type);
        return list;
    }
}
