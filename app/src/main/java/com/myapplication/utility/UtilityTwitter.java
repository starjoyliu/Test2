package com.myapplication.utility;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * must call "getNewInstance" to init twitter in application onCreate
 *
 * Once Twitter kit has been initialized TwitterCore, TweetUi, and TweetComposer can be accessed through their singleton accessor.
 * * TwitterCore.getInstance()
 * * TweetUi.getInstance()
 * * TweetComposer.getInstance()
 *
 * Created by star on 2017/11/16.
 */
public class UtilityTwitter {
    private volatile static UtilityTwitter u;

    public UtilityTwitter (){}

    public static UtilityTwitter getNewInstance(Context context
            , String consumerKey, String consumerSecret, String callBackURL) {
        if(u == null) {
            synchronized (UtilityTwitter.class) {
                if(u == null) {
                    u = new UtilityTwitter();
                }
            }
        }

        initTwitter(context, consumerKey, consumerSecret, callBackURL);
        return u;
    }

    private static void initTwitter(Context context
            , String consumerKey, String consumerSecret, String callBackURL){
        TwitterConfig config = new TwitterConfig.Builder(context)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(consumerKey, consumerSecret))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }
}
