package com.coder.zzq.smartshowdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;

public class TestTypeToastActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_type_toast);
    }

    public void onShowClick(View view) {
        SmartToast.success("重置成功");
        SmartToast.successLong("重置成功");
    }

    public void onShowAtTopClick(View view) {
        SmartToast.error("保存失败");
        SmartToast.errorLong("保存失败");
    }

    public void onShowInCenterClick(View view) {
        SmartToast.warning("电量过低，请充电");
        SmartToast.warning("电量过低，请充电");
    }

    public void onShowAtLocationClick(View view) {
        SmartToast.info("已在后台下载");
        SmartToast.infoLong("已在后台下载");
    }

    public void onNextPageClick(View view) {

    }
}
