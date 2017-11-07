package com.myapplication.Presenter;

import android.app.Activity;

import com.myapplication.Model.Speech2TextModel;
import com.myapplication.R;
import com.myapplication.View.speech2text.ISpeech2TextActivity;
import com.variable.UtilitySpeech;
import com.variable.custom_interface.IUtilitySpeech;

/**
 * Created by star on 2017/11/7.
 */

public class Speech2TextPresenter implements IUtilitySpeech {
    private ISpeech2TextActivity iSpeech2TextActivity;
    private Speech2TextModel speech2TextModel;
    private Activity activity;
    private UtilitySpeech utilitySpeech;

    public Speech2TextPresenter(Activity activity, ISpeech2TextActivity iSpeech2TextActivity) {
        this.activity = activity;
        this.iSpeech2TextActivity = iSpeech2TextActivity;
        utilitySpeech = UtilitySpeech.getNewInstance();

        speech2TextModel = new Speech2TextModel();
    }

    /**
     * Showing google speech input dialog
     *
     * PS: 注意加入onActivityResult(int requestCode, int resultCode, Intent data)
     */
    public void promptSpeechInput(){
        utilitySpeech.promptSpeechInput(activity
                , R.string.activity_speech_prompt
                , R.string.activity_speech_not_supported
                , this);
    }

    /**
     * 取得request code
     * @return
     */
    public int getRequestCode(){
        return utilitySpeech.REQ_CODE_SPEECH_INPUT;
    }


    @Override
    public void showNoSupportMsg(String msg) {
        iSpeech2TextActivity.showNoSupportMsg(msg);
    }
}
