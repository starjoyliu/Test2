package com.myapplication.View.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.myapplication.R;
import com.myapplication.Object.Interface.IRemoteConfig;
import com.myapplication.utility.UtilityRemoteConfig;
import com.myapplication.utility.UtilityToast;

/**
 * Created by star on 2017/11/1.
 */

public class BaseActivity extends AppCompatActivity implements IRemoteConfig {
    private final String TAG = BaseActivity.class.getSimpleName();
    protected Activity activity = BaseActivity.this;
    protected Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 設定 remote config
         */
        UtilityRemoteConfig.getNewInstance().init(this);
        activity = BaseActivity.this;
        bundle = getIntent().getExtras();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        activity = BaseActivity.this;
//        bundle = getIntent().getExtras();
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
    public void onCompleteRemoteConfig(String msg) {
        UtilityToast.getNewInstance().show(activity, msg);
    }

    @Override
    public void onFailedRemoteConfig(String msg) {
        UtilityToast.getNewInstance().show(activity, msg);
    }
}
