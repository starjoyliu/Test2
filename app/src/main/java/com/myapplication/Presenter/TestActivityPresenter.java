package com.myapplication.Presenter;

import android.os.Bundle;

import com.myapplication.Model.MTestActivity;
import com.myapplication.View.test.ITestActivity;

/**
 * Created by star on 2017/11/1.
 */

public class TestActivityPresenter {
    private ITestActivity iTestActivity;
    private MTestActivity mTestActivity;
    private Bundle data;

    public TestActivityPresenter(ITestActivity iTestActivity, Bundle data){
        this.iTestActivity = iTestActivity;
        this.data = data;

        mTestActivity = new MTestActivity();
        mTestActivity.setData(this.data);
    }

    public Bundle getData(){
        return mTestActivity.getData();
    }

    public String getButtonLoadText(){
        return getData().getString("load");
    }

    public void loadButtonText(){
        iTestActivity.setButtonLoadText(getButtonLoadText());
    }
}
