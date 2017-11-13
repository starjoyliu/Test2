package com.variable;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by star on 2017/11/1.
 */

public class UtilitySharedPreferences {
    private volatile static UtilitySharedPreferences u;
    private volatile static SharedPreferences sharedPreferences;

    public UtilitySharedPreferences(){}

    public static UtilitySharedPreferences getNewInstance(Context context) {
        if(u == null) {
            synchronized (UtilitySharedPreferences.class) {
                if(u == null) {
                    u = new UtilitySharedPreferences();
                    sharedPreferences = getSharedPreferences(context);
                }
            }
        }
        return u;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

    public void save(String key, String m){
        sharedPreferences.edit().putString(key, m).apply();
    }

    public void save(String key, int i){
        sharedPreferences.edit().putInt(key, i).apply();
    }

    public void save(String key, long l){
        sharedPreferences.edit().putLong(key, l).apply();
    }

    public void save(String key, float f){
        sharedPreferences.edit().putFloat(key, f).apply();
    }

    public void save(String key, boolean b){
        sharedPreferences.edit().putBoolean(key, b).apply();
    }

    public String loadString(String key, String msg){
        return sharedPreferences.getString(key, msg);
    }

    public int loadInt(String key, int i){
        return sharedPreferences.getInt(key, i);
    }

    public long loadLong(String key, long l){
        return sharedPreferences.getLong(key, l);
    }

    public float loadFloat(String key, float f){
        return sharedPreferences.getFloat(key, f);
    }

    public boolean loadBoolean(String key, boolean b){
        return sharedPreferences.getBoolean(key, b);
    }
}
