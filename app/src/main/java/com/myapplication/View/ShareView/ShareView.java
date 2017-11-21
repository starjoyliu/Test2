package com.myapplication.View.ShareView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.log.Logger;
import com.myapplication.Presenter.ShareView.ShareViewPresenter;
import com.myapplication.Presenter.login.LoginPresenter;
import com.myapplication.R;
import com.myapplication.View.base.BaseActivity;
import com.variable.UtilityShare;

/**
 * Created by star on 2017/11/17.
 */

public class ShareView extends BaseActivity implements View.OnClickListener
        , IShareView{
    private final String TAG = ShareView.class.getSimpleName();

    private Button btnGoogle, btnFB, btnLINE, btnWeChat, btnTwitter;

    private ShareViewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_view);

        btnGoogle = findViewById(R.id.share_view_btn_google);
        btnFB = findViewById(R.id.share_view_btn_fb);
        btnLINE = findViewById(R.id.share_view_btn_line);
        btnTwitter = findViewById(R.id.share_view_btn_twitter);
        btnWeChat = findViewById(R.id.share_view_btn_wechat);

        presenter = new ShareViewPresenter(activity, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnGoogle.setOnClickListener(this);
        btnFB.setOnClickListener(this);
        btnLINE.setOnClickListener(this);

        //twitter
        btnTwitter.setOnClickListener(this);

        btnWeChat.setOnClickListener(this);
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
            case R.id.share_view_btn_google:
                presenter.goolgeShareClick("456", "https://www.google.com");
                break;
            case R.id.share_view_btn_fb:
                presenter.fbShareClick("456", "https://www.google.com");
                break;
            case R.id.share_view_btn_line:
                presenter.lineShareClick("456", "https://www.google.com");
                break;
            case R.id.share_view_btn_twitter:
                presenter.twitterShareClick("456", "https://www.google.com");
                break;
            case R.id.share_view_btn_wechat:
                presenter.weiboShareCliclk("123", "456");
                break;
        }
    }
}
