package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.dialog.creator.type.IEnsureDialogCreator;
import com.coder.zzq.smartshow.dialog.creator.type.ILoadingDialogCreator;
import com.coder.zzq.smartshow.dialog.creator.type.INotificationCreator;
import com.coder.zzq.smartshow.dialog.creator.type.impl.DialogCreatorFactory;

public class TestDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);

    }


    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestDialogActivity.class));
    }

    INotificationCreator mNotificationCreator = DialogCreatorFactory.notification();

    public void onNotificationClick(View view) {
        mNotificationCreator.message("充值成功")
                .title("提示")
                .secondsDelayConfirm(5)
                .createAndShow(this);
    }

    IEnsureDialogCreator mEnsureDialogCreator = DialogCreatorFactory.ensure();

    public void onEnsureClick(View view) {
        mEnsureDialogCreator.confirmBtn("确定", null)
                .secondsDelayConfirm(5)
                .darkAroundWhenShow(false)
                .cancelBtn("取消", null)
                .message("确定不再关注此人？")
                .createAndShow(this);
    }

    public void onEnsureDelayClick(View view) {

    }


    public void onInputClick(View view) {
        DialogCreatorFactory.input()
                .inputAtMost(10)
                .hint("输入姓名")
                .inputCountMarkColor(Color.GREEN)
                .createAndShow(this);
    }

    ILoadingDialogCreator mLoadingDialogCreator = DialogCreatorFactory.loading();

    public void onLoadingLargeClick(View view) {

        mLoadingDialogCreator.message("加载中...").large()
                .createAndShow(this);

    }

    public void onLoadingMiddleClick(View view) {

    }

    public void onLoadingSmallClick(View view) {
    }
}
