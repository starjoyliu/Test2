package com.variable;

import android.app.Activity;
import android.support.annotation.BoolRes;
import android.support.annotation.IntegerRes;

/**
 * Created by star on 2017/11/3.
 */

public class UtilityRes {
    private volatile static UtilityRes u;

    public UtilityRes (){}

    public static UtilityRes getNewInstance() {
        if(u == null) {
            synchronized (UtilityRes.class) {
                if(u == null) {
                    u = new UtilityRes();
                }
            }
        }
        return u;
    }

    /**
     * 取得integer值
     * @param activity
     * @param res
     * @return
     */
    public int getInteger(Activity activity, @IntegerRes int res){
        return activity.getResources().getInteger(res);
    }

    /**
     * 取得boolean值
     * @param activity
     * @param res
     * @return
     */
    public boolean getBool(Activity activity, @BoolRes int res){
        return activity.getResources().getBoolean(res);
    }
}
