package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;

public class TestTypeToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_type_toast);
    }

    public void onInfoClick(View view) {
        SmartToast.info("当前网络良好");
    }

    public void onSuccessClick(View view) {
        SmartToast.success("删除成功");
    }

    public void onErrorClick(View view) {
        SmartToast.error("数据解析出错");
    }

    public void onWarningClick(View view) {
        SmartToast.warning("电量过低，请尽快充电");
    }

    public void onFailClick(View view) {
        SmartToast.fail("保存失败");
    }

    public void onCompleteClick(View view) {
        SmartToast.complete("下载完成");
    }


    public void onForbidClick(View view) {
        SmartToast.forbid("当前账户不允许汇款操作");
    }

    public void onWaitingClick(View view) {
        SmartToast.waiting("已在后台下载，请耐心等待");
    }


    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestTypeToastActivity.class));
    }


}
