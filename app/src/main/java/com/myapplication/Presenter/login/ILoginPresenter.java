package com.myapplication.Presenter.login;

/**
 * Created by star on 2017/11/10.
 */

public interface ILoginPresenter {
    void onReceive(String login_success);

    void TwitterRequestEmailFailure(String exception);

    void TwitterFail(String exception);

    void TwitterSuccess();

    void FBSuccess();

    void FBFail(String message);
}
