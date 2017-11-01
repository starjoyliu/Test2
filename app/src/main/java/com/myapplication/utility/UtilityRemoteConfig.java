package com.myapplication.utility;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.myapplication.Object.Interface.IRemoteConfig;
import com.myapplication.R;

/**
 * 搭配 IRemoteConfig 使用
 * Created by star on 2017/11/1.
 */

public class UtilityRemoteConfig {
    private volatile static UtilityRemoteConfig u;
    private FirebaseRemoteConfig firebaseRemoteConfig;

    public UtilityRemoteConfig (){}

    public static UtilityRemoteConfig getNewInstance() {
        if(u == null) {
            synchronized (UtilityRemoteConfig.class) {
                if(u == null) {
                    u = new UtilityRemoteConfig();
                }
            }
        }
        return u;
    }

    public void init(final IRemoteConfig listener){
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_default);
        firebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(listener!=null) {
                        firebaseRemoteConfig.activateFetched();
                        listener.onCompleteRemoteConfig(IRemoteConfig.SUCCESS);
                    } else {
                        listener.onCompleteRemoteConfig(IRemoteConfig.LISTENER_NULL);
                    }
                }else{
                    listener.onFailedRemoteConfig(IRemoteConfig.FAILED);
                }
            }
        });
    }

    public String getString(String key){
        return firebaseRemoteConfig.getString(key);
    }

    public boolean getBoolean(String key){
        return firebaseRemoteConfig.getBoolean(key);
    }

    public long getInt(String key){
        return firebaseRemoteConfig.getLong(key);
    }
}
