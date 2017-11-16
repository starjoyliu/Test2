package com.myapplication.View.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.log.Logger;
import com.myapplication.Presenter.login.LoginPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.myapplication.View.speech2text.Speech2TextActivity;
import com.myapplication.utility.UtilityLINE;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.variable.UtilityInstagram;
import com.variable.UtilityKeyboard;
import com.variable.UtilityRes;
import com.variable.UtilitySwitchActivity;
import com.variable.UtilityToast;

import java.util.Arrays;

/**
 * Created by star on 2017/11/10.
 */

public class LoginView extends BaseActivity implements ILoginView
        , View.OnClickListener
        , UtilityLINE.ILINE
        , UtilityInstagram.IInstagram{
    private final String TAG = Speech2TextActivity.class.getSimpleName();
    private static final int LINE_REQUEST_CODE = 1006;

    private LoginPresenter loginPresenter;
    private EditText etEmail, etPassword;
    private Button btnLogin, btnSignUp, loginFBButton
            , loginLineButton, loginInstagramButton;
    private TwitterLoginButton loginTwitterButton;
    private CallbackManager callbackManagerFB;

    private UtilityKeyboard utilityKeyboard;
    private UtilityToast utilityToast;
    private UtilitySwitchActivity utilitySwitchActivity;
    private UtilityRes utilityRes;
    private UtilityLINE utilityLINE;
    private UtilityInstagram utilityInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.editText4);
        etPassword = findViewById(R.id.editText5);
        btnLogin = findViewById(R.id.loginview_btn_login);
        btnSignUp = findViewById(R.id.loginview_btn_sign_up);

        // FB Callback manager
        callbackManagerFB = CallbackManager.Factory.create();
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
        utilityLINE = UtilityLINE.getNewInstance(activity
                , utilityRes.getString(activity, R.string.line_channel_id)
                , this);
        utilityInstagram = UtilityInstagram.getNewInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        //FB
        loginFBButton.setOnClickListener(this);
        // FB Callback registration
        LoginManager.getInstance().registerCallback(callbackManagerFB, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException error) {
                // App code
            }
        });

        //LINE
        loginLineButton.setOnClickListener(this);

        //Instagram
        loginInstagramButton.setOnClickListener(this);

        //Twitter
        loginTwitterButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                Logger.d("token: "+token);
                Logger.d("secret: "+secret);

                TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        // Do something with the result, which provides the email address
                        Logger.d("email: "+ result.data);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Do something on failure
                        utilityToast.show(activity, exception.getMessage());
                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                utilityToast.show(activity, exception.getMessage());
            }
        });

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
        callbackManagerFB.onActivityResult(requestCode, resultCode, data);
        // Twitter Pass the activity result to the login button.
        loginTwitterButton.onActivityResult(requestCode, resultCode, data);

        // LINE
        if(requestCode == LINE_REQUEST_CODE){
            LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);
            switch (result.getResponseCode()) {
                case SUCCESS:
                    // Login successful
                    String accessToken = result.getLineCredential().getAccessToken().getAccessToken();
                    Logger.d(accessToken);
                    Logger.d(Long.toString(result.getLineCredential().getAccessToken().getExpiresInMillis()));
                    Logger.d(result.getLineProfile().getDisplayName());
                    Logger.d(result.getLineProfile().getStatusMessage());
                    Logger.d(result.getLineProfile().getUserId());
                    Logger.d(result.getLineProfile().getPictureUrl().toString());
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
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.loginview_btn_login:
                loginPresenter.clickLogin(etEmail, etPassword);
                break;
            case R.id.loginview_btn_sign_up:
                loginPresenter.clickSignUp();
                break;
            case R.id.login_button:
                LoginManager.getInstance().logInWithReadPermissions(activity
                        , Arrays.asList("public_profile", "user_friends"));
                break;
            case R.id.line_login_button:
                try{
                    // App-to-app login
                    Intent loginIntent = LineLoginApi.getLoginIntent(activity, utilityRes.getString(activity, R.string.line_channel_id));
                    startActivityForResult(loginIntent, LINE_REQUEST_CODE);
                }
                catch(Exception e) {
                    Logger.e("ERROR: "+ e.toString());
                }
                break;
            case R.id.instagram_login_button:
                utilityInstagram.showDialog(activity
                        , utilityRes.getString(activity, R.string.instgram_client_id)
                        , utilityRes.getString(activity, R.string.instgram_client_secret)
                        , utilityRes.getString(activity, R.string.instgram_callback_url)
                        , this);
                break;
        }
    }

    @Override
    public void onGetLineApiClientFinish() {
        Logger.d(utilityLINE.getDisplayName());
    }

    @Override
    public void onInstagramSuccess() {
        Logger.d(utilityInstagram.getUserName());
    }

    @Override
    public void onInstagramFail(String error) {
        utilityToast.show(activity, error);
    }
}
