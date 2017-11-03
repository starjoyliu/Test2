package com.variable;

import android.app.Activity;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

/**
 * Created by star on 2017/11/2.
 */

public class UtilityDialog {
    private final @DrawableRes Integer DEFAULT_ICON = -999;
    private final @ColorRes int DEFAULT_HEADER_COLOR = -999;
    private final @StringRes int DEFAULT_NEG_TEXT = -999;
    private final @StringRes int DEFAULT_TITLE_TEXT = -999;
    private final @IntegerRes int DEFAULT_PADDING = -999;

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
     * @param activity
     * @param title
     * @param content
     * @param iconRes
     * @param headerColor
     * @param customView
     */
    public void showCUSTOM(Activity activity, @StringRes int title, @StringRes int content
            , @DrawableRes Integer iconRes, @ColorRes int headerColor
            , View customView){
        showCUSTOM(activity, title, content, iconRes, headerColor, customView
        , DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING);
    }

    /**
     * 顯示自訂View Dialog, 自訂icon和header背景, 自訂padding
     * @param activity
     * @param title
     * @param content
     * @param iconRes
     * @param headerColor
     * @param customView
     * @param paddingLeft
     * @param paddingTop
     * @param paddingRight
     * @param paddingBottom
     */
    public void showCUSTOM(Activity activity, @StringRes int title, @StringRes int content
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

        dialogShow(activity, dialog);
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
}
