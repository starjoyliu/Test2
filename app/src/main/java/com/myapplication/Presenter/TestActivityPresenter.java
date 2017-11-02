package com.myapplication.Presenter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.myapplication.Database.DBTest;
import com.myapplication.Model.TestActivityModel;
import com.myapplication.R;
import com.myapplication.View.test.ITestActivity;

/**
 * Created by star on 2017/11/1.
 */

public class TestActivityPresenter {
    private ITestActivity iTestActivity;
    private TestActivityModel testActivityModel;
    private Bundle data;
    private Activity activity;

    public TestActivityPresenter(Activity activity, ITestActivity iTestActivity){
        this.activity = activity;
        this.iTestActivity = iTestActivity;

        testActivityModel = new TestActivityModel();
    }

    public void load(){
        DBTest dbTest = testActivityModel.load();
        if(dbTest!=null){
            iTestActivity.setEtName(dbTest.getName());
            iTestActivity.setEtPhone(dbTest.getPhone());
            iTestActivity.msg(activity.getResources().getString(R.string.activity_load_user_successfully));
        }else{
            iTestActivity.setEtName("");
            iTestActivity.setEtPhone("");
            iTestActivity.msg(activity.getResources().getString(R.string.activity_no_user_data));
        }
    }

    public void update(String name, String phone){
        if(TextUtils.isEmpty(name) || name.equals("")){
            iTestActivity.msg(activity.getResources().getString(R.string.activity_et_name));
            return;
        }
        if(TextUtils.isEmpty(phone) || phone.equals("")){
            iTestActivity.msg(activity.getResources().getString(R.string.activity_et_phone));
            return;
        }

        boolean bUpdate = testActivityModel.update(name, phone);
        if (bUpdate){
            iTestActivity.msg(activity.getResources().getString(R.string.activity_update_user_successfully));
        }else{
            iTestActivity.msg(activity.getResources().getString(R.string.activity_no_user_data));
        }
    }
}
