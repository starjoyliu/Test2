package com.myapplication.utility;

import android.app.Activity;

import com.arasthel.asyncjob.AsyncJob;
import com.linecorp.linesdk.api.LineApiClient;
import com.linecorp.linesdk.api.LineApiClientBuilder;
import com.variable.UtilityAsync;

/**
 * Created by star on 2017/11/15.
 */

public class UtilityLINE {
    private static LineApiClient lineApiClient;

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

    public String getDisplayName(){
        return lineApiClient.getProfile().getResponseData().getDisplayName();
    }

    public String getStatusMessage(){
        return lineApiClient.getProfile().getResponseData().getStatusMessage();
    }

    public String getUserId(){
        return lineApiClient.getProfile().getResponseData().getUserId();
    }

    public String getCurrentAccessToken(){
        lineApiClient.refreshAccessToken().getResponseData().getAccessToken();
        return lineApiClient.getCurrentAccessToken().getResponseData().getAccessToken();
    }

    public String refreshAccessToken(){
        return lineApiClient.refreshAccessToken().getResponseData().getAccessToken();
    }

    public String verifyToken(){
        return lineApiClient.verifyToken().getResponseData().getAccessToken().getAccessToken();
    }

    public long getExpiresInMillis(){
        return lineApiClient.getCurrentAccessToken().getResponseData().getExpiresInMillis();
    }

    public interface ILINE {
        void onGetLineApiClientFinish();
    }
}
