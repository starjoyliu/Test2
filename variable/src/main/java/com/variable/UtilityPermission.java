package com.variable;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by star on 2017/11/1.
 */
public class UtilityPermission {
    public static final int ViaQRCodeActivity_CAMERA_REQUEST = 1001;
    public static final int QRCodeActivity_CAMERA_REQUEST = 1002;
    public static final int SDCARD_REQUEST = 1003;
    public static final int Reply_CAMERA_REQUEST = 1004;
    public static final int SendMessage_CAMERA_REQUEST = 1005;

    private volatile static UtilityPermission u;
    public UtilityPermission(){}

    public static UtilityPermission getNewInstance() {
        if(u == null) {
            synchronized (UtilityPermission.class) {
                if(u == null) {
                    u = new UtilityPermission();
                }
            }
        }
        return u;
    }
    
    /**
     * 檢查是否有SD CARD 讀/寫權限
     * @param activity
     * @return true:有, false:無
     */
    public boolean checkSDCardPermission(Activity activity){
        boolean hasPermission = true;
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            hasPermission = false;
        }
        return hasPermission;
    }

    /**
     * 檢查是否有相機權限
     * @param activity
     * @return true:有, false:無
     */
    public boolean checkCameraPermission(Activity activity){
        boolean hasPermission = true;
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            hasPermission = false;
        }
        return hasPermission;
    }
}
