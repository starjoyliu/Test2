package com.variable;

import android.content.Context;
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

    public void show(Context context, String msg, int duration){
        Toast.makeText(context, msg, duration).show();
    }

    public void show(Context context, String msg){
        show(context, msg, Toast.LENGTH_SHORT);
    }
}
