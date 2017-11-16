package com.myapplication.utility;

import android.app.Activity;

import com.arasthel.asyncjob.AsyncJob;
import com.linecorp.linesdk.api.LineApiClient;
import com.linecorp.linesdk.api.LineApiClientBuilder;
import com.myapplication.Object.SharedPreferenceKey;
import com.variable.UtilityAsync;
import com.variable.UtilitySharedPreferences;

/**
 * Created by star on 2017/11/15.
 */

public class UtilityLINE {
    private static LineApiClient lineApiClient;
    public static final String LINE_ACCESS_TOKEN = "LINE_ACCESS_TOKEN";
    public static final String LINE_DISPLAY_NAME = "LINE_DISPLAY_NAME";
    public static final String LINE_STATUS_MESSAGE = "LINE_STATUS_MESSAGE";
    public static final String LINE_USER_ID = "LINE_USER_ID";
    public static final String LINE_PIC_URL = "LINE_PIC_URL";
    public static final String LINE_EXPIRE_TIME = "LINE_EXPIRE_TIME";
    public static final String LINE_REFRESH_ACCESS_TOKEN = "LINE_REFRESH_ACCESS_TOKEN";
    public static final String LINE_VERIFY_TOKEN = "LINE_VERIFY_TOKEN";
    private static UtilitySharedPreferences sharedPreferences;

    private volatile static UtilityLINE u;

    public UtilityLINE (){}

    public static UtilityLINE getNewInstance(final Activity activity
            , final String channelID
            , final ILINE iline) {
        if(u == null) {
            synchronized (UtilityLINE.class) {
                if(u == null) {
                    u = new UtilityLINE();

                    AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob(){
                        @Override
                        public void doOnBackground() {
                            // doOnBackground
                            LineApiClientBuilder apiClientBuilder = new LineApiClientBuilder(activity
                                    , channelID);
                            lineApiClient = apiClientBuilder.build();

                            sharedPreferences = UtilitySharedPreferences.getNewInstance(activity);
                            sharedPreferences.save(LINE_ACCESS_TOKEN, getCurrentAccessToken());
                            sharedPreferences.save(LINE_REFRESH_ACCESS_TOKEN, getRefreshAccessToken());
                            sharedPreferences.save(LINE_VERIFY_TOKEN, getVerifyToken());
                            sharedPreferences.save(LINE_EXPIRE_TIME, getExpiresInMillis());
                            sharedPreferences.save(LINE_DISPLAY_NAME, getDisplayName());
                            sharedPreferences.save(LINE_STATUS_MESSAGE, getStatusMessage());
                            sharedPreferences.save(LINE_USER_ID, getUserId());
                            sharedPreferences.save(LINE_PIC_URL, getPictureUrl());

                            if(iline!=null){
                                iline.onGetLineApiClientFinish();
                            }
                        }
                    }, UtilityAsync.getNewInstance().getExecutorService());
                }
            }
        }
        return u;
    }

    public LineApiClient getLineApiClient(){
        return lineApiClient;
    }

    public static String getDisplayName(){
        return sharedPreferences.loadString(LINE_DISPLAY_NAME, null);
    }

    public static String getStatusMessage(){
        return sharedPreferences.loadString(LINE_STATUS_MESSAGE, null);
    }

    public static String getUserId(){
        return sharedPreferences.loadString(LINE_USER_ID, null);
    }

    public static String getCurrentAccessToken(){
        return sharedPreferences.loadString(LINE_ACCESS_TOKEN, null);
    }

    public static String getRefreshAccessToken(){
        return sharedPreferences.loadString(LINE_REFRESH_ACCESS_TOKEN, null);
    }

    public static String getVerifyToken(){
        return sharedPreferences.loadString(LINE_VERIFY_TOKEN, null);
    }

    public static long getExpiresInMillis(){
        return sharedPreferences.loadLong(LINE_EXPIRE_TIME, -1);
    }

    public static String getPictureUrl(){
        return sharedPreferences.loadString(LINE_PIC_URL, null);
    }

    public interface ILINE {
        void onGetLineApiClientFinish();
    }
}
