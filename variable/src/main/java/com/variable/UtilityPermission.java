package com.variable;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by star on 2017/11/1.
 */
public class UtilityPermission {
    public static final int REQUEST_CAMERA_PERM = 10061;
    public static final int REQUEST_SD_CARD_PERM = 10062;

    private SDPermission sdlistener;
    private CameraPermission cameralistener;

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
            if(sdlistener!=null) sdlistener.onSDPermission(hasPermission);
        }
        return hasPermission;
    }

    /**
     * 要求SD CARD權限
     *
     * PS:監聽
     * public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
     * @param activity
     */
    public void requestSDCardPermission(Activity activity){
        final String[] permissions
                = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_SD_CARD_PERM);
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
            if(cameralistener!=null) cameralistener.onCameraPermission(hasPermission);
        }
        return hasPermission;
    }

    /**
     * 要求camera權限
     *
     * PS:監聽
     * public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
     * @param activity
     */
    public void requestCameraPermission(Activity activity){
        final String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_CAMERA_PERM);
    }

    public interface SDPermission {
        void onSDPermission(boolean hasPermission);
    }

    public interface CameraPermission {
        void onCameraPermission(boolean hasPermission);
    }
}
