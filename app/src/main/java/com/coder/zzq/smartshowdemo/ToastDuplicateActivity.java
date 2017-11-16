package com.coder.zzq.smartshowdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;

public class ToastDuplicateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_duplicate);
    }

    public void onAppleClick(View view) {
        SmartToast.show("苹果");
    }

    public void onBananaClick(View view) {
        SmartToast.show("香蕉");
    }
}
