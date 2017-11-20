package com.myapplication.Presenter.ShareView;

import android.app.Activity;

import com.myapplication.Model.ShareView.ShareViewModel;
import com.myapplication.View.ShareView.IShareView;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.variable.UtilityShare;

/**
 * Created by star on 2017/11/17.
 */

public class ShareViewPresenter implements IShareViewPresenter{
    private Activity activity;
    private IShareView iShareView;

    private ShareViewModel model;

    public ShareViewPresenter(Activity activity, IShareView iShareView) {
        this.activity = activity;
        this.iShareView = iShareView;

        model = new ShareViewModel(activity, this);
    }

    public void goolgeShareClick(String title, String message){
        UtilityShare.getNewInstance().shareToGooglePlus(activity, title, message);
    }

    public void fbShareClick(String title, String message){
        UtilityShare.getNewInstance().shareToFB(activity, title, message);
    }

    public void lineShareClick(String title, String message){
        UtilityShare.getNewInstance().shareToLine(activity, title, message);
    }

    public void twitterShareClick(String title, String message){
        UtilityShare.getNewInstance().shareToTwitter(activity, title, message);
    }

    public void wechatShareClick(String title, String message){
        UtilityShare.getNewInstance().shareToWeChat(activity, title, message);
    }

    public void whatsappShareClick(String title, String message){
        UtilityShare.getNewInstance().shareToWhatsApp(activity, title, message);
    }

    public void weiboShareCliclk(String title, String message) {
        UtilityShare.getNewInstance().shareToWeibo(activity, title, message);
    }
}
