package com.myapplication.Presenter.ShareView;

import android.app.Activity;
import android.net.Uri;

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

    /**
     * 利用內建chooser
     * @param activity
     * @param chooserTitle
     * @param subject
     * @param message
     * @param uri
     * @param mineType {@value TYPE_TEXT} {@value TYPE_IMAGE}
     */
    public void shareChooser(Activity activity, String chooserTitle, String subject, String message
            , Uri uri, String mineType){
        UtilityShare.getNewInstance().shareChooser(activity, chooserTitle, subject, message
                , uri, mineType);
    }
}
