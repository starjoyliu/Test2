package com.myapplication.View.login;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.log.Logger;
import com.myapplication.Presenter.Speech2TextPresenter;
import com.myapplication.Presenter.login.LoginPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.myapplication.View.signup.SignUpView;
import com.myapplication.View.speech2text.Speech2TextActivity;
import com.myapplication.View.test.TestActivity;
import com.variable.UtilityKeyStore;
import com.variable.UtilityKeyboard;
import com.variable.UtilitySwitchActivity;
import com.variable.UtilityToast;

import java.util.ArrayList;

/**
 * Created by star on 2017/11/10.
 */

public class LoginView extends BaseActivity implements ILoginView, View.OnClickListener{
    private final String TAG = Speech2TextActivity.class.getSimpleName();
    private LoginPresenter loginPresenter;
    private EditText etEmail, etPassword;
    private Button btnLogin, btnSignUp;
    private UtilityKeyboard utilityKeyboard;
    private UtilityToast utilityToast;
    private UtilitySwitchActivity utilitySwitchActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.editText4);
        etPassword = findViewById(R.id.editText5);
        btnLogin = findViewById(R.id.loginview_btn_login);
        btnSignUp = findViewById(R.id.loginview_btn_sign_up);

        loginPresenter = new LoginPresenter(activity, this);
        utilityKeyboard = UtilityKeyboard.getNewInstance();
        utilityToast = UtilityToast.getNewInstance();
        utilitySwitchActivity = UtilitySwitchActivity.getNewInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

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
        }
    }
}
