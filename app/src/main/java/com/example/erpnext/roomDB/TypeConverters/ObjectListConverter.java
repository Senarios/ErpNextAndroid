package com.example.erpnext.roomDB.TypeConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ObjectListConverter {
    @TypeConverter
    public static String ListToJson(List<Object> replyMessages) {
        if (replyMessages == null) return null;
        Type type = new TypeToken<List<Object>>() {
        }.getType();
        String json = new Gson().toJson(replyMessages, type);
        Log.i("JSON", "toJson: " + json);
        return replyMessages.isEmpty() ? null : json;
    }

    @TypeConverter
    public static List<Object> JsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Object>>() {
        }.getType();
        List<Object> replyMessages = gson.fromJson(json, type);
        return replyMessages;
    }
}
