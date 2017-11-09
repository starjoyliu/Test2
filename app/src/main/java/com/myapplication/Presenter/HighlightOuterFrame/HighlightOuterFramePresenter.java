package com.myapplication.Presenter.HighlightOuterFrame;

import android.app.Activity;

import com.myapplication.Model.HighlightOuterFrame.HighlightOuterFrameMode;
import com.myapplication.View.HighlightOuterFrame.IHighlightOuterFrameView;

/**
 * Created by star on 2017/11/9.
 */

public class HighlightOuterFramePresenter implements IHighlightOuterFramePresenter{
    private Activity activity;
    private IHighlightOuterFrameView iHighlightOuterFrameView;
    private HighlightOuterFrameMode highlightOuterFrameMode;

    public HighlightOuterFramePresenter(Activity activity, IHighlightOuterFrameView iHighlightOuterFrameView) {
        this.activity = activity;
        this.iHighlightOuterFrameView = iHighlightOuterFrameView;

        highlightOuterFrameMode = new HighlightOuterFrameMode(activity, this);
    }

    public void sendMessage(String msg){
        highlightOuterFrameMode.sendMessage(msg);
    }

    @Override
    public void receiveData(String data) {
        //TODO decode data, notify view to refresh UI
        iHighlightOuterFrameView.refreshUI();
    }
}
