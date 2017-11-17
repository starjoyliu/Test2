package com.myapplication.Presenter.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.log.Logger;
import com.myapplication.Model.login.LoginModel;
import com.myapplication.Object.CommonObject;
import com.myapplication.R;
import com.myapplication.View.SignUp.SignUpView;
import com.myapplication.View.login.ILoginView;
import com.myapplication.View.test.TestActivity;
import com.myapplication.utility.UtilityLINE;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.variable.UtilityInstagram;
import com.variable.UtilityKeyboard;
import com.variable.UtilityRes;
import com.variable.UtilitySwitchActivity;

import java.util.Arrays;

/**
 * Created by star on 2017/11/10.
 */

public class LoginPresenter implements ILoginPresenter
        , FacebookCallback<LoginResult>
        , UtilityInstagram.IInstagram
        , UtilityLINE.ILINE{
    public static final int LINE_REQUEST_CODE = 1006;

    private Activity activity;
    private ILoginView iLoginView;
    private LoginModel loginModel;
    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManagerFB;
    private UtilityRes utilityRes;
    private UtilityKeyboard utilityKeyboard;
    private UtilitySwitchActivity utilitySwitchActivity;
    private UtilityInstagram utilityInstagram;
    private UtilityLINE utilityLINE;

    public LoginPresenter(Activity activity, ILoginView iLoginView) {
        this.activity = activity;
        this.iLoginView = iLoginView;

        firebaseAuth = FirebaseAuth.getInstance();
        loginModel = new LoginModel(activity, this);

        utilityRes = UtilityRes.getNewInstance();
        utilityKeyboard = UtilityKeyboard.getNewInstance();
        utilitySwitchActivity = UtilitySwitchActivity.getNewInstance();
        utilityInstagram = UtilityInstagram.getNewInstance();
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

    /**
     * twitter click
     * @param loginTwitterButton
     */
    public void clickTwitterLogin(TwitterLoginButton loginTwitterButton){
        loginTwitterButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                loginModel.twitterLogin(TwitterCore.getInstance().getSessionManager().getActiveSession());
            }

            @Override
            public void failure(TwitterException exception) {
                iLoginView.TwitterFail(exception.toString());
            }
        });
    }

    public CallbackManager getCallbackManagerFB(){
        return callbackManagerFB;
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

    //---------------FB callback register
    /**
     * FB click
     */
    public void clickFBLogin(){
        // FB Callback manager
        callbackManagerFB = CallbackManager.Factory.create();
        // FB Callback registration
        LoginManager.getInstance().registerCallback(callbackManagerFB, this);
        LoginManager.getInstance().logInWithReadPermissions(activity
                , Arrays.asList("public_profile", "email"));
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        String accessToken = loginResult.getAccessToken().getToken();
        Logger.d("FB accessToken: " + accessToken);
        loginModel.fbLogin(accessToken);
    }

    @Override
    public void onCancel() {
        iLoginView.onFBCancel();
    }

    @Override
    public void onError(FacebookException error) {
        iLoginView.onFBError(error.getMessage());
    }

    @Override
    public void FBSuccess() {
        iLoginView.FBSuccess();
    }

    @Override
    public void FBFail(String message) {
        iLoginView.FBFail(message);
    }

    public String getFB_DISPLAY_NAME(){
        return loginModel.getFB_DISPLAY_NAME();
    }

    public String getFB_EMAIL(){
        return loginModel.getFB_EMAIL();
    }

    public String getFB_PHONE_NUMBER(){
        return loginModel.getFB_PHONE_NUMBER();
    }

    public String getFB_PIC_URL(){
        return loginModel.getFB_PIC_URL();
    }

    public String getFB_USER_ID(){
        return loginModel.getFB_USER_ID();
    }

    public String getFB_PROVIDER_ID(){
        return loginModel.getFB_PROVIDER_ID();
    }
    //---------------FB callback register

    //-------------------LINE
    /**
     * LINE click
     * @param activity
     */
    public void clickLineLogin(){
        try{
            // App-to-app login
            Intent loginIntent = LineLoginApi.getLoginIntent(activity, utilityRes.getString(activity, R.string.line_channel_id));
            activity.startActivityForResult(loginIntent, LINE_REQUEST_CODE);
        }
        catch(Exception e) {
            Logger.e("ERROR: "+ e.toString());
        }
    }

    public void initLine(){
        utilityLINE = UtilityLINE.getNewInstance(activity
                , utilityRes.getString(activity, R.string.line_channel_id)
                , this);
    }

    public void saveLineInfo(LineLoginResult result) {
        loginModel.saveLineInfo(result);
    }

    public String getLINE_ACCESS_TOKEN(){
        return loginModel.getLINE_ACCESS_TOKEN();
    }

    public long getLINE_ACCESS_TOKEN_EXPIRE_TIME(){
        return loginModel.getLINE_ACCESS_TOKEN_EXPIRE_TIME();
    }

    public String getLINE_DISPLAY_NAME(){
        return loginModel.getLINE_DISPLAY_NAME();
    }

    public String getLINE_STATUS_MESSAGE(){
        return loginModel.getLINE_STATUS_MESSAGE();
    }

    public String getLINE_USER_ID(){
        return loginModel.getLINE_USER_ID();
    }

    public String getLINE_PIC_URL(){
        return loginModel.getLINE_PIC_URL();
    }

    @Override
    public void onGetLineApiClientFinish() {
        Logger.d(utilityLINE.getDisplayName());
    }
    //-------------------LINE

    //-------------------Instagram
    /**
     * Instagram click
     * @param activity
     */
    public void clickInstagramLogin(){
        utilityInstagram.showDialog(activity
                , utilityRes.getString(activity, R.string.instgram_client_id)
                , utilityRes.getString(activity, R.string.instgram_client_secret)
                , utilityRes.getString(activity, R.string.instgram_callback_url)
                , this);
    }

    @Override
    public void onInstagramSuccess() {
        iLoginView.onInstagramSuccess();
    }

    @Override
    public void onInstagramFail(String error) {
        iLoginView.onInstagramFail(error);
    }
    //-------------------Instagram

    //-------------------Twitter
    @Override
    public void TwitterRequestEmailFailure(String exception) {
        Logger.w("signInWithCredential:failure " + exception);
        iLoginView.TwitterRequestEmailFailure(exception);
    }

    @Override
    public void TwitterFail(String exception) {
        Logger.w("signInWithCredential:failure "+ exception);
        iLoginView.TwitterFail(exception);
    }

    @Override
    public void TwitterSuccess() {
        iLoginView.TwitterSuccess();
    }

    public String getTWITTER_DISPLAY_NAME(){
        return loginModel.getTWITTER_DISPLAY_NAME();
    }

    public String getTWITTER_EMAIL(){
        return loginModel.getTWITTER_EMAIL();
    }

    public String getTWITTER_PHONE_NUMBER(){
        return loginModel.getTWITTER_PHONE_NUMBER();
    }

    public String getTWITTER_PIC_URL(){
        return loginModel.getTWITTER_PIC_URL();
    }

    public String getTWITTER_USER_ID(){
        return loginModel.getTWITTER_USER_ID();
    }

    public String getTWITTER_PROVIDER_ID(){
        return loginModel.getTWITTER_PROVIDER_ID();
    }
    //-------------------Twitter
}
