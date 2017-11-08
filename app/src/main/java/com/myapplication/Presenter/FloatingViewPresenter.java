package com.myapplication.Presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

import com.myapplication.Model.FloatingViewModel;
import com.myapplication.Model.PIPModel;
import com.myapplication.R;
import com.myapplication.View.floatingview.IFloatingViewActivity;
import com.variable.UtilityPhone;
import com.variable.UtilityRes;

/**
 * Created by star on 2017/11/8.
 */

public class FloatingViewPresenter {
    private final int PIP_MODE_API_LEVEL = android.os.Build.VERSION_CODES.O;
    private final int DRAW_OVERLAYS_API_LEVEL = android.os.Build.VERSION_CODES.M;
    private final int SYSTEM_ALERT_WINDOW_PERMISSION = 1001;

    private IFloatingViewActivity iFloatingViewActivity;
    private FloatingViewModel floatingViewModel;
    private Activity activity;
    private UtilityPhone utilityPhone;
    private UtilityRes utilityRes;

    public FloatingViewPresenter(Activity activity, IFloatingViewActivity iFloatingViewActivity) {
        this.activity = activity;
        this.iFloatingViewActivity = iFloatingViewActivity;

        utilityPhone = UtilityPhone.getNewInstance();
        utilityRes = UtilityRes.getNewInstance();
        floatingViewModel = new FloatingViewModel();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlays() {
        if (utilityPhone.API_LEVEL >= Build.VERSION_CODES.M && !canDrawOverlays()) {
            askPermission();
        }
    }

    /**
     * add permission into AndroidManifest.xml
     * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
     */
    public void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void triggerFloatingWindows(){
        if (utilityPhone.API_LEVEL < Build.VERSION_CODES.M) {
            iFloatingViewActivity.triggerFloatingWindows();
        } else if (Settings.canDrawOverlays(activity)) {
            iFloatingViewActivity.triggerFloatingWindows();
        } else {
            askPermission();
            iFloatingViewActivity.showNeedAlertWindowsPermission(utilityRes.getString(activity, R.string.activity_need_alert_window_permission));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean canDrawOverlays(){
        return Settings.canDrawOverlays(activity);
    }
}
