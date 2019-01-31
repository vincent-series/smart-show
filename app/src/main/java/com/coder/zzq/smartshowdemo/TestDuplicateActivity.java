package com.coder.zzq.smartshowdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class TestDuplicateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_duplicate);
    }

    public void showAppleBottom(View view) {
        ToastUtil.showToast("苹果", Toast.LENGTH_SHORT);
    }

    public void showBananaBottom(View view) {
        ToastUtil.showToast("香蕉", Toast.LENGTH_SHORT);
    }

    public void showOrangeCenter(View view) {
        ToastUtil.showCenterToast("橘子", Toast.LENGTH_SHORT);
    }
}
