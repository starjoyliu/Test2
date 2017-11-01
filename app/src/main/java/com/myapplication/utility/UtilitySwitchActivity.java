package com.myapplication.utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by star on 2017/11/1.
 */

public class UtilitySwitchActivity {
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
        switchActivity(activity, cls, false);
    }

    public void switchActivity(Activity activity, Class<?> cls, Bundle data){
        switchActivity(activity, cls, false, data);
    }

    public void switchActivity(Activity activity, Class<?> cls, boolean isBack){
        switchActivity(activity, cls, isBack, new Bundle());
    }

    public void switchActivity(Activity activity, Class<?> cls, boolean isBack, Bundle data){
        Intent intent = new Intent();
        intent.putExtras(data);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        if(false==isBack) activity.finish();
    }
}
