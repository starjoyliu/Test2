package com.variable;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

/**
 * Created by star on 2017/11/1.
 */

public class UtilityToast {
    private volatile static UtilityToast u;
    private final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private final int DEFAULT_BG_COLOR = Color.DKGRAY;
    private final int DEFAULT_ICON = -999;
    private final int DEFAULT_DURATION = Toast.LENGTH_SHORT;
    /**
     * 0 is rectangle
     * 99 is radius
     */
    private final int DEFAULT_CORNER_RADIUS = 99;

    public UtilityToast (){}

    public static UtilityToast getNewInstance() {
        if(u == null) {
            synchronized (UtilityToast.class) {
                if(u == null) {
                    u = new UtilityToast();
                }
            }
        }
        return u;
    }

    public void show(Activity context, String msg){
        show(context, msg, DEFAULT_TEXT_COLOR, DEFAULT_BG_COLOR);
    }

    public void show(Activity context, String msg, int textColor, int bgColor){
        show(context, msg, textColor, bgColor, DEFAULT_ICON);
    }

    public void show(Activity context, String msg, int textColor, int bgColor, int icon){
        show(context, msg, textColor, bgColor, icon, DEFAULT_CORNER_RADIUS);
    }

    public void show(Activity context, String msg, int textColor, int bgColor, int icon, int cornerRadius){
        show(context, msg, textColor, bgColor, icon, cornerRadius, DEFAULT_DURATION);
    }

    public void show(final Activity context, final String msg, final int textColor, final int bgColor, final int icon, final int cornerRadius, final int duration){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StyleableToast st = new StyleableToast
                        .Builder(context)
                        .text(msg)
                        .textColor(textColor)
                        .cornerRadius(cornerRadius)
                        .backgroundColor(bgColor)
                        .icon(icon)
                        .duration(duration)
                        .build();
                st.show();
            }
        });
    }
}
