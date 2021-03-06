package com.myapplication.Object.Interface;

/**
 * 若要取得遠端設定檔, 請實作
 * Created by star on 2017/11/1.
 */

public interface IRemoteConfig {
    String SUCCESS = "SUCCESS";
    String FAILED = "FAILED";
    String LISTENER_NULL = "LISTENER_NULL";

    void onCompleteRemoteConfig(String msg);
    void onFailedRemoteConfig(String msg);
}
