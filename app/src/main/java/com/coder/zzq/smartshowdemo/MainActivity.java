package com.coder.zzq.smartshowdemo;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.coder.zzq.smartshow.SmartSnackbar;
import com.coder.zzq.smartshow.SmartToast;
import com.coder.zzq.smartshow.snackbar.custom.BaseTransientBar;
import com.coder.zzq.smartshow.snackbar.custom.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showSnackBar(View view) {
        SmartSnackbar.get().showAtTop("你好");
    }
}
