package com.myapplication.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.crash.FirebaseCrash;
import com.log.LogLevel;
import com.log.Logger;
import com.myapplication.R;
import com.orm.SugarContext;
import com.myapplication.utility.skin.UtilitySkin;
import com.myapplication.utility.skin.key.SkinMode;

/**
 * Created by star on 2017/10/26.
 */

public class MainApplication extends MultiDexApplication{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 開關 Firebase Crash Reporting
         */
        FirebaseCrash.setCrashCollectionEnabled(getResources().getBoolean(R.bool.crash_reporting));
        /**
         * 設定Logger是否打開
         */
        Logger.init().logLevel(getResources().getBoolean(R.bool.debug_open)? LogLevel.FULL:LogLevel.NONE);
        /**
         * init Sugar Database
         */
        SugarContext.init(getApplicationContext());
        /**
         * 設定 skin
         */
        UtilitySkin.getNewInstance().initSkin(getResources().getString(R.string.skin_mode));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        /**
         * terminate Sugar Database
         */
        SugarContext.terminate();
    }
}
