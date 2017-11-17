package com.myapplication.View.login;

import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by star on 2017/11/10.
 */

public interface ILoginView {
    void logIn(String email);

    void errorMessage(String msg);

    void showMessage(String msg);

    void onInstagramFail(String error);

    void onInstagramSuccess();

    void TwitterRequestEmailFailure(String exception);

    void TwitterFail(String exception);

    void TwitterSuccess();

    void onFBCancel();

    void onFBError(String error);

    void FBSuccess();

    void FBFail(String message);
}
