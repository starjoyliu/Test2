package com.myapplication.Presenter.login;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.log.Logger;
import com.myapplication.Model.login.LoginModel;
import com.myapplication.Object.CommonObject;
import com.myapplication.R;
import com.myapplication.View.login.ILoginView;
import com.myapplication.View.signup.SignUpView;
import com.myapplication.View.test.TestActivity;
import com.variable.UtilityKeyboard;
import com.variable.UtilityRes;
import com.variable.UtilitySwitchActivity;

/**
 * Created by star on 2017/11/10.
 */

public class LoginPresenter implements ILoginPresenter{
    private Activity activity;
    private ILoginView iLoginView;
    private LoginModel loginModel;
    private FirebaseAuth firebaseAuth;
    private UtilityRes utilityRes;
    private UtilityKeyboard utilityKeyboard;
    private UtilitySwitchActivity utilitySwitchActivity;

    public LoginPresenter(Activity activity, ILoginView iLoginView) {
        this.activity = activity;
        this.iLoginView = iLoginView;

        firebaseAuth = FirebaseAuth.getInstance();
        loginModel = new LoginModel(activity, this);

        utilityRes = UtilityRes.getNewInstance();
        utilityKeyboard = UtilityKeyboard.getNewInstance();
        utilitySwitchActivity = UtilitySwitchActivity.getNewInstance();
    }

    public void login(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user==null){
//            iLoginView.signUp();
        }else{
            iLoginView.logIn(user.getEmail());
        }
    }

    /**
     * 點擊登入的錯誤訊息
     * @param etEmail
     * @param etPassword
     */
    public void clickLogin(EditText etEmail, EditText etPassword) {
        utilityKeyboard.hiddenKeyboard(activity, etEmail);

        /**
         * 符合Email / password規則
         */
        if(TextUtils.isEmpty(etEmail.getText().toString())==false
                &&etEmail.getText().toString().contains("@")
                &&TextUtils.isEmpty(etPassword.getText().toString())==false
                &&etPassword.getText().toString().length()>= CommonObject.AT_LEAST_PASSWORD_LENGTH){
            loginModel.logIn(etEmail.getText().toString(), etPassword.getText().toString());
        }else{
            if(TextUtils.isEmpty(etEmail.getText().toString())){
                iLoginView.errorMessage(utilityRes.getString(activity, R.string.loginview_msg_email_is_empty));
            } else if(etEmail.getText().toString().contains("@")==false){
                iLoginView.errorMessage(utilityRes.getString(activity, R.string.loginview_msg_invalidation_email));
            } else if(TextUtils.isEmpty(etPassword.getText().toString())){
                iLoginView.errorMessage(utilityRes.getString(activity, R.string.loginview_msg_password_is_empty));
            } else if(etPassword.getText().toString().length()<CommonObject.AT_LEAST_PASSWORD_LENGTH){
                iLoginView.errorMessage(utilityRes.getString(activity, R.string.loginview_msg_invalidation_password));
            } else {
                Logger.e("無匹配的click login訊息");
            }
        }
    }

    @Override
    public void onReceive(String msg) {
        if(msg.equals("LOGIN_SUCCESS")){
            iLoginView.showMessage(utilityRes.getString(activity, R.string.loginview_msg_login_success));
            utilitySwitchActivity.switchActivity(activity, TestActivity.class);
        }else if(msg.equals("LOGIN_FAIL")){
            iLoginView.showMessage(utilityRes.getString(activity, R.string.loginview_msg_login_fail));
        }else{
            Logger.e("沒有匹配的訊息");
        }
    }

    public void clickSignUp() {
        utilitySwitchActivity.switchActivity(activity, SignUpView.class);
    }

    public String getP() {
        return loginModel.getP();
    }
}
