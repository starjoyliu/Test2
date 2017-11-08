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

    /**
     * switch activity
     * @param activity
     * @param cls
     */
    public void switchActivity(Activity activity, Class<?> cls){
        switchActivity(activity, cls, DEFAULT_BACK);
    }

    /**
     * switch activity
     * @param activity
     * @param cls
     * @param data bring data to next activity
     */
    public void switchActivity(Activity activity, Class<?> cls, Bundle data){
        switchActivity(activity, cls, DEFAULT_BACK, data);
    }

    /**
     * switch activity
     * @param activity
     * @param cls
     * @param isBack false: no back, true: will back
     */
    public void switchActivity(Activity activity, Class<?> cls, boolean isBack){
        switchActivity(activity, cls, isBack, new Bundle());
    }

    /**
     * switch activity
     * @param activity
     * @param cls
     * @param isBack false: no back, true: will back, default: {@value DEFAULT_BACK}
     * @param data bring data to next activity
     */
    public void switchActivity(Activity activity, Class<?> cls, boolean isBack, Bundle data){
        switchActivity(activity, cls, isBack, data, DEFAULT_DATA);
    }

    /**
     * switch activity
     * @param activity
     * @param cls
     * @param isBack false: no back, true: will back, default: {@value DEFAULT_BACK}
     * @param data bring data to next activity
     * @param dataKey bring data key, default: {@value DEFAULT_DATA}
     */
    public void switchActivity(Activity activity, Class<?> cls, boolean isBack, Bundle data, String dataKey){
        Intent intent = new Intent();
        intent.putExtra(dataKey, data);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        if(false==isBack) activity.finish();
    }

    /**
     * switch activity
     * @param activity
     * @param cls
     * @param isBack false: no back, true: will back, default: {@value DEFAULT_BACK}
     * @param data bring data to next activity
     */
    public void switchActivity(Activity activity, Class<?> cls, boolean isBack, Serializable data){
        switchActivity(activity, cls, isBack, data, DEFAULT_DATA);
    }

    /**
     * switch activity
     * @param activity
     * @param cls
     * @param isBack false: no back, true: will back, default: {@value DEFAULT_BACK}
     * @param data bring data to next activity
     * @param dataKey bring data key, default: {@value DEFAULT_DATA}
     */
    public void switchActivity(Activity activity, Class<?> cls, boolean isBack, Serializable data, String dataKey){
        Intent intent = new Intent();
        intent.putExtra(dataKey, data);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        if(false==isBack) activity.finish();
    }

    /**
     * get bundle data
     * @param activity
     * @return
     */
    public Bundle getBundleData(Activity activity){
        return getBundleData(activity, DEFAULT_DATA);
    }

    /**
     * get bundle data
     * @param activity
     * @param dataKey bundle data key
     * @return
     */
    public Bundle getBundleData(Activity activity, String dataKey){
        return activity.getIntent().getBundleExtra(dataKey);
    }

    /**
     * get serializable data
     * @param activity
     * @return
     */
    public Serializable getSerializableData(Activity activity){
        return getSerializableData(activity, DEFAULT_DATA);
    }

    /**
     * get serializable data
     * @param activity
     * @param dataKey serializable data key
     * @return
     */
    public Serializable getSerializableData(Activity activity, String dataKey){
        return activity.getIntent().getSerializableExtra(dataKey);
    }
}
