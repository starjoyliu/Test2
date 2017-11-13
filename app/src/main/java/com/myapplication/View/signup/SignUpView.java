package com.myapplication.View.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myapplication.Presenter.SignUpView.SignUpPresenter;
import com.myapplication.Presenter.login.LoginPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.myapplication.View.login.LoginView;
import com.myapplication.View.speech2text.Speech2TextActivity;
import com.variable.UtilityKeyboard;
import com.variable.UtilitySwitchActivity;
import com.variable.UtilityToast;

/**
 * Created by star on 2017/11/13.
 */

public class SignUpView extends BaseActivity implements ISignUpView, View.OnClickListener{
    private final String TAG = Speech2TextActivity.class.getSimpleName();
    private SignUpPresenter signUpPresenter;
    private EditText etEmail, etPassword;
    private Button btnSignUp, btnBack;
    private UtilityToast utilityToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.editText4);
        etPassword = findViewById(R.id.editText5);
        btnSignUp = findViewById(R.id.signup_btn_signup);
        btnBack = findViewById(R.id.signup_btn_back);

        signUpPresenter = new SignUpPresenter(activity, this);

        utilityToast = UtilityToast.getNewInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnSignUp.setOnClickListener(this);
        btnBack.setOnClickListener(this);
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
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.signup_btn_signup:
                signUpPresenter.signUp(etEmail, etPassword);
                break;
            case R.id.signup_btn_back:
                signUpPresenter.clickBack();
                break;
        }
    }

    @Override
    public void errorMessage(String msg) {
        utilityToast.show(activity, msg);
    }

    @Override
    public void showMessage(String msg) {
        utilityToast.show(activity, msg);
    }
}
