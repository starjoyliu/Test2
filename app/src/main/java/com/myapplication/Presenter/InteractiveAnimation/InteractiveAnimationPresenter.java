package com.myapplication.Presenter.InteractiveAnimation;

import android.app.Activity;

import com.myapplication.Model.InteractiveAnimation.InteractiveAnimationModel;
import com.myapplication.View.InteractiveAnimationView.IInteractiveAnimationView;

/**
 * Created by star on 2017/11/9.
 */

public class InteractiveAnimationPresenter implements IInteractiveAnimationPresenter{
    private Activity activity;
    private IInteractiveAnimationView iInteractiveAnimationView;
    private InteractiveAnimationModel interactiveAnimationModel;

    public InteractiveAnimationPresenter(Activity activity, IInteractiveAnimationView iInteractiveAnimationView) {
        this.activity = activity;
        this.iInteractiveAnimationView = iInteractiveAnimationView;

        interactiveAnimationModel = new InteractiveAnimationModel(activity, this);
    }

    public void sendInteractiveAnimation(String userId){
        interactiveAnimationModel.sendInteractiveAnimation(userId);
    }

    @Override
    public void onReceive(String data) {
        iInteractiveAnimationView.refreshUI();
    }
}
