package com.myapplication.View.floatingview;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import com.log.Logger;
import com.myapplication.Presenter.FloatingViewPresenter;
import com.myapplication.Presenter.PIPPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.myapplication.View.pip.PIPService;
import com.variable.UtilityPhone;
import com.variable.UtilityRes;
import com.variable.UtilityToast;

/**
 * Created by star on 2017/11/8.
 */

public class FloatingViewActivity extends BaseActivity implements View.OnClickListener, IFloatingViewActivity {
    private final String TAG = FloatingViewActivity.class.getSimpleName();

    private Button triggerFloatingWindows;
    private FloatingViewPresenter floatingViewPresenter;
    private UtilityToast utilityToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_view);

        triggerFloatingWindows = findViewById(R.id.triggerFloatingWindows);

        floatingViewPresenter = new FloatingViewPresenter(activity,this);
        utilityToast = UtilityToast.getNewInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        triggerFloatingWindows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingViewPresenter.triggerFloatingWindows();
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            default:
                Logger.v(TAG + " no match id");
                break;
        }
    }

    @Override
    public void triggerFloatingWindows() {
        startService(new Intent(FloatingViewActivity.this, FloatingViewService.class));
//        finish();
    }

    @Override
    public void showNeedAlertWindowsPermission(String msg) {
        utilityToast.show(activity, msg);
    }
}
