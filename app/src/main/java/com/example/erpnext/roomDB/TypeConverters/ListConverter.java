package com.example.erpnext.roomDB.TypeConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListConverter {
    @TypeConverter
    public static String ListToJson(List<String> replyMessages) {
        if (replyMessages == null) return null;
        Type type = new TypeToken<List<String>>() {
        }.getType();
        String json = new Gson().toJson(replyMessages, type);
        Log.i("JSON", "toJson: " + json);
        return replyMessages.isEmpty() ? null : json;
    }

    @TypeConverter
    public static List<String> JsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> replyMessages = gson.fromJson(json, type);
        return replyMessages;
    }
}
