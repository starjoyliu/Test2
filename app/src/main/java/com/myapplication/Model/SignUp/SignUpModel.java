package com.myapplication.Model.SignUp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.myapplication.Object.SharedPreferenceKey;
import com.myapplication.Presenter.SignUp.ISignUpPresenter;
import com.variable.UtilityKeyStore;
import com.variable.UtilitySharedPreferences;

/**
 * Created by star on 2017/11/13.
 */

public class SignUpModel {
    private Activity activity;
    private ISignUpPresenter iSignUpPresenter;
    private FirebaseAuth firebaseAuth;
    private UtilityKeyStore utilityKeyStore;
    private UtilitySharedPreferences utilitySharedPreferences;

    public SignUpModel(Activity activity, ISignUpPresenter iSignUpPresenter) {
        this.activity = activity;
        this.iSignUpPresenter = iSignUpPresenter;

        firebaseAuth = FirebaseAuth.getInstance();
        utilityKeyStore = UtilityKeyStore.getNewInstance(activity);
        utilitySharedPreferences = UtilitySharedPreferences.getNewInstance(activity);
    }

    public void sinUp(String email, final String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if(TextUtils.isEmpty(user.getEmail())==false){
                        String encrtpyP = utilityKeyStore.encrypt(password);
                        utilitySharedPreferences.save(SharedPreferenceKey.ENCRYPT_P, encrtpyP);
                        iSignUpPresenter.onReceive("SIGN_UP_SUCCESS");
                    }else{
                        iSignUpPresenter.onReceive("SIGN_UP_FAIL");
                    }
                }else{
                    iSignUpPresenter.onReceive("SIGN_UP_FAIL");
                    iSignUpPresenter.onReceive(task.getException().getMessage());
                }
            }
        });
    }

    public String getP(){
        return utilityKeyStore.decrypt(utilitySharedPreferences.loadString(SharedPreferenceKey.ENCRYPT_P, ""));
    }
}
