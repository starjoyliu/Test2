package com.variable;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by star on 2017/11/1.
 */

public class UtilityToast {
    private volatile static UtilityToast u;

    public UtilityToast (){}

    public static UtilityToast getNewInstance() {
        if(u == null) {
            synchronized (UtilityToast.class) {
                if(u == null) {
                    u = new UtilityToast();
                }
            }
        }
        return u;
    }

    public void show(final Activity context, final String msg, final int duration){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, duration).show();
            }
        });
    }

    public void show(Activity context, String msg){
        show(context, msg, Toast.LENGTH_SHORT);
    }
}
