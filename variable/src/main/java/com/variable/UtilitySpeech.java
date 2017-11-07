package com.variable;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.variable.custom_interface.IUtilitySpeech;

import java.util.Locale;

/**
 * Created by star on 2017/11/7.
 */

public class UtilitySpeech{
    private static final String TAG = UtilitySpeech.class.getSimpleName();
    public final int REQ_CODE_SPEECH_INPUT = 1000;

    private volatile static UtilitySpeech u;
    public UtilitySpeech(){}

    public static UtilitySpeech getNewInstance() {
        if(u == null) {
            synchronized (UtilitySpeech.class) {
                if(u == null) {
                    u = new UtilitySpeech();
                }
            }
        }
        return u;
    }

    /**
     * Showing google speech input dialog
     * @param activity
     * @param resHint show hint text from value/strings.xml, -1 is null
     * @param noSupport show no support text from value/strings.xml, -1 is null
     * @param iUtilitySpeech Listener UtilitySpeech event, implements IUtilitySpeech
     */
    public void promptSpeechInput(Activity activity, int resHint, int noSupport, IUtilitySpeech iUtilitySpeech) {
        promptSpeechInput(activity, resHint, noSupport, REQ_CODE_SPEECH_INPUT, iUtilitySpeech);
    }

    /**
     * Showing google speech input dialog
     * @param activity
     * @param resHint show hint text from value/strings.xml, -1 is null
     * @param noSupport show no support text from value/strings.xml, -1 is null
     * @param requestCode onActivityResult return code
     * @param iUtilitySpeech Listener UtilitySpeech event, implements IUtilitySpeech
     */
    public void promptSpeechInput(Activity activity, int resHint, int noSupport, int requestCode, IUtilitySpeech iUtilitySpeech) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if(resHint!=-1){
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    UtilityRes.getNewInstance().getString(activity, resHint));
        }
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException a) {
            if(noSupport!=-1){
                if(iUtilitySpeech!=null){
                    iUtilitySpeech.showNoSupportMsg(UtilityRes.getNewInstance().getString(activity, noSupport));
                }
            }
        }
    }
}
