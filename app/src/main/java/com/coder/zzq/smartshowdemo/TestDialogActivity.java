package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.dialog.DialogWrapper;
import com.coder.zzq.smartshow.dialog.SmartDialog;

public class TestDialogActivity extends AppCompatActivity {
    private DialogWrapper mDialogWrapper = new DialogWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);

    }


    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestDialogActivity.class));
    }

    private DialogWrapper mNotification = new DialogWrapper();

    public void onNotificationClick(View view) {
//        SmartDialog.notification("充值成功").createAndShow(this, null);
    }

    public void onEnsureClick(View view) {
        SmartDialog.ensure("确定不再关注此人？").createAndShow(this, mNotification);
    }

    public void onEnsureDelayClick(View view) {
//        SmartDialog.ensureDelay("确定启用开发者模式？").sh(this).show();
    }


    public void onInputClick(View view) {
//        SmartDialog.inputText().hint("请输入建议")
//                .inputAtMost(70)
//                .positiveBtn("提交", new DialogBtnClickListener() {
//                    @Override
//                    public void onBtnClick(TextView btn, Object data) {
//                        SmartToast.showInCenter("已提交——>" + data.toString());
//                    }
//                })
//                .create(this)
//                .show();

    }

    public void onLoadingLargeClick(View view) {
//        SmartDialog.loading("加载中").large().create(this).show();
    }

    public void onLoadingMiddleClick(View view) {
//        SmartDialog.loading("加载中").middle().create(this).show();
    }

    public void onLoadingSmallClick(View view) {
//        SmartDialog.loading("加载中").small().create(this).show();
    }
}
