package com.myapplication.View.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myapplication.Presenter.TestActivityPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.variable.UtilityKeyboard;
import com.variable.UtilityToast;

/**
 * Created by star on 2017/11/1.
 */

public class TestActivity extends BaseActivity implements ITestActivity {
    private final String TAG = TestActivity.class.getSimpleName();

    private Button btnLoad, btnUpdate;
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

        testActivityPresenter = new TestActivityPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilityKeyboard.getNewInstance().hiddenKeyboard(activity, view);
                testActivityPresenter.load();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilityKeyboard.getNewInstance().hiddenKeyboard(activity, view);
                testActivityPresenter.update(getEtName(), getEtPhone());
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
