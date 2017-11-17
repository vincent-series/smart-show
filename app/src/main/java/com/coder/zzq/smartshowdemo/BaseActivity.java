package com.coder.zzq.smartshowdemo;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.coder.zzq.smartshow.snackbar.SmartSnackbar;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentLayout());

    }

    @LayoutRes
    protected abstract int contentLayout();


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
