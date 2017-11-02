package com.variable;

import android.app.Activity;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

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
     *
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
     *
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

    public void show(Activity activity, @StringRes int title, @StringRes int content
            , @StringRes int posText, MaterialDialog.SingleButtonCallback posCallback
            , @StringRes int negText, MaterialDialog.SingleButtonCallback negCallback){
        show(activity, title, content, DEFAULT_ICON, DEFAULT_HEADER_COLOR, posText, posCallback, negText, negCallback);
    }

    /**
     *
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

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    /**
     *
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
     *
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

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }
}
