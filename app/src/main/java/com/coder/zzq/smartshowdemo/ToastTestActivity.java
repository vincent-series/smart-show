package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.coder.zzq.smartshow.SmartToast;

public class ToastTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toast_test_activity);
    }

    public void onShowClick(View view) {
        SmartToast.show("香蕉");
    }

    public void onShowAtTopClick(View view) {
        SmartToast.showAtTop("苹果");
    }

    public void onShowInCenterClick(View view) {
        SmartToast.showInCenter("荔枝");
    }

    public void onShowAtLocationClick(View view) {
        SmartToast.showAtLocation("芒果", Gravity.TOP|Gravity.LEFT,30,60);
    }

    public void onNextPageClick(View view) {
        startActivity(new Intent(this,ToastTestActivity.class));
    }
}
