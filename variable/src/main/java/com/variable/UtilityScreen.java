package com.variable;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.provider.Settings;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by star on 2017/11/1.
 */
public class UtilityScreen {
    private volatile static UtilityScreen u;
    public UtilityScreen(){}

    public static UtilityScreen getNewInstance() {
        if(u == null) {
            synchronized (UtilityScreen.class) {
                if(u == null) {
                    u = new UtilityScreen();
                }
            }
        }
        return u;
    }
    
    /**
     * 設定為全螢幕
     * @param activity
     */
    public void setFullScreen(Activity activity){
        activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 是否為橫式
     * @param activity
     * @return
     */
    public boolean isLandscape(Activity activity){
        return getOrientation(activity)== Configuration.ORIENTATION_LANDSCAPE?true:false;
    }

    /**
     * 1. {@link Configuration#ORIENTATION_UNDEFINED}
     * 2. {@link Configuration#ORIENTATION_PORTRAIT}
     * 3. {@link Configuration#ORIENTATION_LANDSCAPE}
     * @param activity
     * @return
     */
    public int getOrientation(Activity activity){
        return activity.getResources().getConfiguration().orientation;
    }

    /**
     * 設定螢幕直/橫
     * @param activity
     * @param requestedOrientation
     * {@link ActivityInfo#SCREEN_ORIENTATION_LANDSCAPE}
     * {@link ActivityInfo#SCREEN_ORIENTATION_PORTRAIT}
     */
    public void setOrientation(Activity activity, int requestedOrientation){
        activity.setRequestedOrientation(requestedOrientation);
    }

    /**
     * 設定設備旋轉
     * @param activity
     * @param rotationDegree
     * {@link Surface#ROTATION_0}
     * {@link Surface#ROTATION_90}
     * {@link Surface#ROTATION_180}
     * {@link Surface#ROTATION_270}
     * <!-- 寫系統設定權限-->
     * <uses-permission android:name="android.permission.WRITE_SETTINGS" />
     */
    public void setDeviceOrientation(Activity activity, int rotationDegree){
        /**
         * 將旋轉偵測設為關閉
         */
        Settings.System.putInt(
                activity.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION,
                0
        );
        Settings.System.putInt(
                activity.getContentResolver(),
                Settings.System.USER_ROTATION,
                rotationDegree //Or a different ROTATION_ constant
        );
    }

    /**
     * 恢復使用者設定的旋轉方式
     * @param activity
     */
    public void resetDeviceOrientation(Activity activity, int accelerometer){
        /**
         * 將旋轉偵測設回使用者設定
         */
        Settings.System.putInt(
                activity.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION,
                accelerometer
        );
    }

    /**
     * 取得是否鎖定旋轉螢幕
     * @param activity
     * @return true: 鎖定, false: 沒鎖定
     */
    public boolean getDeviceAccelerometer(Activity activity){
        if(Settings.System.getInt(activity.getContentResolver()
                ,Settings.System.ACCELEROMETER_ROTATION, 0) == 1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判斷是否為pad
     * @param context
     * @return true:是, false: 否
     */
    public boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
