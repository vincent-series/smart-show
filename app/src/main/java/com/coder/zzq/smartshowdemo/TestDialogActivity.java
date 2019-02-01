package com.coder.zzq.smartshowdemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.creator.type.impl.DialogCreatorFactory;
import com.coder.zzq.smartshow.toast.SmartToast;

public class TestDialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);
    }

    private SmartDialog mExampleDialog;

    public void onShowDialogClick(View view) {
        if (mExampleDialog == null) {
            mExampleDialog = new SmartDialog()
                    .dialogCreator(new ExampleDialogCreator())
                    .reuse(true);
        }
        mExampleDialog.show(this);
    }

    private SmartDialog mResetSuccTip;

    public void onNotificationClick(View view) {
        if (mResetSuccTip == null) {
            mResetSuccTip = new SmartDialog()
                    .dialogCreator(DialogCreatorFactory.notification().message("重置成功"))
                    .reuse(true);
        }

        mResetSuccTip.show(this);
    }

    private SmartDialog mCancelConcernDialog;

    public void onEnsureClick(View view) {
        if (mCancelConcernDialog == null) {
            mCancelConcernDialog = new SmartDialog()
                    .dialogCreator(
                            DialogCreatorFactory
                                    .ensure()
                                    .confirmBtn("确定")
                                    .cancelBtn("取消")
                                    .message("确定不再关注此人？")
                    )
                    .reuse(true);
        }
        mCancelConcernDialog.show(this);
    }

    private SmartDialog mEnableDevelopModeDialog;

    public void onEnsureDelayClick(View view) {
        if (mEnableDevelopModeDialog == null) {
            mEnableDevelopModeDialog = new SmartDialog()
                    .dialogCreator(
                            DialogCreatorFactory
                                    .ensure()
                                    .message("确定开启开发者模式？")
                                    .secondsDelayConfirm(10)
                    )
                    .reuse(true);
        }
        mEnableDevelopModeDialog.show(this);
    }

    private SmartDialog mInputSuggestionDialog;

    public void onInputClick(View view) {
        if (mInputSuggestionDialog == null) {
            mInputSuggestionDialog = new SmartDialog()
                    .dialogCreator(
                            DialogCreatorFactory
                                    .input()
                                    .inputAtMost(60)
                                    .hint("输入建议")
                                    .confirmBtn("提交", new DialogBtnClickListener() {

                                        @Override
                                        public void onBtnClick(Dialog dialog, int which, Object data) {
                                            if (data.toString().length() > 60) {
                                                SmartToast.showInCenter("最多只能输入70个字符");
                                                return;
                                            } else {
                                                dialog.dismiss();
                                                SmartToast.showInCenter(data.toString());
                                            }
                                        }
                                    })
                                    .inputCountMarkColor(Utils.getColorFromRes(R.color.colorPrimary))
                    )
                    .reuse(true);
        }

        mInputSuggestionDialog.show(this);
    }

    private SmartDialog mLargeLoadingDialog;

    public void onLoadingLargeClick(View view) {
        if (mLargeLoadingDialog == null) {
            mLargeLoadingDialog = new SmartDialog()
                    .dialogCreator(
                            DialogCreatorFactory
                                    .loading()
                                    .large()
                                    .message("加载中")
                    )
                    .reuse(true);
        }
        mLargeLoadingDialog.show(this);
    }

    SmartDialog mMiddleLoadingDialog;

    public void onLoadingMiddleClick(View view) {
        if (mMiddleLoadingDialog == null) {
            mMiddleLoadingDialog = new SmartDialog();
            mMiddleLoadingDialog.dialogCreator(DialogCreatorFactory.loading().middle().message("加载中"))
                    .reuse(true);
        }
        mMiddleLoadingDialog.show(this);
    }

    private SmartDialog mSmallLoadingDialog;

    public void onLoadingSmallClick(View view) {
        if (mSmallLoadingDialog == null) {
            mSmallLoadingDialog = new SmartDialog()
                    .reuse(false)
                    .dialogCreator(DialogCreatorFactory.loading().small());
        }
        mSmallLoadingDialog.show(this);
    }
}
