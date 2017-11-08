package com.myapplication.Presenter;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Rational;
import android.widget.Toast;

import com.myapplication.Model.PIPModel;
import com.myapplication.R;
import com.myapplication.View.pip.IPIPActivity;
import com.myapplication.View.pip.PIPActivity;
import com.myapplication.View.pip.PIPService;
import com.variable.UtilityPhone;
import com.variable.UtilityRes;

/**
 * Created by star on 2017/11/7.
 */

public class PIPPresenter {
    private IPIPActivity ipipActivity;
    private PIPModel pipModel;
    private Activity activity;
    private UtilityPhone utilityPhone;
    private UtilityRes utilityRes;

    public PIPPresenter(Activity activity, IPIPActivity ipipActivity) {
        this.activity = activity;
        this.ipipActivity = ipipActivity;

        utilityPhone = UtilityPhone.getNewInstance();
        utilityRes = UtilityRes.getNewInstance();
        pipModel = new PIPModel();
    }

    /**
     * 初始化
     */
    public void init(){
        checkAPILevel();
    }


    /**
     * 檢查是否支援PIP Mode
     */
    private void checkAPILevel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            ipipActivity.supportPIPMode();
        }else{
            ipipActivity.showNotSupportMsg(utilityRes.getString(activity, R.string.activity_not_support_pip));
        }
    }


    /**
     * 驅動PIP mode
     */
    public void triggerPIPMode(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                activity.enterPictureInPictureMode();
                ipipActivity.triggerPIPModeSuccess(utilityRes.getString(activity, R.string.activity_trigger_pip_success));
            }else{
                ipipActivity.showNotSupportMsg(utilityRes.getString(activity, R.string.activity_not_support_pip));
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            ipipActivity.showNotSupportMsg(utilityRes.getString(activity, R.string.activity_not_support_pip));
        }
    }
}
