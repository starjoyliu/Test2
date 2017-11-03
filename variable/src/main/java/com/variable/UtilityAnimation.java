package com.variable;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by star on 2017/11/3.
 *
 * {@link Techniques}
 */
public class UtilityAnimation {
    private volatile static UtilityAnimation u;
    public UtilityAnimation(){}

    public static UtilityAnimation getNewInstance() {
        if(u == null) {
            synchronized (UtilityAnimation.class) {
                if(u == null) {
                    u = new UtilityAnimation();
                }
            }
        }
        return u;
    }
    
    public void FlipInX(View v){
        YoYo.with(Techniques.FlipInX)
                .playOn(v);
    }

    public void FlipOutX(View v){
        YoYo.with(Techniques.FlipOutX)
                .playOn(v);
    }
}
