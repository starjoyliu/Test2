package com.myapplication.View.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.crash.FirebaseCrash;
import com.myapplication.R;

/**
 * Created by star on 2017/10/26.
 */

public class MainApplication extends MultiDexApplication{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        /**
         * 開關Firebase Crash Reporting
         */
        FirebaseCrash.setCrashCollectionEnabled(getResources().getBoolean(R.bool.crash_reporting));
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
