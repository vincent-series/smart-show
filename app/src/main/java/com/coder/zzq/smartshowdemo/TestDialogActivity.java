package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.InputCheckListener;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.type.INormalDialogBuilder;
import com.coder.zzq.smartshow.toast.SmartToast;

public class TestDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);

    }


    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestDialogActivity.class));
    }

    public void onNotificationClick(View view) {

        SmartDialog.messageDialog()
                .message("充值成功")
                .buttonMode(INormalDialogBuilder.MODE_ONLY_CONFIRM)
                .createAndShow(this, 0);
    }

    public void onEnsureClick(View view) {
        SmartDialog.messageDialog()
                .message("确定不再关注此人？")
                .buttonMode(INormalDialogBuilder.MODE_BOTN_CONFIRM_AND_CANCEL)
                .createAndShow(this, 1);
    }

    public void onEnsureDelayClick(View view) {
        SmartDialog.messageDialog()
                .buttonMode(INormalDialogBuilder.MODE_BOTN_CONFIRM_AND_CANCEL)
                .delaySecondsConfirm(10)
                .createAndShow(this, 2);

    }


    public void onInputClick(View view) {
        SmartDialog.inputText().hint("请输入建议")
                .inputAtMost(70)
                .confirmBtn("提交", new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(TextView btn, Object data) {
                        SmartToast.showInCenter("已提交——>" + data.toString());
                    }
                })
                .inputCheck(new InputCheckListener() {
                    @Override
                    public boolean check(String input) {
                        if (input.trim().length() > 70) {
                            SmartToast.showInCenter("最多输入70个字！");
                            return false;
                        }
                        return true;
                    }
                })
                .createAndShow(this, 3);

    }

    public void onLoadingLargeClick(View view) {
        SmartDialog.loading("加载中").large().createAndShow(this,4);
    }

    public void onLoadingMiddleClick(View view) {
        SmartDialog.loading("加载中").middle().createAndShow(this,5);
    }

    public void onLoadingSmallClick(View view) {
        SmartDialog.loading("加载中").small().createAndShow(this,6);
    }

}
