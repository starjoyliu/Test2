package com.myapplication.View.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myapplication.Presenter.TestActivityPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.myapplication.utility.UtilityToast;

/**
 * Created by star on 2017/11/1.
 */

public class TestActivity extends BaseActivity implements ITestActivity {
    private final String TAG = TestActivity.class.getSimpleName();

    private Button btnLoad;
    private TestActivityPresenter testActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btnLoad = findViewById(R.id.button2);

        testActivityPresenter = new TestActivityPresenter(this, bundle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        testActivityPresenter.loadButtonText();

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilityToast.getNewInstance().show(activity, getButtonLoadText());
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
    public void setButtonLoadText(String msg) {
        btnLoad.setText(msg);
    }

    @Override
    public String getButtonLoadText() {
        return testActivityPresenter.getButtonLoadText();
    }
}
