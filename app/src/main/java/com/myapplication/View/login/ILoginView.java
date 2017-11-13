package com.myapplication.View.login;

/**
 * Created by star on 2017/11/10.
 */

public interface ILoginView {
    void logIn(String email);

    void errorMessage(String msg);

    void showMessage(String msg);
}
