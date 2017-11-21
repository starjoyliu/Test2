package com.variable;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import jp.wasabeef.blurry.Blurry;

/**
 * Blur Options:
 * * Radius
 * * Down Sampling
 * * Color Filter
 * * Asynchronous Support
 * * Animation (Overlay Only)
 *
 * sample code:
 * * Blurry.with(context)
 * * .radius(10)
 * * .sampling(8)
 * * .color(Color.argb(66, 255, 255, 0))
 * * .async()
 * * .animate(500)
 * * .onto(rootView);
 *
 * Created by starliu on 2017/11/21.
 */
public class UtilityBlur {
    private final int DEFAULT_SAMPLING = 2;

    private volatile static UtilityBlur u;
    public UtilityBlur(){}

    public static UtilityBlur getNewInstance() {
        if(u == null) {
            synchronized (UtilityBlur.class) {
                if(u == null) {
                    u = new UtilityBlur();
                }
            }
        }
        return u;
    }

    /**
     * blur view
     * @param activity
     * @param viewRoot
     * @param view
     * @param radius
     */
    public void blur(Activity activity, ViewGroup viewRoot, ImageView view, int radius){
        blur(activity, viewRoot, view, radius, DEFAULT_SAMPLING);

    }

    /**
     * blur view
     * @param activity
     * @param viewRoot
     * @param view
     * @param radius
     * @param sampling
     */
    public void blur(Activity activity, ViewGroup viewRoot, ImageView view, int radius, int sampling){
        // parent must be ViewGroup
        Blurry.with(activity).radius(radius).sampling(sampling).onto(viewRoot);
        // from View
        Blurry.with(activity).capture(view).into(view);

    }

    /**
     * blur bitmap
     * @param activity
     * @param viewRoot
     * @param view
     * @param bitmap
     * @param radius
     */
    public void blurBitmap(Activity activity, ViewGroup viewRoot, ImageView view, Bitmap bitmap, int radius){
        blurBitmap(activity, viewRoot, view, bitmap, radius, DEFAULT_SAMPLING);
    }

    /**
     * blur bitmap
     * @param activity
     * @param viewRoot
     * @param view
     * @param bitmap
     * @param radius
     * @param sampling
     */
    public void blurBitmap(Activity activity, ViewGroup viewRoot, ImageView view, Bitmap bitmap, int radius, int sampling){
        // parent must be ViewGroup
        Blurry.with(activity).radius(radius).sampling(sampling).onto(viewRoot);
        // from View
        Blurry.with(activity).from(bitmap).into(view);
    }
}
