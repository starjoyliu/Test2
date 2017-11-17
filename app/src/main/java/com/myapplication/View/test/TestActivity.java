package com.myapplication.View.test;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.log.Logger;
import com.myapplication.Presenter.TestActivityPresenter;
import com.myapplication.R;
import com.myapplication.View.HighlightOuterFrame.HighlightOuterFrameView;
import com.myapplication.View.InteractiveAnimationView.InteractiveAnimationView;
import com.myapplication.View.ShareView.ShareView;
import com.myapplication.View.base.BaseActivity;
import com.myapplication.View.floatingview.FloatingViewActivity;
import com.myapplication.View.pip.PIPActivity;
import com.myapplication.View.speech2text.Speech2TextActivity;
import com.variable.UtilityAnimation;
import com.variable.UtilityDialog;
import com.variable.UtilityKeyboard;
import com.variable.UtilityRes;
import com.variable.UtilitySwitchActivity;
import com.variable.UtilityToast;
import com.variable.UtilityUI;

/**
 * Created by star on 2017/11/1.
 */

public class TestActivity extends BaseActivity implements ITestActivity, View.OnClickListener {
    private final String TAG = TestActivity.class.getSimpleName();

    private Button btnLoad, btnUpdate, btnSpeech, btnPIP, btnFV, btnHighlight, btnInteractive;
    private Button btnShare;
    private EditText etName, etPhone;
    private TestActivityPresenter testActivityPresenter;
    private UtilityUI utilityUI;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_test);

        etName = findViewById(R.id.editText);
        etPhone = findViewById(R.id.editText2);
        btnLoad = findViewById(R.id.load_button);
        btnUpdate = findViewById(R.id.update_button);
        btnSpeech = findViewById(R.id.speech_to_text);
        btnPIP = findViewById(R.id.pip_btn);
        btnFV = findViewById(R.id.btnFV);
        btnHighlight = findViewById(R.id.btnHighlight);
        btnInteractive = findViewById(R.id.btnInteractive);
        btnShare = findViewById(R.id.btnShare);

        testActivityPresenter = new TestActivityPresenter(activity, this);
        utilityUI = UtilityUI.getNewInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //check if user is signed in (non-null) and update UI
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        updateUI(firebaseUser);
        //sign up new users createAccount()

        btnLoad.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnSpeech.setOnClickListener(this);
        btnPIP.setOnClickListener(this);
        btnFV.setOnClickListener(this);
        btnHighlight.setOnClickListener(this);
        btnInteractive.setOnClickListener(this);
        btnShare.setOnClickListener(this);
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
    public void msg(String msg) {
        UtilityToast.getNewInstance().show(activity, msg);
    }

    @Override
    public String getEtName() {
        return etName.getText().toString();
    }

    @Override
    public void setEtName(String name) {
        etName.setText(name);
    }

    @Override
    public String getEtPhone() {
        return etPhone.getText().toString();
    }

    @Override
    public void setEtPhone(String phone) {
        etPhone.setText(phone);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.load_button:
                UtilityKeyboard.getNewInstance().hiddenKeyboard(activity, view);
                testActivityPresenter.load();

                UtilityAnimation.getNewInstance().FlipInX(view);

//                UtilityDialog.getNewInstance().show(activity
//                        , R.string.activity_test_dialog_title
//                        , R.string.activity_test_dialog_content
//                        , R.string.activity_test_dialog_pos_text
//                        , new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                dialog.dismiss();
//                            }
//                        }
//                        , R.string.activity_test_dialog_neg_text
//                        , new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                dialog.dismiss();
//                            }
//                        });
                break;
            case R.id.update_button:
                UtilityKeyboard.getNewInstance().hiddenKeyboard(activity, view);
                testActivityPresenter.update(getEtName(), getEtPhone());

                View layout = utilityUI.getLayoutView(activity, R.layout.test_custom_view_dialog);
                final MaterialStyledDialog materialStyledDialog = UtilityDialog.getNewInstance().showCUSTOM(activity
                        , R.string.activity_test_dialog_title
                        , R.string.activity_test_dialog_content
                        , R.drawable.common_google_signin_btn_icon_dark
                        , UtilityDialog.getNewInstance().DEFAULT_HEADER_COLOR
                        , layout
                        , UtilityRes.getNewInstance().getInteger(activity, R.integer.test_custom_view_padding)
                        , UtilityRes.getNewInstance().getInteger(activity, R.integer.test_custom_view_padding)
                        , UtilityRes.getNewInstance().getInteger(activity, R.integer.test_custom_view_padding)
                        , UtilityRes.getNewInstance().getInteger(activity, R.integer.test_custom_view_padding));
                layout.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        materialStyledDialog.dismiss();
                    }
                });
                materialStyledDialog.show();

                Logger.d("test bool: " + UtilityRes.getNewInstance().getBoolean(activity, R.bool.test_bool));
                break;
            case R.id.speech_to_text:
                UtilitySwitchActivity.getNewInstance().switchActivity(activity, Speech2TextActivity.class, true);

//                View layout1 = utilityUI.getLayoutView(activity, R.layout.test_custom_view_dialog);
//                Dialog dialog = UtilityDialog.getNewInstance().buildCustomDialog(
//                        activity
//                        , layout1, R.style.DialogTheme_bottom_in_bottom_out
//                        , Gravity.BOTTOM
//                        , true
//                );
//                dialog.show();

                break;
            case R.id.pip_btn:
                UtilitySwitchActivity.getNewInstance().switchActivity(activity, PIPActivity.class, true);
                break;
            case R.id.btnFV:
                UtilitySwitchActivity.getNewInstance().switchActivity(activity, FloatingViewActivity.class, true);
                break;
            case R.id.btnHighlight:
                UtilitySwitchActivity.getNewInstance().switchActivity(activity, HighlightOuterFrameView.class, true);
                break;
            case R.id.btnInteractive:
                UtilitySwitchActivity.getNewInstance().switchActivity(activity, InteractiveAnimationView.class, true);
                break;
            case R.id.btnShare:
                UtilitySwitchActivity.getNewInstance().switchActivity(activity, ShareView.class, true);
                break;
            default:
                Logger.v(TAG + " no match id");
                break;
        }
    }

    // sign up new users
    private void createAccount(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            updateUI(firebaseUser);
                        }else {
                            //TODO show sign up error
                            updateUI(null);
                        }
                    }
                });


    }

    /**
     * sign in
     * @param email
     * @param password
     */
    private void signIn(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            updateUI(firebaseUser);
                        }else{
                            //TODO show sign in error
                            updateUI(null);
                        }
                    }
                });
    }

    /**
     * 更新畫面
     * @param firebaseUser
     */
    private void updateUI(FirebaseUser firebaseUser) {
        if(firebaseUser!=null){
            switchToHome();
        }else{
            switchToSignUp();
        }
    }

    /**
     * 切換到首頁
     */
    private void switchToHome() {

    }

    /**
     * 切換到註冊畫面
     */
    private void switchToSignUp() {

    }
}
