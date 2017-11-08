package com.myapplication.View.floatingview;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.log.Logger;
import com.myapplication.R;
import com.variable.UtilityPhone;
import com.variable.UtilityUI;

/**
 * Created by star on 2017/11/8.
 */

public class FloatingViewService extends Service implements View.OnClickListener {
    private WindowManager mWindowManager;
    private View mFloatingView;
    private View collapsedView;
    private View expandedView;
    private UtilityPhone utilityPhone;

    public FloatingViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        utilityPhone = UtilityPhone.getNewInstance();
        //getting the widget layout from xml using layout inflater
        mFloatingView = UtilityUI.getNewInstance().getLayoutView(this, R.layout.service_floating_view);

        //setting the layout parameters
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.x = utilityPhone.WIDTH/2;


        //getting windows services and adding the floating view to it
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);


        //getting the collapsed and expanded view from the floating view
        collapsedView = mFloatingView.findViewById(R.id.layoutCollapsed);

        expandedView = mFloatingView.findViewById(R.id.layoutExpanded);
        expandedView.setOnClickListener(this);

        mFloatingView.findViewById(R.id.buttonClose).setOnClickListener(this);

        //adding an touchlistener to make drag movement of the floating widget
        mFloatingView.findViewById(R.id.relativeLayoutParent).setOnTouchListener(new View.OnTouchListener() {
            private final int TAP_THRESHOLD = 20;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            private boolean isTap;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //default
                        isTap = true;

                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        //when the drag is ended switching the state of the widget
                        if(isTap){
                            collapsedView.setVisibility(View.GONE);
                            expandedView.setVisibility(View.VISIBLE);
                        }else{
                            //移動超過中線靠左邊, 自動貼齊左邊
                            if(params.x<=0){
                                params.x = -utilityPhone.WIDTH/2;
                            }else{
                                //反之, 貼齊右邊
                                params.x = utilityPhone.WIDTH/2;
                            }
                            mWindowManager.updateViewLayout(mFloatingView, params);
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //this code is helping the widget to move around the screen with fingers
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingView, params);

                        //左右移動超過容忍值,
                        if(Math.abs(initialX-params.x)>=TAP_THRESHOLD){
                            isTap = false;
                        }else{
                            if(!isTap){
                                //已經左右移動過, 不觸發Tap
                            }else{
                                isTap = true;
                            }
                        }
                        Logger.d("params.x:"+params.x +" params.y:"+params.y);
                        return true;
                }
                return false;
            }


        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutExpanded:
                //switching views
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
                break;
            case R.id.buttonClose:
                //closing the widget
                stopSelf();
                break;
        }
    }
}
