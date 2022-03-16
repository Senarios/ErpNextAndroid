package com.example.erpnext.roomDB.TypeConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.erpnext.models.MenuMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MenuMessageConverter {
    @TypeConverter
    public static String ListToJson(List<MenuMessage> replyMessages) {
        if (replyMessages == null) return null;
        Type type = new TypeToken<List<MenuMessage>>() {
        }.getType();
        String json = new Gson().toJson(replyMessages, type);
        Log.i("JSON", "toJson: " + json);
        return replyMessages.isEmpty() ? null : json;
    }

    @TypeConverter
    public static List<MenuMessage> JsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MenuMessage>>() {
        }.getType();
        List<MenuMessage> replyMessages = gson.fromJson(json, type);
        return replyMessages;
    }
}
