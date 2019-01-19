package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.dialog.dialog.DialogWrapper;
import com.coder.zzq.smartshow.dialog.dialog.type.IEnsureBuilder;
import com.coder.zzq.smartshow.dialog.dialog.type.INotificationBuilder;
import com.coder.zzq.smartshow.dialog.dialog.type.impl.EnsureBuilder;
import com.coder.zzq.smartshow.dialog.dialog.type.impl.NotificationBuilder;

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
        IEnsureBuilder ensureBuilder = new EnsureBuilder(this,R.style.smart_show_normal_dialog);
        ensureBuilder.confirmBtn("确定",null)
                .cancelBtn("取消",null)
                .message("确定不再关注此人？")
                .createAndShow(this,null);


//        SmartDialog.notification("充值成功").createAndShow(this, null);
    }

    public void onEnsureClick(View view) {
//        SmartDialog.ensure("确定不再关注此人？").createAndShow(this, null);
    }

    public void onEnsureDelayClick(View view) {
//        SmartDialog.ensureDelay("确定启用开发者模式？").delaySeconds(10).createAndShow(this, mNotification);
    }


    public void onInputClick(View view) {
//        SmartDialog.inputText().hint("请输入建议")
//                .inputAtMost(70)
//                .positiveBtn("提交", new DialogBtn.onDialogBtnClickListener() {
//                    @Override
//                    public void onDialogBtnClick(Dialog dialog, DialogBtn btn, Object data) {
//
//                    }
//                })
//                .createAndShow(this, mNotification);

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
