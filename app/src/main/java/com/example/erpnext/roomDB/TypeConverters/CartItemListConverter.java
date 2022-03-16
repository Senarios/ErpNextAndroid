package com.example.erpnext.roomDB.TypeConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.erpnext.models.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CartItemListConverter {
    @TypeConverter
    public static String ListToJson(List<CartItem> list) {
        if (list == null) return null;
        Type type = new TypeToken<List<CartItem>>() {
        }.getType();
        String json = new Gson().toJson(list, type);
        Log.i("JSON", "toJson: " + json);
        return list.isEmpty() ? null : json;
    }

    @TypeConverter
    public static List<CartItem> JsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<CartItem>>() {
        }.getType();
        List<CartItem> list = gson.fromJson(json, type);
        return list;
    }
}
