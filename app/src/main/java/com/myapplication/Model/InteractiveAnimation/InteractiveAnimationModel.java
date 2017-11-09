package com.myapplication.Model.InteractiveAnimation;

import android.app.Activity;

import com.myapplication.Presenter.InteractiveAnimation.IInteractiveAnimationPresenter;
import com.myapplication.Presenter.InteractiveAnimation.InteractiveAnimationPresenter;

/**
 * Created by star on 2017/11/9.
 */

public class InteractiveAnimationModel {
    private Activity activity;
    private IInteractiveAnimationPresenter iInteractiveAnimationPresenter;

    public InteractiveAnimationModel(Activity activity, IInteractiveAnimationPresenter iInteractiveAnimationPresenter) {
        this.activity = activity;
        this.iInteractiveAnimationPresenter = iInteractiveAnimationPresenter;
    }

    public void sendInteractiveAnimation(String userId) {
        //TODO send to server
    }

    /**
     * 接收server回傳的訊息
     * @param data
     */
    public void onReceive(String data){
        iInteractiveAnimationPresenter.onReceive(data);
    }
}
