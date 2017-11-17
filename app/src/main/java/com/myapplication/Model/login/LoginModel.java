package com.myapplication.Model.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.log.Logger;
import com.myapplication.Object.SharedPreferenceKey;
import com.myapplication.Presenter.login.ILoginPresenter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.variable.UtilityKeyStore;
import com.variable.UtilityRes;
import com.variable.UtilitySharedPreferences;
import com.variable.UtilityToast;

import java.util.Arrays;

/**
 * Created by star on 2017/11/10.
 */

public class LoginModel {
    private Activity activity;
    private ILoginPresenter iLoginPresenter;
    private FirebaseAuth firebaseAuth;
    private UtilitySharedPreferences utilitySharedPreferences;
    private UtilityKeyStore utilityKeyStore;

    //-----------------LINE
    private final String LINE_ACCESS_TOKEN = "LINE_ACCESS_TOKEN";
    private final String LINE_ACCESS_TOKEN_EXPIRE_TIME = "LINE_ACCESS_TOKEN_EXPIRE_TIME";
    private final String LINE_DISPLAY_NAME = "LINE_DISPLAY_NAME";
    private final String LINE_STATUS_MESSAGE = "LINE_STATUS_MESSAGE";
    private final String LINE_USER_ID = "LINE_USER_ID";
    private final String LINE_PIC_URL = "LINE_PIC_URL";

    //Twitter
    private final String TWITTER_DISPLAY_NAME = "FB_DISPLAY_NAME";
    private final String TWITTER_EMAIL = "FB_EMAIL";
    private final String TWITTER_PHONE_NUMBER = "FB_PHONE_NUMBER";
    private final String TWITTER_PIC_URL = "FB_PIC_URL";
    private final String TWITTER_USER_ID = "FB_USER_ID";
    private final String TWITTER_PROVIDER_ID = "FB_PROVIDER_ID";


    //FB
    private final String FB_DISPLAY_NAME = "FB_DISPLAY_NAME";
    private final String FB_EMAIL = "FB_EMAIL";
    private final String FB_PHONE_NUMBER = "FB_PHONE_NUMBER";
    private final String FB_PIC_URL = "FB_PIC_URL";
    private final String FB_USER_ID = "FB_USER_ID";
    private final String FB_PROVIDER_ID = "FB_PROVIDER_ID";

    public LoginModel(Activity activity, ILoginPresenter iLoginPresenter) {
        this.activity = activity;
        this.iLoginPresenter = iLoginPresenter;

        firebaseAuth = FirebaseAuth.getInstance();
        utilitySharedPreferences = UtilitySharedPreferences.getNewInstance(activity);
        utilityKeyStore = UtilityKeyStore.getNewInstance(activity);
    }

    public void logIn(String email, final String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if(TextUtils.isEmpty(user.getEmail())==false){
                                String encrtpyP = utilityKeyStore.encrypt(password);
                                utilitySharedPreferences.save(SharedPreferenceKey.ENCRYPT_P, encrtpyP);
                                iLoginPresenter.onReceive("LOGIN_SUCCESS");
                            }else{
                                iLoginPresenter.onReceive("LOGIN_FAIL");
                            }
                        }else{
                            iLoginPresenter.onReceive("LOGIN_FAIL");
                        }
                    }
                });
    }

    public void instagramLogin(){

    }

    public void fbLogin(String accessToken){
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Logger.d("fb signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            utilitySharedPreferences.save(FB_DISPLAY_NAME, user.getDisplayName());
                            utilitySharedPreferences.save(FB_EMAIL, user.getEmail());
                            utilitySharedPreferences.save(FB_PHONE_NUMBER, user.getPhoneNumber());
                            utilitySharedPreferences.save(FB_PIC_URL, user.getPhotoUrl().toString());
                            utilitySharedPreferences.save(FB_USER_ID, user.getUid());
                            utilitySharedPreferences.save(FB_PROVIDER_ID, user.getProviderId());

                            iLoginPresenter.FBSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            iLoginPresenter.FBFail(task.getException().getMessage());
                        }
                    }
                });
    }

    public void twitterLogin(TwitterSession session){
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Logger.d("signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            utilitySharedPreferences.save(TWITTER_DISPLAY_NAME, user.getDisplayName());
                            utilitySharedPreferences.save(TWITTER_EMAIL, user.getEmail());
                            utilitySharedPreferences.save(TWITTER_PHONE_NUMBER, user.getPhoneNumber());
                            utilitySharedPreferences.save(TWITTER_PIC_URL, user.getPhotoUrl().toString());
                            utilitySharedPreferences.save(TWITTER_USER_ID, user.getUid());
                            utilitySharedPreferences.save(TWITTER_PROVIDER_ID, user.getProviderId());
                            iLoginPresenter.TwitterSuccess();
                        } else {
                            iLoginPresenter.TwitterFail(task.getException().getMessage());
                        }
                    }
                });

        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                // Do something with the result, which provides the email address
                Logger.d("email: "+ result.data);
                utilitySharedPreferences.save(TWITTER_EMAIL, result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                iLoginPresenter.TwitterRequestEmailFailure(exception.getMessage());
            }
        });
    }

    public String getP(){
        return utilitySharedPreferences.loadString(SharedPreferenceKey.ENCRYPT_P, "");
    }

    //--------------------------LINE
    public void saveLineInfo(LineLoginResult result) {
        String accessToken = result.getLineCredential().getAccessToken().getAccessToken();
        Logger.d(accessToken);
        Logger.d(Long.toString(result.getLineCredential().getAccessToken().getExpiresInMillis()));
        Logger.d(result.getLineProfile().getDisplayName());
        Logger.d(result.getLineProfile().getStatusMessage());
        Logger.d(result.getLineProfile().getUserId());
        Logger.d(result.getLineProfile().getPictureUrl().toString());
        utilitySharedPreferences.save(LINE_ACCESS_TOKEN, accessToken);
        utilitySharedPreferences.save(LINE_ACCESS_TOKEN_EXPIRE_TIME, Long.toString(result.getLineCredential().getAccessToken().getExpiresInMillis()));
        utilitySharedPreferences.save(LINE_DISPLAY_NAME, result.getLineProfile().getDisplayName());
        utilitySharedPreferences.save(LINE_STATUS_MESSAGE, result.getLineProfile().getStatusMessage());
        utilitySharedPreferences.save(LINE_USER_ID, result.getLineProfile().getUserId());
        utilitySharedPreferences.save(LINE_PIC_URL, result.getLineProfile().getPictureUrl().toString());
    }

    public String getLINE_ACCESS_TOKEN(){
        return utilitySharedPreferences.loadString(LINE_ACCESS_TOKEN, "");
    }

    public long getLINE_ACCESS_TOKEN_EXPIRE_TIME(){
        return utilitySharedPreferences.loadLong(LINE_ACCESS_TOKEN_EXPIRE_TIME, -999);
    }

    public String getLINE_DISPLAY_NAME(){
        return utilitySharedPreferences.loadString(LINE_DISPLAY_NAME, "");
    }

    public String getLINE_STATUS_MESSAGE(){
        return utilitySharedPreferences.loadString(LINE_STATUS_MESSAGE, "");
    }

    public String getLINE_USER_ID(){
        return utilitySharedPreferences.loadString(LINE_USER_ID, "");
    }

    public String getLINE_PIC_URL(){
        return utilitySharedPreferences.loadString(LINE_PIC_URL, "");
    }

    //--------------------------Twitter
    public String getTWITTER_DISPLAY_NAME(){
        return utilitySharedPreferences.loadString(TWITTER_DISPLAY_NAME, "");
    }

    public String getTWITTER_EMAIL(){
        return utilitySharedPreferences.loadString(TWITTER_EMAIL, "");
    }

    public String getTWITTER_PHONE_NUMBER(){
        return utilitySharedPreferences.loadString(TWITTER_PHONE_NUMBER, "");
    }

    public String getTWITTER_PIC_URL(){
        return utilitySharedPreferences.loadString(TWITTER_PIC_URL, "");
    }

    public String getTWITTER_USER_ID(){
        return utilitySharedPreferences.loadString(TWITTER_USER_ID, "");
    }

    public String getTWITTER_PROVIDER_ID(){
        return utilitySharedPreferences.loadString(TWITTER_PROVIDER_ID, "");
    }

    //------------------------FB
    public String getFB_DISPLAY_NAME(){
        return utilitySharedPreferences.loadString(FB_DISPLAY_NAME, "");
    }

    public String getFB_EMAIL(){
        return utilitySharedPreferences.loadString(FB_EMAIL, "");
    }

    public String getFB_PHONE_NUMBER(){
        return utilitySharedPreferences.loadString(FB_PHONE_NUMBER, "");
    }

    public String getFB_PIC_URL(){
        return utilitySharedPreferences.loadString(FB_PIC_URL, "");
    }

    public String getFB_USER_ID(){
        return utilitySharedPreferences.loadString(FB_USER_ID, "");
    }

    public String getFB_PROVIDER_ID(){
        return utilitySharedPreferences.loadString(FB_PROVIDER_ID, "");
    }
}
