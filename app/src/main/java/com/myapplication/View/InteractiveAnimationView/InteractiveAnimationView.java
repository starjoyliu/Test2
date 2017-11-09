package com.myapplication.View.InteractiveAnimationView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myapplication.Presenter.HighlightOuterFrame.HighlightOuterFramePresenter;
import com.myapplication.Presenter.InteractiveAnimation.InteractiveAnimationPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.variable.UtilityToast;

/**
 * Created by star on 2017/11/9.
 */

public class InteractiveAnimationView extends BaseActivity implements View.OnClickListener, IInteractiveAnimationView{
    private final String TAG = InteractiveAnimationView.class.getSimpleName();

    private InteractiveAnimationPresenter interactiveAnimationPresenter;
    private UtilityToast utilityToast;
    private Button btnPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_animation);

        btnPush = findViewById(R.id.btnPush);

        interactiveAnimationPresenter = new InteractiveAnimationPresenter(activity,this);
        utilityToast = UtilityToast.getNewInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();
        btnPush.setOnClickListener(this);
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
            case R.id.btnPush:
                interactiveAnimationPresenter.sendInteractiveAnimation("star");
                break;
        }
    }

    @Override
    public void refreshUI() {
        // 刷新畫面
    }
}
