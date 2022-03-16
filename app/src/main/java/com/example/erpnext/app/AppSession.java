package com.example.erpnext.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.example.erpnext.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class AppSession {

    public static final String BLANK_STRING_KEY = "N/A";
    public static final String WRONG_PAIR = "Key-Value pair cannot be blank or null";
    private static final String SHARED_PREFERENCE_NAME = "SMRT";
    private static Location userLocation;

    public static boolean put(final String key, final String value) {
        if (key == null || value == null || key.equals("")) {
            throw new IllegalArgumentException(WRONG_PAIR);
        }
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean clearSharedPref() {
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        return editor.commit();
    }

    public static boolean put(final String key, final int value) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException(WRONG_PAIR);
        }
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static boolean put(final String key, final boolean value) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException(WRONG_PAIR);
        }
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean put(final String key, final long value) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException(WRONG_PAIR);
        }
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean put(final String key, final boolean value, final String sharedPreferenceName) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException(WRONG_PAIR);
        }
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static String get(final String key) {
        SharedPreferences savedSession = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return savedSession.getString(key, BLANK_STRING_KEY);
    }

    public static String get(final String key, final String defaultValue) {
        SharedPreferences savedSession = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return savedSession.getString(key, defaultValue);
    }

    public static int getInt(final String key) {
        SharedPreferences savedSession = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return savedSession.getInt(key, 0);
    }

    public static long getLong(final String key) {
        SharedPreferences savedSession = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return savedSession.getLong(key, 0);
    }

    public static Boolean getBoolean(final String key) {
        SharedPreferences savedSession = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return savedSession.getBoolean(key, false);
    }

    public static Boolean getBoolean(final String key, final boolean defaultValue) {
        SharedPreferences savedSession = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return savedSession.getBoolean(key, defaultValue);
    }

    public static void remove(final String key) {
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.commit();
    }

    public static boolean removeRememberMe() {
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(Constants.REMEMBER_ME, Context.MODE_PRIVATE).edit();
        editor.clear();
        return editor.commit();
    }

    public static Location getUserLocation() {
        if (userLocation == null) {
            Location location = new Location("N/A");
            location.setLatitude(0);
            location.setLongitude(0);
            return location;
        } else
            return userLocation;
    }

    public static void setUserLocation(Location location) {
        userLocation = location;
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences savedSession = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return savedSession.getInt(key, defaultValue);
    }

    public static boolean hasKey(String key) {
        SharedPreferences savedSession = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return savedSession.contains(key);
    }

    public static boolean put(final String key, final Double value) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException(WRONG_PAIR);
        }
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value.toString());
        return editor.commit();
    }

    public static Double getDouble(String key) {
        SharedPreferences savedSession = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return Double.parseDouble(savedSession.getString(key, "0.0"));
    }

    public static boolean put(String key, ArrayList<Integer> value) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException(WRONG_PAIR);
        }
        String string = "";
        for (Integer inte : value) {
            string = string + inte + ",";
        }
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, string);
        return editor.commit();
    }

    public static ArrayList<Integer> getIntArrayList(String key) {
        SharedPreferences savedSession = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String[] list = savedSession.getString(key, "").split(",");
        ArrayList<Integer> integers = new ArrayList<>();
        for (String item : list) {
            try {
                integers.add(Integer.parseInt(item));
            } catch (Exception e) {

            }
        }
        return integers;
    }

    public void putTaskList(String key, HashMap<Integer, Boolean> Taskmap) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException(WRONG_PAIR);
        }
        SharedPreferences.Editor editor = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME + "HashMap", Context.MODE_PRIVATE).edit();
        for (Integer s : Taskmap.keySet()) {
            editor.putBoolean(key + s, Taskmap.get(s));
        }
        editor.commit();
    }

    public HashMap<String, Boolean> getTaskList(String key) {

        SharedPreferences pref = MainApp.getAppContext().getSharedPreferences(SHARED_PREFERENCE_NAME + "HashMap", Context.MODE_PRIVATE);
        HashMap<String, Boolean> map = (HashMap<String, Boolean>) pref.getAll();
        HashMap<String, Boolean> Taskap = new HashMap<String, Boolean>();
        for (String s : map.keySet()) {
            boolean value = map.get(s);
            String KEY = s.substring(key.length());
            Taskap.put(KEY, value);
        }
        return Taskap;
    }
}
