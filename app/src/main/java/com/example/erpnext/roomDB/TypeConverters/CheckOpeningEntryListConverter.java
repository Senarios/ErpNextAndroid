package com.example.erpnext.roomDB.TypeConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.erpnext.models.CheckOpeningEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CheckOpeningEntryListConverter {
    @TypeConverter
    public static String ListToJson(List<CheckOpeningEntry> openingEntries) {
        if (openingEntries == null) return null;
        Type type = new TypeToken<List<CheckOpeningEntry>>() {
        }.getType();
        String json = new Gson().toJson(openingEntries, type);
        Log.i("JSON", "toJson: " + json);
        return openingEntries.isEmpty() ? null : json;
    }

    @TypeConverter
    public static List<CheckOpeningEntry> JsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<CheckOpeningEntry>>() {
        }.getType();
        List<CheckOpeningEntry> openingEntries = gson.fromJson(json, type);
        return openingEntries;
    }
}
