package com.myapplication.View.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.log.Logger;
import com.myapplication.Presenter.Speech2TextPresenter;
import com.myapplication.Presenter.TestActivityPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.myapplication.View.speech2text.Speech2TextActivity;
import com.variable.UtilityAnimation;
import com.variable.UtilityDialog;
import com.variable.UtilityKeyboard;
import com.variable.UtilityRes;
import com.variable.UtilitySwitchActivity;
import com.variable.UtilityToast;

/**
 * Created by star on 2017/11/1.
 */

public class TestActivity extends BaseActivity implements ITestActivity {
    private final String TAG = TestActivity.class.getSimpleName();

    private Button btnLoad, btnUpdate, btnSpeech;
    private EditText etName, etPhone;
    private TestActivityPresenter testActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        etName = findViewById(R.id.editText);
        etPhone = findViewById(R.id.editText2);
        btnLoad = findViewById(R.id.load_button);
        btnUpdate = findViewById(R.id.update_button);
        btnSpeech = findViewById(R.id.speech_to_text);

        testActivityPresenter = new TestActivityPresenter(activity, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilityKeyboard.getNewInstance().hiddenKeyboard(activity, view);
                testActivityPresenter.update(getEtName(), getEtPhone());

                View v = activity.getLayoutInflater().inflate(R.layout.test_custom_view_dialog, null);
                final MaterialStyledDialog materialStyledDialog = UtilityDialog.getNewInstance().showCUSTOM(activity
                        , R.string.activity_test_dialog_title
                        , R.string.activity_test_dialog_content
                        , R.drawable.common_google_signin_btn_icon_dark
                        , UtilityDialog.getNewInstance().DEFAULT_HEADER_COLOR
                        , v
                        , UtilityRes.getNewInstance().getInteger(activity, R.integer.test_custom_view_padding)
                        , UtilityRes.getNewInstance().getInteger(activity, R.integer.test_custom_view_padding)
                        , UtilityRes.getNewInstance().getInteger(activity, R.integer.test_custom_view_padding)
                        , UtilityRes.getNewInstance().getInteger(activity, R.integer.test_custom_view_padding));
                v.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        materialStyledDialog.dismiss();
                    }
                });
                materialStyledDialog.show();

                Logger.d("test bool: " + UtilityRes.getNewInstance().getBoolean(activity, R.bool.test_bool));
            }
        });

        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilitySwitchActivity.getNewInstance().switchActivity(activity, Speech2TextActivity.class, true);
            }
        });
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
}
