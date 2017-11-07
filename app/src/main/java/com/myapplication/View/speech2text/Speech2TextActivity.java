package com.myapplication.View.speech2text;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.log.Logger;
import com.myapplication.Presenter.Speech2TextPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.variable.UtilityToast;

import java.util.ArrayList;

/**
 * Created by star on 2017/11/7.
 */

public class Speech2TextActivity extends BaseActivity implements ISpeech2TextActivity{
    private final String TAG = Speech2TextActivity.class.getSimpleName();
    private int REQUEST_CODE_SPEECH;
    private Button btnSpeech;
    private Speech2TextPresenter speech2TextPresenter;
    private TextView showText;
    private UtilityToast utilityToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        btnSpeech = findViewById(R.id.btn_speech);
        showText = findViewById(R.id.showText);

        speech2TextPresenter = new Speech2TextPresenter(activity, this);
        utilityToast = UtilityToast.getNewInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        REQUEST_CODE_SPEECH = speech2TextPresenter.getRequestCode();

        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speech2TextPresenter.promptSpeechInput();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == REQUEST_CODE_SPEECH){
                if(null != data){
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result!=null && result.size()>0){
                        showSpeechText(result.get(0));
                    }else{
                        Logger.v(TAG + " no data");
                    }
                }
            }else{
                Logger.v(TAG + " no match requestCode:" + requestCode);
            }
    }

    @Override
    public void showSpeechText(String msg) {
        showText.setText(msg);
    }

    @Override
    public void showNoSupportMsg(String msg) {
        utilityToast.show(activity, msg);
    }
}
