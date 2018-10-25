package com.coder.zzq.smartshowdemo;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.toast.SmartToast;

public class TestDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);

    }


    public void onNextPageClick(View view) {
        SmartDialog.loading("加载中...").small().create(this).show();

    }

    public void onNotificationClick(View view) {

        SmartDialog.notification("充值成功").create(this).show();
    }

    public void onEnsureClick(View view) {
        SmartDialog.ensure("确定不再关注此人？")
                .create(this)
                .show();
    }

    public void onEnsureDelayClick(View view) {
        SmartDialog.ensureDelay("确定启用开发者模式？")
                .create(this)
                .show();
    }


    public void onInputClick(View view) {
        SmartDialog.inputText().hint("请输入建议")
                .inputAtMost(70)
                .positiveBtn("提交", new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(TextView btn, Object data) {
                        SmartToast.showInCenter("已提交——>" + data.toString());
                    }
                })
                .create(this)
                .show();

    }

    public void onLoadingLargeClick(View view) {
        SmartDialog.loading("加载中").large().create(this).show();
    }

    public void onLoadingMiddleClick(View view) {
        SmartDialog.loading("加载中").middle().create(this).show();
    }

    public void onLoadingSmallClick(View view) {
        SmartDialog.loading("加载中").small().create(this).show();
    }
}
