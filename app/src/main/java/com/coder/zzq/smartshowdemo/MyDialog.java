package com.coder.zzq.smartshowdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.toast.SmartToast;

public class MyDialog extends SmartDialog {
    /**
     * 必须实现的方法，负责创建内部持有的Dialog。
     *
     * @param activity 创建Dialog所需的activity context
     * @return 返回内部持有的Dialog
     */
    @NonNull
    @Override
    protected Dialog createDialog(Activity activity) {
        //要使用该方法传入的activity创建Dialog

        // 这里可以保证activity不为null且没有正在finish或已经销毁

        //这里要每次返回一个新的Dialog对象，不要做缓存操作，缓存功能已内部处理

        return new AlertDialog.Builder(activity)

                .setMessage("为该库star一下好么")

                .setPositiveButton("好的", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SmartToast.showInCenter("谢谢");

                    }

                })
                .create();
    }

    /**
     * 不是必须重写的方法，默认实现为空，
     * <p>
     * 该方法会在下一次显示Dialog前调用，进行重置操作，如清除出入框的内容
     *
     * @param dialog 内部持有的Dialog
     */
    @Override
    protected void resetDialogWhenShowAgain(Dialog dialog) {
        super.resetDialogWhenShowAgain(dialog);
    }
}
