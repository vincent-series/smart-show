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
        SmartToast.success("重置成功");
    }

    public void onShowAtTopClick(View view) {
        SmartToast.fail("下载失败");
    }

    public void onShowInCenterClick(View view) {
        SmartToast.warning("电量过低，请充电");
    }

    public void onShowAtLocationClick(View view) {
        SmartToast.normal("已在后台下载");
    }

    public void onNextPageClick(View view) {
        SmartToast.error("密码错误");
    }
}
