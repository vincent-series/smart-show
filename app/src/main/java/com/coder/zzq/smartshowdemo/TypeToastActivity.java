package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;

public class TypeToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_toast);
    }

    public void onInfoClick(View view) {
        SmartToast.info("已在后台下载");
    }

    public void onSuccessClick(View view) {
        SmartToast.success("删除成功");
    }

    public void onErrorClick(View view) {
        SmartToast.fail("模式转换失败");
    }

    public void onWarningClick(View view) {
        SmartToast.warning("电量过低，请尽快充电");
    }


    public void onNextPageClick(View view) {
        startActivity(new Intent(this,TypeToastActivity.class));
    }
}
