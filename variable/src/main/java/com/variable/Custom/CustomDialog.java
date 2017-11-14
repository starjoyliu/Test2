package com.variable.Custom;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.variable.R;

/**
 * Custom full width dialog
 * Created by star on 2017/11/14.
 */
public class CustomDialog extends Dialog{
    public CustomDialog(@NonNull Context context) {
        super(context, R.style.MATCH_DIALOG);
    }
}
