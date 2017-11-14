package com.myapplication.Model.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.myapplication.Object.SharedPreferenceKey;
import com.myapplication.Presenter.login.ILoginPresenter;
import com.variable.UtilityKeyStore;
import com.variable.UtilityRes;
import com.variable.UtilitySharedPreferences;
import com.variable.UtilityToast;

/**
 * Created by star on 2017/11/10.
 */

public class LoginModel {
    private Activity activity;
    private ILoginPresenter iLoginPresenter;
    private FirebaseAuth firebaseAuth;
    private UtilitySharedPreferences utilitySharedPreferences;
    private UtilityKeyStore utilityKeyStore;

    public LoginModel(Activity activity, ILoginPresenter iLoginPresenter) {
        this.activity = activity;
        this.iLoginPresenter = iLoginPresenter;

        firebaseAuth = FirebaseAuth.getInstance();
        utilitySharedPreferences = UtilitySharedPreferences.getNewInstance(activity);
        utilityKeyStore = UtilityKeyStore.getNewInstance(activity);
    }

    public void logIn(String email, final String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if(TextUtils.isEmpty(user.getEmail())==false){
                                String encrtpyP = utilityKeyStore.encrypt(password);
                                utilitySharedPreferences.save(SharedPreferenceKey.ENCRYPT_P, encrtpyP);
                                iLoginPresenter.onReceive("LOGIN_SUCCESS");
                            }else{
                                iLoginPresenter.onReceive("LOGIN_FAIL");
                            }
                        }else{
                            iLoginPresenter.onReceive("LOGIN_FAIL");
                        }
                    }
                });
    }

    public String getP(){
        return utilitySharedPreferences.loadString(SharedPreferenceKey.ENCRYPT_P, "");
    }
}
