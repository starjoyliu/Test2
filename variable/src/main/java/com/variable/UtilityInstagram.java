package com.variable;

import android.app.Activity;

import com.variable.Object.Instagram.InstagramApp;

/**
 * Created by star on 2017/11/15.
 */
public class UtilityInstagram {
    private static InstagramApp instagramApp;

    private volatile static UtilityInstagram u;

    public UtilityInstagram (){}

    public static UtilityInstagram getNewInstance() {
        if(u == null) {
            synchronized (UtilityInstagram.class) {
                if(u == null) {
                    u = new UtilityInstagram();
                }
            }
        }
        return u;
    }
    
    public void showDialog(Activity activity
            , String clientId, String clientSecret, String callbackUrl
            , final IInstagram iInstagram){
        instagramApp = new InstagramApp(activity
                , clientId
                , clientSecret
                , callbackUrl);
        instagramApp.setListener(new InstagramApp.OAuthAuthenticationListener() {
            @Override
            public void onSuccess() {
                if(iInstagram!=null){
                    iInstagram.onInstagramSuccess();
                }
            }

            @Override
            public void onFail(String error) {
                if (iInstagram!=null){
                    iInstagram.onInstagramFail(error);
                }
            }
        });
        instagramApp.authorize();
    }

    public InstagramApp getInstagramApp(){
        return instagramApp;
    }

    public String getName(){
        return instagramApp.getName();
    }

    public String getUserName(){
        return instagramApp.getUserName();
    }

    public String getId(){
        return instagramApp.getId();
    }

    public String getUserPicture(){
        return instagramApp.getUserPicture();
    }

    public interface IInstagram{
        void onInstagramSuccess();
        void onInstagramFail(String error);
    }
}
