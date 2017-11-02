package com.myapplication.Presenter;

import android.os.Bundle;

import com.myapplication.Database.DBTest;
import com.myapplication.Model.TestActivityModel;
import com.myapplication.View.test.ITestActivity;

/**
 * Created by star on 2017/11/1.
 */

public class TestActivityPresenter {
    private ITestActivity iTestActivity;
    private TestActivityModel testActivityModel;
    private Bundle data;

    public TestActivityPresenter(ITestActivity iTestActivity){
        this.iTestActivity = iTestActivity;

        testActivityModel = new TestActivityModel();
    }

    public void load(){
        DBTest dbTest = testActivityModel.load();
        if(dbTest!=null){
            iTestActivity.setEtName(dbTest.getName());
            iTestActivity.setEtPhone(dbTest.getPhone());
            iTestActivity.msg("Load user successfully!");
        }else{
            iTestActivity.setEtName("");
            iTestActivity.setEtPhone("");
            iTestActivity.msg("No user data!");
        }
    }

    public void update(String name, String phone){
        boolean bUpdate = testActivityModel.update(name, phone);
        if (bUpdate){
            iTestActivity.msg("Update user successfully!");
        }else{
            iTestActivity.msg("No user data!");
        }
    }
}
