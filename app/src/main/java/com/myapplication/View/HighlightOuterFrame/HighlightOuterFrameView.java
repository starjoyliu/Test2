package com.myapplication.View.HighlightOuterFrame;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myapplication.Presenter.HighlightOuterFrame.HighlightOuterFramePresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.variable.UtilityToast;

/**
 * Created by star on 2017/11/9.
 */

public class HighlightOuterFrameView extends BaseActivity implements IHighlightOuterFrameView, View.OnClickListener {
    private final String TAG = HighlightOuterFrameView.class.getSimpleName();

    private HighlightOuterFramePresenter highlightOuterFramePresenter;
    private UtilityToast utilityToast;
    private Button btnSend;
    private EditText et;
    private boolean hasText = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlight_outer_frame);

        et = findViewById(R.id.editText3);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        highlightOuterFramePresenter = new HighlightOuterFramePresenter(activity,this);
        utilityToast = UtilityToast.getNewInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();
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
        switch(id){
            case R.id.btnSend:
                if(et==null || TextUtils.isEmpty(et.getText().toString())){
                    //TODO 浪live設計為關閉輸入框
                }else{
                    highlightOuterFramePresenter.sendMessage(et.getText().toString());
                }
                break;
        }
    }

    @Override
    public void refreshUI() {
        // 刷新畫面
    }
}
