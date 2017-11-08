package com.myapplication.View.pip;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import com.log.Logger;
import com.myapplication.Presenter.PIPPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.variable.UtilityPhone;
import com.variable.UtilityRes;
import com.variable.UtilityToast;

/**
 * Created by star on 2017/11/7.
 */

public class PIPActivity extends BaseActivity implements View.OnClickListener, IPIPActivity{
    private final String TAG = PIPActivity.class.getSimpleName();

    private Button triggerPIPMode;
    private PIPPresenter pipPresenter;
    private UtilityToast utilityToast;
    private UtilityRes utilityRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pip);

        triggerPIPMode = findViewById(R.id.triggerPIPMode);

        pipPresenter = new PIPPresenter(activity,this);
        utilityToast = UtilityToast.getNewInstance();
        utilityRes = UtilityRes.getNewInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        triggerPIPMode.setOnClickListener(this);
        triggerPIPMode.setEnabled(false);

        pipPresenter.init();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            default:
                Logger.v(TAG + " no match id");
                break;
        }
    }

    @Override
    public void showNotSupportMsg(String msg) {
        utilityToast.show(activity, msg);
        triggerPIPMode.setEnabled(false);
    }

    @Override
    public void triggerPIPModeSuccess(String msg) {
        utilityToast.show(activity, msg);
        triggerPIPMode.setEnabled(false);
        triggerPIPMode.setText(utilityRes.getString(activity, R.string.activity_trigger_pip_success));
    }

    @Override
    public void supportPIPMode() {
        triggerPIPMode.setEnabled(true);
        triggerPIPMode.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                pipPresenter.triggerPIPMode();
            }
        });
    }
}
