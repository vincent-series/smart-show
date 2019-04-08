package com.coder.zzq.smartshowdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.ChooseListDialog;
import com.coder.zzq.smartshow.dialog.ChooseResult;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.EnsureDialog;
import com.coder.zzq.smartshow.dialog.InputTextDialog;
import com.coder.zzq.smartshow.dialog.LargeLoadingDialog;
import com.coder.zzq.smartshow.dialog.MiddleLoadingDialog;
import com.coder.zzq.smartshow.dialog.NotificationDialog;
import com.coder.zzq.smartshow.dialog.SmallLoadingDialog;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.toast.SmartToast;

import java.util.Arrays;

public class TestDialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);
    }

    private SmartDialog mExampleDialog;

    public void onShowDialogClick(View view) {

    }

    public NotificationDialog mNotificationDialog;

    public void onNotificationClick(View view) {
        if (mCancelFollowDialog == null) {
            mCancelFollowDialog = new EnsureDialog();
        }
        mCancelFollowDialog.message("爱你")
                .title(null)
                .darkAroundWhenShow(true)
                .windowBackground(R.drawable.smart_show_round_dialog_bg)
                .cancelable(true)
                .secondsDelayConfirm(5)
                .confirmBtn("确定不", new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(AppCompatDialog dialog, int which, Object data) {
                        SmartToast.showInCenter("确定");
                    }
                })
                .cancelBtn("djfal")
                .cancelableOnTouchOutside(false)
                .dialogShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        EasyLogger.d("show");
                    }
                })
                .dialogDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        EasyLogger.d("dismiss");
                    }
                })
                .dialogCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        EasyLogger.d("cancel");
                    }
                })
                .apply()
                .showInActivity(this);
    }

    private EnsureDialog mCancelFollowDialog;

    SmartDialog<AlertDialog> mSmartDialog;

    public void onEnsureClick(View view) {
        mCancelFollowDialog.message("重置成功")
                .darkAroundWhenShow(true)
                .cancelable(true)
                .title("你好")
                .secondsDelayConfirm(10)
                .confirmBtn("ok", new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(AppCompatDialog dialog, int which, Object data) {
                        SmartToast.showInCenter("ok");
                    }
                })
                .cancelableOnTouchOutside(true)
                .dialogShowListener(null)
                .dialogDismissListener(null)
                .dialogCancelListener(null)
                .cancelBtn("cancel", Utils.getColorFromRes(R.color.colorAccent),
                        new DialogBtnClickListener() {
                            @Override
                            public void onBtnClick(AppCompatDialog dialog, int which, Object data) {
                                SmartToast.showInCenter("cancelBtnClick");
                            }
                        })
                .apply()
                .showInActivity(this);
//        if (mCancelConcernDialog == null) {
//            mCancelConcernDialog = new EnsureDialog();
//        }
//        mCancelConcernDialog.message("确定不再关注此人？")
//                .confirmBtn("确定")
//                .cancelBtn("取消")
//                .showInActivity(this);
//        mNotificationDialog.message("你好")
//                .confirmBtn("好的")
//                .apply()
//                .showInActivity(this);
//        if (mSmartDialog == null) {
//            mSmartDialog = new SmartDialog<AlertDialog>() {
//                @NonNull
//                @Override
//                protected AlertDialog createDialog(Activity activity) {
//                    return new AlertDialog.Builder(activity)
//                            .setItems(new String[]{
//                                    "周一",
//                                    "周二",
//                                    "周三"
//                            }, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .setTitle("提示")
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    SmartToast.showInCenter("已退出");
//                                }
//                            })
//                            .create();
//                }
//            };
//        }
//        mSmartDialog.showInActivity(this);
    }

    private EnsureDialog mEnableDevelopModeDialog;

    public void onEnsureDelayClick(View view) {
        if (mEnableDevelopModeDialog == null) {
            mEnableDevelopModeDialog = new EnsureDialog();
        }
        mEnableDevelopModeDialog.message("确定开启开发者模式?")
                .secondsDelayConfirm(10)
                .showInActivity(this);
    }

    private InputTextDialog mInputSuggestionDialog;

    public void onInputClick(View view) {
        if (mInputSuggestionDialog == null) {
            mInputSuggestionDialog = new InputTextDialog();
        }

        mInputSuggestionDialog.inputAtMost(30)
                .hint("输入建议")
                .darkAroundWhenShow(true)
                .textOfDefaultFill("我提个建议")
                .clearInputPerShow(false)
                .confirmBtn("提交", new DialogBtnClickListener() {

                    @Override
                    public void onBtnClick(AppCompatDialog dialog, int which, Object data) {
                        if (data.toString().length() > 30) {
                            SmartToast.showInCenter("最多只能输入30个字符");
                            return;
                        } else {
                            dialog.dismiss();
                            SmartToast.showInCenter(data.toString());
                        }
                    }
                })
                .inputCountMarkColor(Utils.getColorFromRes(R.color.colorPrimary))
                .apply()
                .showInActivity(this);
    }

    private LargeLoadingDialog mLargeLoadingDialog;

    public void onLoadingLargeClick(View view) {
//        if (mLargeLoadingDialog == null) {
//            mLargeLoadingDialog = new LargeLoadingDialog();
//        }
//        mLargeLoadingDialog
//                .showInActivity(this);
        if (mInputSuggestionDialog == null) {
            mInputSuggestionDialog = new InputTextDialog();
        }

        mInputSuggestionDialog.inputAtMost(-1)
                .title("你好")
                .darkAroundWhenShow(false)
                .hint("sdfads")
                .textOfDefaultFill("")
                .clearInputPerShow(true)
                .confirmBtn("ok", new DialogBtnClickListener() {

                    @Override
                    public void onBtnClick(AppCompatDialog dialog, int which, Object data) {
                        if (data.toString().length() > 30) {
                            SmartToast.showInCenter("字符");
                            return;
                        } else {
                            dialog.dismiss();
                            SmartToast.showInCenter(data.toString());
                        }
                    }
                })
                .cancelBtn("cancel", new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(AppCompatDialog dialog, int which, Object data) {
                        dialog.dismiss();
                        SmartToast.showInCenter("cancel");
                    }
                })
                .inputCountMarkColor(Utils.getColorFromRes(R.color.colorAccent))
                .apply()
                .showInActivity(this);
    }

    MiddleLoadingDialog mMiddleLoadingDialog;

    public void onLoadingMiddleClick(View view) {
        if (mMiddleLoadingDialog == null) {
            mMiddleLoadingDialog = new MiddleLoadingDialog();
        }
        mMiddleLoadingDialog.message("加载中")
                .showInActivity(this);
    }

    SmallLoadingDialog mSmallLoadingDialog;

    public void onLoadingSmallClick(View view) {
        if (mSmallLoadingDialog == null) {
            mSmallLoadingDialog = new SmallLoadingDialog();
        }
        mSmallLoadingDialog.showInActivity(this);
    }

    ChooseListDialog mChooseListDialog;

    public void onSingleChooseListClick(View view) {
        getChooseListDialog()
                .title("请选择人物")
                .items(new String[]{
                        "张飞",
                        "关羽",
                        "刘备",
                        "诸葛亮",
                        "周瑜",
                        "孙权",
                        "吕布"
                })
                .defaultChoosePos(4)
                .checkMarkPos(Gravity.LEFT)
                .choiceMode(ChooseListDialog.CHOICE_MODE_SINGLE)
                .checkMarkColorRes(R.color.colorAccent)
                .apply()
                .showInActivity(this);
    }

    public void onMultipleChooseListClick(View view) {
        getChooseListDialog()
                .title("请选择日期")
                .items(new String[]{
                        "周一",
                        "周二",
                        "周三",
                        "周四",
                        "周五",
                        "周六",
                        "周日"
                })
                .checkMarkPos(Gravity.LEFT)
                .defaultChoosePos(1, 2)
                .useCubeMarkWhenMultipleChoice(true)
                .choiceMode(ChooseListDialog.CHOICE_MODE_MULTIPLE)
                .checkMarkColorRes(R.color.colorPrimary)
                .keepChosenPosByLast(true)
                .confirmBtn("嗯嗯", new DialogBtnClickListener() {
                    @Override
                    public void onBtnClick(AppCompatDialog dialog, int which, Object data) {
                        SmartToast.showInCenter(Arrays
                                .toString(((ChooseResult) data).getChooseItems()));
                    }
                })
                .apply()
                .showInActivity(this);
    }

    public ChooseListDialog getChooseListDialog() {
        if (mChooseListDialog == null) {
            mChooseListDialog = new ChooseListDialog();
        }
        return mChooseListDialog;
    }
}
