package com.myapplication.Model;

import android.os.Bundle;

/**
 * Created by star on 2017/11/1.
 */

public class MTestActivity {
    private Bundle data;

    public void setData(Bundle data) {
        this.data = data;
    }

    public Bundle getData(){
        return this.data;
    }
}
