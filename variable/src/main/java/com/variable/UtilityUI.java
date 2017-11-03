package com.variable;

import android.app.Activity;

/**
 * Created by star on 2017/11/3.
 */

public class UtilityUI {
    private volatile static UtilityUI u;

    public UtilityUI (){}

    public static UtilityUI getNewInstance() {
        if(u == null) {
            synchronized (UtilityUI.class) {
                if(u == null) {
                    u = new UtilityUI();
                }
            }
        }
        return u;
    }
    
    public int Dp2Pixel(Activity activity, float dp){
        final float scale = activity.getResources().getDisplayMetrics().density;
        int mGestureThreshold = (int) (dp * scale + 0.5f);
        return mGestureThreshold;
    }
}
