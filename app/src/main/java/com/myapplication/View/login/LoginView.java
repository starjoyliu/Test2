package com.myapplication.View.login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.login.LoginResult;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.log.Logger;
import com.myapplication.Presenter.login.LoginPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.myapplication.View.speech2text.Speech2TextActivity;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.variable.UtilityKeyboard;
import com.variable.UtilityRes;
import com.variable.UtilitySwitchActivity;
import com.variable.UtilityToast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by star on 2017/11/10.
 */

public class LoginView extends BaseActivity implements ILoginView, View.OnClickListener{
    private final String TAG = Speech2TextActivity.class.getSimpleName();

    private LoginPresenter loginPresenter;
    private EditText etEmail, etPassword;
    private Button btnLogin, btnSignUp, loginFBButton
            , loginLineButton, loginInstagramButton;
    private TwitterLoginButton loginTwitterButton;

    private UtilityKeyboard utilityKeyboard;
    private UtilityToast utilityToast;
    private UtilitySwitchActivity utilitySwitchActivity;
    private UtilityRes utilityRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.myapplication",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Logger.d("KeyHash:"+Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        etEmail = findViewById(R.id.editText4);
        etPassword = findViewById(R.id.editText5);
        btnLogin = findViewById(R.id.loginview_btn_login);
        btnSignUp = findViewById(R.id.loginview_btn_sign_up);

        //FB
        loginFBButton = findViewById(R.id.login_button);
        loginFBButton.setBackgroundResource(R.drawable.icon_fb);

        // LINE
        loginLineButton = findViewById(R.id.line_login_button);

        // Instagram
        loginInstagramButton = findViewById(R.id.instagram_login_button);

        // Twitter
        loginTwitterButton = findViewById(R.id.twitter_login_button);

        loginPresenter = new LoginPresenter(activity, this);
        utilityKeyboard = UtilityKeyboard.getNewInstance();
        utilityToast = UtilityToast.getNewInstance();
        utilitySwitchActivity = UtilitySwitchActivity.getNewInstance();
        utilityRes = UtilityRes.getNewInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        //FB
        loginFBButton.setOnClickListener(this);

        //LINE
        loginLineButton.setOnClickListener(this);
        loginPresenter.initLine();

        //Instagram
        loginInstagramButton.setOnClickListener(this);

        //Twitter
        loginTwitterButton.setOnClickListener(this);
        loginPresenter.clickTwitterLogin(loginTwitterButton);

        loginPresenter.login();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // FB
        if(loginPresenter.getCallbackManagerFB()!=null){
            loginPresenter.getCallbackManagerFB().onActivityResult(requestCode, resultCode, data);
        }
        // Twitter Pass the activity result to the login button.
        loginTwitterButton.onActivityResult(requestCode, resultCode, data);

        // LINE
        if(requestCode == LoginPresenter.LINE_REQUEST_CODE){
            LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);
            switch (result.getResponseCode()) {
                case SUCCESS:
                    // Login successful
                    loginPresenter.saveLineInfo(result);
                    break;
                case CANCEL:
                    // Login canceled by user
                    Logger.e("ERROR: LINE Login Canceled by user!!");
                    break;
                default:
                    // Login canceled due to other error
                    Logger.e("ERROR: Login FAILED!");
                    Logger.e("ERROR: " + result.getErrorData().toString());
                    break;
            }
        }
    }

    @Override
    public void logIn(String email) {
        etEmail.setText(email);
        etPassword.requestFocus();
        utilityKeyboard.showKeyboard(activity, etPassword);
    }

    @Override
    public void errorMessage(String msg) {
        utilityToast.show(activity, msg);
    }

    @Override
    public void showMessage(String msg) {
        utilityToast.show(activity, msg);
    }

    @Override
    public void onInstagramFail(String error) {
        utilityToast.show(activity, error);
    }

    @Override
    public void onInstagramSuccess() {
        utilityToast.show(activity, utilityRes.getString(activity, R.string.instagram_success));
    }

    @Override
    public void TwitterRequestEmailFailure(String exception) {
        // Do something on failure
        Logger.w(exception);
    }

    @Override
    public void TwitterFail(String exception) {
        // Do something on failure
        Logger.w(exception);
    }

    @Override
    public void TwitterSuccess() {
        utilityToast.show(activity, utilityRes.getString(activity, R.string.twitter_success));
    }

    @Override
    public void onFBCancel() {

    }

    @Override
    public void onFBError(String error) {
        Logger.w(error);
    }

    @Override
    public void FBSuccess() {

    }

    @Override
    public void FBFail(String message) {
        Logger.w(message);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.loginview_btn_login:
                //login click
                loginPresenter.clickLogin(etEmail, etPassword);
                break;
            case R.id.loginview_btn_sign_up:
                //sign up click
                loginPresenter.clickSignUp();
                break;
            case R.id.login_button:
                //FB
                loginPresenter.clickFBLogin();
                break;
            case R.id.line_login_button:
                //LINE
                loginPresenter.clickLineLogin();
                break;
            case R.id.instagram_login_button:
                //instagram
                loginPresenter.clickInstagramLogin();
                break;
        }
    }
}
