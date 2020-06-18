package org.tensorflow.demo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.tensorflow.demo.Melanomax;

/**
 * Created by adhit on 03/01/2018.
 */

public class SharedPrefUtil {
    private static SharedPreferences getPref() {
        Context context = Melanomax.getContext();
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveString(String key, String value) {
        getPref().edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getPref().getString(key, null);
    }

    public static void saveInt(String key, int value) {
        getPref().edit().putInt(key, value).apply();
    }

    public static int getInt(String key) {
        return getPref().getInt(key, 0);
    }

    public static void saveBoolean(String key, boolean value) {
        getPref().edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return getPref().getBoolean(key, false);
    }

    public static void saveLong(String key, Long value) {
        getPref().edit().putLong(key, value).apply();
    }

    public static Long getLong(String key) {
        return getPref().getLong(key, 0);
    }

    public static void saveFloat(String key, float value) {
        getPref().edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key) {
        return getPref().getFloat(key, 0);
    }

    public static void remove(String key) {
        getPref().edit().remove(key).apply();
    }

    public static SharedPreferences.Editor getEditor() {
        return getPref().edit();
    }

    public static void saveObject(String key, Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        getPref().edit().putString(key, json).apply();
    }

    public static Object getObject(String key, Object object){
        Gson gson = new Gson();
        String json = getPref().getString(key, "");
        return gson.fromJson(json, (Class<Object>) object);
    }
}
