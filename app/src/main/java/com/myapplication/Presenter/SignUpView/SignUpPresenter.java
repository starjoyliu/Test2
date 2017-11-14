package com.myapplication.Presenter.SignUpView;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.log.Logger;
import com.myapplication.Model.SignUpModel.SignUpModel;
import com.myapplication.Object.CommonObject;
import com.myapplication.R;
import com.myapplication.View.login.LoginView;
import com.myapplication.View.signup.ISignUpView;
import com.myapplication.View.signup.SignUpView;
import com.myapplication.View.test.TestActivity;
import com.variable.UtilityKeyboard;
import com.variable.UtilityRes;
import com.variable.UtilitySwitchActivity;

/**
 * Created by star on 2017/11/13.
 */

public class SignUpPresenter implements ISignUpPresenter{
    private Activity activity;
    private ISignUpView iSignUpView;
    private SignUpModel signUpModel;
    private UtilitySwitchActivity utilitySwitchActivity;
    private UtilityKeyboard utilityKeyboard;
    private UtilityRes utilityRes;

    public SignUpPresenter(Activity activity, ISignUpView iSignUpView) {
        this.activity = activity;
        this.iSignUpView = iSignUpView;

        signUpModel = new SignUpModel(activity, this);

        utilitySwitchActivity = UtilitySwitchActivity.getNewInstance();
        utilityKeyboard = UtilityKeyboard.getNewInstance();
        utilityRes = UtilityRes.getNewInstance();
    }

    public void signUp(EditText etEmail, EditText etPassword) {
        utilityKeyboard.hiddenKeyboard(activity, etEmail);

        /**
         * 符合Email / password規則
         */
        if(TextUtils.isEmpty(etEmail.getText().toString())==false
                &&etEmail.getText().toString().contains("@")
                &&TextUtils.isEmpty(etPassword.getText().toString())==false
                &&etPassword.getText().toString().length()>=CommonObject.AT_LEAST_PASSWORD_LENGTH){
            signUpModel.sinUp(etEmail.getText().toString(), etPassword.getText().toString());
        }else{
            if(TextUtils.isEmpty(etEmail.getText().toString())){
                iSignUpView.errorMessage(utilityRes.getString(activity, R.string.loginview_msg_email_is_empty));
            } else if(etEmail.getText().toString().contains("@")==false){
                iSignUpView.errorMessage(utilityRes.getString(activity, R.string.loginview_msg_invalidation_email));
            } else if(TextUtils.isEmpty(etPassword.getText().toString())){
                iSignUpView.errorMessage(utilityRes.getString(activity, R.string.loginview_msg_password_is_empty));
            } else if(etPassword.getText().toString().length()< CommonObject.AT_LEAST_PASSWORD_LENGTH){
                iSignUpView.errorMessage(utilityRes.getString(activity, R.string.loginview_msg_invalidation_password));
            } else {
                Logger.e("無匹配的click sin up");
            }
        }
    }

    public void clickBack() {
        utilitySwitchActivity.switchActivity(activity, LoginView.class);
    }

    @Override
    public void onReceive(String msg) {
        if(msg.equals("SIGN_UP_SUCCESS")){
            iSignUpView.showMessage(utilityRes.getString(activity, R.string.signup_msg_signup_success));
            utilitySwitchActivity.switchActivity(activity, TestActivity.class);
        }else if(msg.equals("SIGN_UP_FAIL")){
            iSignUpView.showMessage(utilityRes.getString(activity, R.string.signup_msg_signup_fail));
        }else{
            iSignUpView.showMessage(msg);
        }
    }
}
