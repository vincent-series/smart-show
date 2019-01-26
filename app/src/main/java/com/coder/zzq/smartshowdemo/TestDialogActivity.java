package com.coder.zzq.smartshowdemo;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.creator.type.IEnsureDialogCreator;
import com.coder.zzq.smartshow.dialog.creator.type.IInputTextDialogCreator;
import com.coder.zzq.smartshow.dialog.creator.type.ILoadingDialogCreator;
import com.coder.zzq.smartshow.dialog.creator.type.INotificationDialogCreator;
import com.coder.zzq.smartshow.dialog.creator.type.impl.DialogCreatorFactory;
import com.coder.zzq.smartshow.toast.SmartToast;

public class TestDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);
        onBtnClick(null);

    }

    private ILoadingDialogCreator mLoadingDialogCreator = DialogCreatorFactory.loading();

    public void onBtnClick(View view) {
        mLoadingLarge.message("加载中").large().createAndShow(this);
    }

    INotificationDialogCreator mNotificationCreator = DialogCreatorFactory.notification();

    public void onNotificationClick(View view) {
        mNotificationCreator.message("充值成功")
                .title("提示")
                .confirmBtnTextStyle(Color.GREEN,20,true)
                .titleStyle(Color.GREEN,20,true)
                .createAndShow(this);
    }

    IEnsureDialogCreator mEnsureDialogCreator = DialogCreatorFactory.ensure();

    public void onEnsureClick(View view) {
        mEnsureDialogCreator.confirmBtn("确定")
                .cancelBtn("取消")
                .cancelBtnTextStyle(Color.GREEN,20,true)
                .messageStyle(Color.parseColor("#ff0000"),16,true)
                .message("确定不再关注此人？")
                .createAndShow(this);
    }

    private IEnsureDialogCreator mEnsureDialogDelay = DialogCreatorFactory.ensure();

    public void onEnsureDelayClick(View view) {
        mEnsureDialogDelay.message("确定开启开发者模式？")
                .secondsDelayConfirm(10)
                .createAndShow(this);
    }

    private IInputTextDialogCreator mInputTextDialogCreator = DialogCreatorFactory.input();

    public void onInputClick(View view) {
        mInputTextDialogCreator
                .inputAtMost(60)
                .hint("输入建议")
                .titleStyle(Color.RED,20,true)
                .confirmBtnTextStyle(Color.GREEN,20,true)
                .cancelBtnTextStyle(Color.RED,20,true)
                .confirmBtn("确定", new DialogBtnClickListener() {

                    @Override
                    public void onBtnClick(Dialog dialog, int which, Object data) {
                        if (data.toString().length() > 60) {
                            SmartToast.showInCenter("最多只能输入70个字符");
                            return;
                        } else {
                            dialog.dismiss();
                            //do something
                        }
                    }
                })
                .inputCountMarkColor(Utils.getColorFromRes(R.color.colorPrimary))
                .createAndShow(this);
    }

    ILoadingDialogCreator mLoadingLarge = DialogCreatorFactory.loading();

    public void onLoadingLargeClick(View view) {
        mLoadingLarge.message("加载中...").large()
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
