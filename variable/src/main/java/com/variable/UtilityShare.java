package com.variable;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.plus.PlusShare;
import com.log.Logger;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.sina.weibo.sdk.net.AsyncWeiboRunner.*;

/**
 * Created by star on 2017/11/17.
 */

public class UtilityShare {
    private static final String TAG = UtilityShare.class.getSimpleName();
    private final String MARKET = "market://details?id=";
    public static final String TYPE_TEXT = "text/plain";
    public static final String TYPE_IMAGE = "image/*";

    private final String Facebook = "com.facebook.katana";
    private final String Twitter = "com.twitter.android";
    private final String Twitter_Composer = "com.twitter.composer.ComposerShareActivity";
    private final String Instagram = "com.instagram.android";
    private final String Pinterest = "com.pinterest";
    private final String Pinterest_Des = "com.pinterest.EXTRA_DESCRIPTION";
    private final String GAMIL = "com.google.android.gm";
    private final String Google_Plus = "com.google.android.apps.plus";
    private final String WhatsApp = "com.whatsapp";
    private final String Skype = "com.skype.raider";
    private final String Telegram = "org.telegram.messenger";
    private final String Line = "jp.naver.line.android";
    private final String Line_Chat = "jp.naver.line.android.activity.selectchat.SelectChatActivity";
    private final String WeChat = "com.tencent.mm";
    private final String WeChat_Select = "com.tencent.mm.ui.tools.ShareImgUI";
    private final String Weibo = "com.weico.international";


    private volatile static UtilityShare u;
    public UtilityShare(){}

    public static UtilityShare getNewInstance() {
        if(u == null) {
            synchronized (UtilityShare.class) {
                if(u == null) {
                    u = new UtilityShare();
                }
            }
        }
        return u;
    }

    /**
     * 分享到facebook
     * @param activity
     * @param title
     * @param message
     */
    public void shareToFB(Activity activity, String title, String message){
        if(TextUtils.isEmpty(title) && TextUtils.isEmpty(message)){
            return;
        }

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setQuote(TextUtils.isEmpty(title)?"":title)
                .setContentUrl(Uri.parse(TextUtils.isEmpty(message)?"":message))
                .build();
        ShareDialog.show(activity, content);
//        try{
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType(TYPE_TEXT);
//            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            shareIntent.setPackage(Facebook);
//            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
//            activity.startActivity(shareIntent);
//        }catch (Exception e){
//            Logger.e(e.getMessage());
//            download(activity, Facebook);
//        }
    }

    /**
     * 分享到line
     * @param activity
     * @param title
     * @param message
     */
    public void shareToLine(Activity activity, String title, String message){
        if(TextUtils.isEmpty(title) && TextUtils.isEmpty(message)){
            return;
        }

        try{
            //跳到指定APP的Activity
            ComponentName cn = new ComponentName(Line, Line_Chat);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType(TYPE_TEXT);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setPackage(Line);
            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(message)==false){
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            }else if(TextUtils.isEmpty(title)==false && TextUtils.isEmpty(message)==false){
                shareIntent.putExtra(Intent.EXTRA_TEXT, title+"\n"+message);
            }else if(TextUtils.isEmpty(title)==false && TextUtils.isEmpty(message)){
                shareIntent.putExtra(Intent.EXTRA_TEXT, title);
            }
            shareIntent.setComponent(cn);
            activity.startActivity(shareIntent);
        }catch (Exception e){
            Logger.e(e.getMessage());
            download(activity, Line);
        }
    }

    /**
     * 分享到twitter
     * @param activity
     * @param title
     * @param message
     */
    public void shareToTwitter(Activity activity, String title, String message){
        if(TextUtils.isEmpty(title) && TextUtils.isEmpty(message)){
            return;
        }

        TweetComposer.Builder builder = new TweetComposer.Builder(activity);
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(message)==false){
            builder.text(message);
        }else if(TextUtils.isEmpty(title)==false && TextUtils.isEmpty(message)==false){
            builder.text(title+"\n"+message);
        }else if(TextUtils.isEmpty(title)==false && TextUtils.isEmpty(message)){
            builder.text(title);
        }
        builder.show();
    }

    /**
     * 分享到instagram
     * @param activity
     * @param uri
     */
    public void shareToInstagram(Activity activity, String uri){
        try{
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType(TYPE_IMAGE);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setPackage(Instagram);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            activity.startActivity(shareIntent);
        }catch (Exception e){
            download(activity, Instagram);
        }
    }

    /**
     * 分享到pinterest
     * @param activity
     * @param message
     * @param uri
     */
    public void shareToPinterest(Activity activity, String message, String uri){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType(TYPE_TEXT);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setPackage(Pinterest);
            shareIntent.putExtra(Pinterest_Des, message);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType(TYPE_IMAGE);
            activity.startActivity(shareIntent);
        }catch (Exception e){
            Logger.e(e.getMessage());
            download(activity, Pinterest);
        }
    }

    /**
     * 分享到wechat
     * @param activity
     * @param title
     * @param message
     */
    public void shareToWeChat(Activity activity, String title, String message){
        if(TextUtils.isEmpty(title) && TextUtils.isEmpty(message)){
            return;
        }

        try{
            ComponentName cn = new ComponentName(WeChat, WeChat_Select);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType(TYPE_TEXT);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setPackage(WeChat);
            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(message)==false){
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            }else if(TextUtils.isEmpty(title)==false && TextUtils.isEmpty(message)==false){
                shareIntent.putExtra(Intent.EXTRA_TEXT, title+"\n"+message);
            }else if(TextUtils.isEmpty(title)==false && TextUtils.isEmpty(message)){
                shareIntent.putExtra(Intent.EXTRA_TEXT, title);
            }
            shareIntent.setComponent(cn);
            activity.startActivity(shareIntent);
        }catch (Exception e){
            Logger.e(e.getMessage());
            download(activity, WeChat);
        }
    }

    /**
     * 分享到google+
     * @param activity
     * @param title
     * @param message
     */
    public void shareToGooglePlus(Activity activity, String title, String message){
        if(TextUtils.isEmpty(title) && TextUtils.isEmpty(message)){
            return;
        }

        // Launch the Google+ share dialog with attribution to your app.
        Intent shareIntent = new PlusShare.Builder(activity)
                .setType(TYPE_TEXT)
                .setText(TextUtils.isEmpty(title)?"":title)
                .setContentUrl(Uri.parse(TextUtils.isEmpty(message)?"":message))
                .getIntent();
        activity.startActivityForResult(shareIntent, 0);
    }

    /**
     * 分享到whatsapp
     * @param activity
     * @param title
     * @param message
     */
    public void shareToWhatsApp(Activity activity, String title, String message){
        if(TextUtils.isEmpty(title) && TextUtils.isEmpty(message)){
            return;
        }

        try{
            //跳到指定APP的Activity
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType(TYPE_TEXT);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setPackage(WhatsApp);
            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(message)==false){
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            }else if(TextUtils.isEmpty(title)==false && TextUtils.isEmpty(message)==false){
                shareIntent.putExtra(Intent.EXTRA_TEXT, title+"\n"+message);
            }else if(TextUtils.isEmpty(title)==false && TextUtils.isEmpty(message)){
                shareIntent.putExtra(Intent.EXTRA_TEXT, title);
            }
            activity.startActivity(shareIntent);
        }catch (Exception e){
            Logger.e(e.getMessage());
            download(activity, WhatsApp);
        }
    }

    /**
     * 分享到weibo
     * @param activity
     * @param title
     * @param message
     */
    public void shareToWeibo(Activity activity, String title, String message){
        if(TextUtils.isEmpty(title) && TextUtils.isEmpty(message)){
            return;
        }

        try{
            //跳到指定APP的Activity
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType(TYPE_TEXT);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setPackage(Weibo);
            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(message)==false){
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            }else if(TextUtils.isEmpty(title)==false && TextUtils.isEmpty(message)==false){
                shareIntent.putExtra(Intent.EXTRA_TEXT, title+"\n"+message);
            }else if(TextUtils.isEmpty(title)==false && TextUtils.isEmpty(message)){
                shareIntent.putExtra(Intent.EXTRA_TEXT, title);
            }
            activity.startActivity(shareIntent);
        }catch (Exception e){
            Logger.e(e.getMessage());
            download(activity, Weibo);
        }
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
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.setType(mineType);

        final PackageManager pm = activity.getPackageManager();
        final List<ResolveInfo> resInfo = pm.queryIntentActivities(shareIntent, 0);

        ArrayList<String> packageAllowed = new ArrayList();
        packageAllowed.add(Twitter);
        packageAllowed.add(Facebook);
        packageAllowed.add(GAMIL);
        packageAllowed.add(Google_Plus);
        packageAllowed.add(WhatsApp);
        packageAllowed.add(Skype);
        packageAllowed.add(Telegram);
        packageAllowed.add(Line);
        packageAllowed.add(Instagram);
        packageAllowed.add(Pinterest);
        packageAllowed.add(Weibo);
        String sms = getDefaultSmsAppPackageName(activity);
        if(sms!=null){
            packageAllowed.add(getDefaultSmsAppPackageName(activity));
        }

        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo resolveInfo : resInfo) {
                String packageName = resolveInfo.activityInfo.packageName;

                if(packageAllowed.contains(packageName)){
                    Intent intent=new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType(mineType);
                    if(TextUtils.isEmpty(message)==false){
                        intent.putExtra(Intent.EXTRA_TEXT, message);
                    }
                    if(TextUtils.isEmpty(subject)==false){
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    }
                    if(uri!=null){
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                    }
                    intent.setComponent(new ComponentName(packageName, resolveInfo.activityInfo.name));
                    intent.setPackage(packageName);
                    targetedShareIntents.add(intent);
                }
            }

            int size = targetedShareIntents.size();
            Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(size-1), chooserTitle);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
            activity.startActivity(chooserIntent);
        }
    }

    private void download(Activity activity, String packagename) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(MARKET+packagename));
        activity.startActivity(intent);
    }

    /**
     * get sms package name
     * @param context
     * @return
     */
    @Nullable
    private String getDefaultSmsAppPackageName(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            return Telephony.Sms.getDefaultSmsPackage(context);
        else {
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_DEFAULT).setType("vnd.android-dir/mms-sms");
            final List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);
            if (resolveInfos != null && !resolveInfos.isEmpty())
                return resolveInfos.get(0).activityInfo.packageName;
            return null;
        }
    }
}
