package com.myapplication.Model.HighlightOuterFrame;

import android.app.Activity;

import com.myapplication.Presenter.HighlightOuterFrame.IHighlightOuterFramePresenter;

/**
 * Created by star on 2017/11/9.
 */

public class HighlightOuterFrameMode {
    private Activity activity;
    private IHighlightOuterFramePresenter iHighlightOuterFramePresenter;

    public HighlightOuterFrameMode(Activity activity, IHighlightOuterFramePresenter iHighlightOuterFramePresenter) {
        this.activity = activity;
        this.iHighlightOuterFramePresenter = iHighlightOuterFramePresenter;
    }

    public void sendMessage(String msg) {
        //TODO 傳給server
    }

    /**
     * 處理 server 回的資料
     * @param data
     */
    public void onReceive(String data){
        iHighlightOuterFramePresenter.receiveData(data);
    }
}
