package com.coder.zzq.smartshowdemo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.creator.type.IEnsureDialogCreator;
import com.coder.zzq.smartshow.dialog.creator.type.IInputTextDialogCreator;
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
                .createAndShow(this);
    }

    IEnsureDialogCreator mEnsureDialogCreator = DialogCreatorFactory.ensure();

    public void onEnsureClick(View view) {
        mEnsureDialogCreator.confirmBtn("确定", null)
                .cancelBtn("取消", null)
                .message("确定不再关注此人？")
                .createAndShow(this);
    }

    private IEnsureDialogCreator mEnsureDialogDelay = DialogCreatorFactory.ensure();

    public void onEnsureDelayClick(View view) {
        mEnsureDialogDelay.message("确定开启开发者模式？")
                .secondsDelayConfirm(10)
                .createAndShow(this);
    }

    IInputTextDialogCreator mInputTextDialogCreator = DialogCreatorFactory.input();

    public void onInputClick(View view) {
        mInputTextDialogCreator
                .inputAtMost(70)
                .hint("输入建议")
                .confirmBtn("确定", new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(Dialog dialog, int which, Object data) {
                        data.toString();
                    }
                })
                .inputCountMarkColor(Color.GREEN)
                .createAndShow(this);
    }

    ILoadingDialogCreator mLoadingDialogCreator = DialogCreatorFactory.loading();

    public void onLoadingLargeClick(View view) {

        mLoadingDialogCreator.message("加载中...").large()
                .createAndShow(this);

    }

    ILoadingDialogCreator mMiddleLoading = DialogCreatorFactory.loading();

    public void onLoadingMiddleClick(View view) {
        mMiddleLoading.middle().message("加载中...")
                .createAndShow(this);
    }

    private ILoadingDialogCreator mSmallLoading = DialogCreatorFactory.loading();

    public void onLoadingSmallClick(View view) {
        mSmallLoading.small().createAndShow(this);
    }
}
