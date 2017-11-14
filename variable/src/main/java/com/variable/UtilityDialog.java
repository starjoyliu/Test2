package com.variable;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.variable.Custom.CustomDialog;

/**
 * Created by star on 2017/11/2.
 */

public class UtilityDialog {
    public static final @DrawableRes Integer DEFAULT_ICON = -999;
    public static final @ColorRes int DEFAULT_HEADER_COLOR = -999;
    public static final @StringRes int DEFAULT_NEG_TEXT = -999;
    public static final @StringRes int DEFAULT_TITLE_TEXT = -999;
    public static final @IntegerRes int DEFAULT_PADDING = -999;
    public static final int DEFAULT_ANIMATION = -999;
    public static final int DEFAULT_GRAVITY = -999;
    public static final boolean DEFAULT_WIDTH_FULL_SCREEN = false;

    private volatile static UtilityDialog u;

    public UtilityDialog (){}

    public static UtilityDialog getNewInstance() {
        if(u == null) {
            synchronized (UtilityDialog.class) {
                if(u == null) {
                    u = new UtilityDialog();
                }
            }
        }
        return u;
    }

    /**
     * 顯示基本Dialog
     * @param activity
     * @param title
     * @param content
     * @param posText
     * @param posCallback
     */
    public void show(Activity activity, @StringRes int title, @StringRes int content
            , @StringRes int posText, MaterialDialog.SingleButtonCallback posCallback){
        show(activity, title, content, DEFAULT_ICON, DEFAULT_HEADER_COLOR, posText, posCallback);
    }

    /**
     * 顯示Dialog, 自訂icon和title背景, 單按鈕
     * @param activity
     * @param title
     * @param content
     * @param iconRes
     * @param headerColor
     * @param posText
     * @param posCallback
     */
    public void show(Activity activity, @StringRes int title, @StringRes int content
            , @DrawableRes Integer iconRes, @ColorRes int headerColor
            , @StringRes int posText, MaterialDialog.SingleButtonCallback posCallback){
        show(activity, title, content, iconRes, headerColor, posText, posCallback, DEFAULT_NEG_TEXT, null);
    }

    /**
     * 顯示基本Dialog, 雙按鈕
     * @param activity
     * @param title
     * @param content
     * @param posText
     * @param posCallback
     * @param negText
     * @param negCallback
     */
    public void show(Activity activity, @StringRes int title, @StringRes int content
            , @StringRes int posText, MaterialDialog.SingleButtonCallback posCallback
            , @StringRes int negText, MaterialDialog.SingleButtonCallback negCallback){
        show(activity, title, content, DEFAULT_ICON, DEFAULT_HEADER_COLOR, posText, posCallback, negText, negCallback);
    }

    /**
     * 顯示Dialog, 自訂icon和header背景, 雙按鈕
     * @param activity
     * @param title
     * @param content
     * @param iconRes
     * @param headerColor
     * @param posText
     * @param posCallback
     * @param negText
     * @param negCallback
     */
    public void show(Activity activity, @StringRes int title, @StringRes int content
            , @DrawableRes Integer iconRes, @ColorRes int headerColor
            , @StringRes int posText, MaterialDialog.SingleButtonCallback posCallback
            , @StringRes int negText, MaterialDialog.SingleButtonCallback negCallback){

        final MaterialStyledDialog.Builder dialog = new MaterialStyledDialog.Builder(activity);
        dialog.setStyle(Style.HEADER_WITH_TITLE);
        dialog.setCancelable(true);
//        dialog.withDialogAnimation(true);
        dialog.setDescription(content);
        dialog.setPositiveText(posText);
        dialog.onPositive(posCallback);
        if (negText!=DEFAULT_NEG_TEXT && negCallback!=null){
            dialog.setNegativeText(negText);
            dialog.onNegative(negCallback);
        }

        if(title!=DEFAULT_TITLE_TEXT){
            dialog.setTitle(title);
        }

        if(iconRes!=DEFAULT_ICON){
            dialog.setStyle(Style.HEADER_WITH_ICON);
            dialog.setIcon(iconRes);
        }

        if(headerColor!=DEFAULT_HEADER_COLOR){
            dialog.setHeaderColor(headerColor);
        }

        dialogShow(activity, dialog);
    }

    /**
     * 顯示Dialog, 自訂header背景, 單按鈕
     * @param activity
     * @param title
     * @param content
     * @param headerColor
     * @param posText
     * @param posCallback
     */
    public void show(Activity activity, @StringRes int title, @StringRes int content, @ColorRes int headerColor
            , @StringRes int posText, MaterialDialog.SingleButtonCallback posCallback){
        show(activity, title, content, headerColor, posText, posCallback, DEFAULT_NEG_TEXT, null);
    }

    /**
     * 顯示Dialog, 自訂header背景, 雙按鈕
     * @param activity
     * @param title
     * @param content
     * @param headerColor
     * @param posText
     * @param posCallback
     * @param negText
     * @param negCallback
     */
    public void show(Activity activity, @StringRes int title, @StringRes int content, @ColorRes int headerColor
            , @StringRes int posText, MaterialDialog.SingleButtonCallback posCallback
            , @StringRes int negText, MaterialDialog.SingleButtonCallback negCallback){

        final MaterialStyledDialog.Builder dialog = new MaterialStyledDialog.Builder(activity);
        dialog.setDescription(content);
        dialog.setStyle(Style.HEADER_WITH_TITLE);
        dialog.setPositiveText(posText);
        dialog.onPositive(posCallback);

        if (negText!=DEFAULT_NEG_TEXT && negCallback!=null){
            dialog.setNegativeText(negText);
            dialog.onNegative(negCallback);
        }

        if(title!=DEFAULT_TITLE_TEXT){
            dialog.setTitle(title);
        }

        if(headerColor!=DEFAULT_HEADER_COLOR){
            dialog.setHeaderColor(headerColor);
        }

        dialogShow(activity, dialog);
    }

    /**
     * 顯示自訂View Dialog, 自訂icon和header背景
     * PS: 自己要call show, 否則不會顯示
     * @param activity
     * @param title
     * @param content
     * @param iconRes
     * @param headerColor
     * @param customView
     */
    public MaterialStyledDialog showCUSTOM(Activity activity, @StringRes int title, @StringRes int content
            , @DrawableRes Integer iconRes, @ColorRes int headerColor
            , View customView){
        return showCUSTOM(activity, title, content, iconRes, headerColor, customView
                , DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING);
    }

    /**
     * 顯示自訂View Dialog, 自訂icon和header背景, 自訂padding
     * PS: 自己要call show, 否則不會顯示
     * @param activity
     * @param title
     * @param content
     * @param iconRes
     * @param headerColor
     * @param customView
     * @param paddingLeft dp
     * @param paddingTop dp
     * @param paddingRight dp
     * @param paddingBottom dp
     */
    public MaterialStyledDialog showCUSTOM(Activity activity, @StringRes int title, @StringRes int content
            , @DrawableRes Integer iconRes, @ColorRes int headerColor
            , View customView
            , @IntegerRes int paddingLeft, @IntegerRes int paddingTop, @IntegerRes int paddingRight, @IntegerRes int paddingBottom){

        final MaterialStyledDialog.Builder dialog = new MaterialStyledDialog.Builder(activity);
        dialog.setDescription(content);
        dialog.setStyle(Style.HEADER_WITH_TITLE);

        if (paddingLeft != DEFAULT_PADDING
                ||paddingTop != DEFAULT_PADDING
                ||paddingRight != DEFAULT_PADDING
                ||paddingBottom != DEFAULT_PADDING){
            dialog.setCustomView(customView, paddingLeft, paddingTop, paddingRight, paddingBottom);
        }else{
            dialog.setCustomView(customView);
        }

        if(title!=DEFAULT_TITLE_TEXT){
            dialog.setTitle(title);
        }

        if(headerColor!=DEFAULT_HEADER_COLOR){
            dialog.setHeaderColor(headerColor);
        }

        if(iconRes!=DEFAULT_ICON){
            dialog.setStyle(Style.HEADER_WITH_ICON);
            dialog.setIcon(iconRes);
        }
        return dialog.build();
    }

    /**
     * private method, 顯示Dialog
     * @param activity
     * @param dialog
     */
    private void dialogShow(final Activity activity, final MaterialStyledDialog.Builder dialog){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    public MaterialStyledDialog showCUSTOM(Activity activity, @StringRes int title, @StringRes int content
            , @DrawableRes Integer iconRes, @ColorRes int headerColor
            , View customView
            , @IntegerRes int paddingLeft, @IntegerRes int paddingTop, @IntegerRes int paddingRight, @IntegerRes int paddingBottom
            , int animationRes){

        final MaterialStyledDialog.Builder dialog = new MaterialStyledDialog.Builder(activity);
        dialog.setDescription(content);
        dialog.setStyle(Style.HEADER_WITH_TITLE);

        if (paddingLeft != DEFAULT_PADDING
                ||paddingTop != DEFAULT_PADDING
                ||paddingRight != DEFAULT_PADDING
                ||paddingBottom != DEFAULT_PADDING){
            dialog.setCustomView(customView, paddingLeft, paddingTop, paddingRight, paddingBottom);
        }else{
            dialog.setCustomView(customView);
        }

        if(title!=DEFAULT_TITLE_TEXT){
            dialog.setTitle(title);
        }

        if(headerColor!=DEFAULT_HEADER_COLOR){
            dialog.setHeaderColor(headerColor);
        }

        if(iconRes!=DEFAULT_ICON){
            dialog.setStyle(Style.HEADER_WITH_ICON);
            dialog.setIcon(iconRes);
        }

        if(animationRes!=DEFAULT_ANIMATION){
            dialog.withDialogAnimation(true);
        }

        MaterialStyledDialog materialStyledDialog = dialog.build();
        materialStyledDialog.getWindow().getAttributes().windowAnimations = animationRes;
        return materialStyledDialog;
    }

    /**
     * 建立一個自訂View
     * @param activity
     * @param customView
     * @return
     */
    public Dialog buildCustomAnimationDialog(Activity activity
            , View customView) {
        return buildCustomAnimationDialog(activity, customView, DEFAULT_ANIMATION, DEFAULT_GRAVITY, DEFAULT_WIDTH_FULL_SCREEN);
    }

    /**
     * 建立一個自訂View
     * @param activity
     * @param customView
     * @param animationSource {@value DEFAULT_ANIMATION} 顯示動畫
     * @param gravity {@value DEFAULT_GRAVITY} 動畫顯示位置
     * @return
     */
    public Dialog buildCustomAnimationDialog(Activity activity
            , View customView
            , int animationSource
            , int gravity) {
        return buildCustomAnimationDialog(activity, customView, animationSource, gravity, DEFAULT_WIDTH_FULL_SCREEN);
    }

    /**
     * 建立一個自訂View
     * @param activity
     * @param customView
     * @param isWidthFullScreen {@value DEFAULT_WIDTH_FULL_SCREEN} 寬度是否滿版
     * @return
     */
    public Dialog buildCustomAnimationDialog(Activity activity
            , View customView
            , boolean isWidthFullScreen) {
        return buildCustomAnimationDialog(activity, customView, DEFAULT_ANIMATION, DEFAULT_GRAVITY, isWidthFullScreen);
    }

    /**
     * 建立一個自訂View
     * @param activity
     * @param customView
     * @param animationSource {@value DEFAULT_ANIMATION} 顯示動畫
     * @param gravity {@value DEFAULT_GRAVITY} 動畫顯示位置
     * @param isWidthFullScreen {@value DEFAULT_WIDTH_FULL_SCREEN} 寬度是否滿版
     * @return
     */
    public Dialog buildCustomAnimationDialog(Activity activity
            , View customView
            , int animationSource
            , int gravity
            , boolean isWidthFullScreen) {
        Dialog dialog;
        if(isWidthFullScreen){
            dialog = new CustomDialog(activity);
        }else{
            dialog = new Dialog(activity);
        }

        dialog.setContentView(customView);

        /**
         * 設定動畫
         */
        if(animationSource!=DEFAULT_ANIMATION){
            dialog.getWindow().getAttributes().windowAnimations = animationSource;
        }

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        /**
         * dialog靠的位置
         */
        if(gravity!=DEFAULT_GRAVITY){
            wlp.gravity = gravity;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        }
        if(isWidthFullScreen){
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        return dialog;
    }
}
