package com.variable;

import android.os.Build;
import android.os.Bundle;

import com.log.Logger;

/**
 * 取得手機資訊
 * Created by star on 2017/11/7.
 */
public class UtilityPhone {
    private static final String TAG = UtilityPhone.class.getSimpleName();
    public int API_LEVEL;
    public String SDK_VERSION;
    public String PHONE_MODEL;

    private volatile static UtilityPhone u;
    public UtilityPhone(){}

    public static UtilityPhone getNewInstance() {
        if(u == null) {
            synchronized (UtilityPhone.class) {
                if(u == null) {
                    u = new UtilityPhone();
                }
            }
        }
        return u;
    }

    public void init(){
        API_LEVEL = Build.VERSION.SDK_INT;
        SDK_VERSION = Build.VERSION.RELEASE;
        PHONE_MODEL = Build.MODEL;

        Logger.d(TAG+" API_LEVEL:" + API_LEVEL + " SDK_VERSION:"+SDK_VERSION + " PHONE_MODEL:"+PHONE_MODEL);
    }
}
