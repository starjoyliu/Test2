package com.myapplication.Model.ShareView;

import android.app.Activity;

import com.myapplication.Presenter.ShareView.IShareViewPresenter;
import com.myapplication.Presenter.ShareView.ShareViewPresenter;

/**
 * Created by star on 2017/11/17.
 */

public class ShareViewModel {
    private Activity activity;
    private IShareViewPresenter iShareViewPresenter;

    public ShareViewModel(Activity activity, IShareViewPresenter iShareViewPresenter) {
        this.activity = activity;
        this.iShareViewPresenter = iShareViewPresenter;
    }
}
