package com.variable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by star on 2017/11/1.
 */

public class UtilitySwitchActivity {
    private final String DEFAULT_DATA = "data";
    private final boolean DEFAULT_BACK = false;

    private volatile static UtilitySwitchActivity u;

    public UtilitySwitchActivity (){}

    public static UtilitySwitchActivity getNewInstance() {
        if(u == null) {
            synchronized (UtilitySwitchActivity.class) {
                if(u == null) {
                    u = new UtilitySwitchActivity();
                }
            }
        }
        return u;
    }

    public void switchActivity(Activity activity, Class<?> cls){
        switchActivity(activity, cls, DEFAULT_BACK);
    }

    public void switchActivity(Activity activity, Class<?> cls, Bundle data){
        switchActivity(activity, cls, DEFAULT_BACK, data);
    }

    public void switchActivity(Activity activity, Class<?> cls, boolean isBack){
        switchActivity(activity, cls, isBack, new Bundle());
    }

    public void switchActivity(Activity activity, Class<?> cls, boolean isBack, Bundle data){
        switchActivity(activity, cls, isBack, data, DEFAULT_DATA);
    }

    public void switchActivity(Activity activity, Class<?> cls, boolean isBack, Bundle data, String dataKey){
        Intent intent = new Intent();
        intent.putExtra(dataKey, data);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        if(false==isBack) activity.finish();
    }

    public void switchActivity(Activity activity, Class<?> cls, boolean isBack, Serializable data){
        switchActivity(activity, cls, isBack, data, DEFAULT_DATA);
    }

    public void switchActivity(Activity activity, Class<?> cls, boolean isBack, Serializable data, String dataKey){
        Intent intent = new Intent();
        intent.putExtra(dataKey, data);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        if(false==isBack) activity.finish();
    }

    public Bundle getBundleData(Activity activity){
        return getBundleData(activity, DEFAULT_DATA);
    }

    public Bundle getBundleData(Activity activity, String dataKey){
        return activity.getIntent().getBundleExtra(dataKey);
    }

    public Serializable getSerializableData(Activity activity){
        return getSerializableData(activity, DEFAULT_DATA);
    }

    public Serializable getSerializableData(Activity activity, String dataKey){
        return activity.getIntent().getSerializableExtra(dataKey);
    }
}
