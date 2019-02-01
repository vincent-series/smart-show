package com.coder.zzq.smartshowdemo;

import android.app.Activity;
import android.app.Dialog;

import com.coder.zzq.smartshow.dialog.creator.type.impl.DialogCreator;

public class ExampleDialogCreator extends DialogCreator {
    /**
     * 抽象方法，必须实现
     *
     * @param activity
     * @return
     */
    @Override
    public Dialog createDialog(Activity activity) {
        //创建Dialog，在这里可以保证activity不为null，并且没有destroyed或isFinishing
        Dialog dialog = null;
//        ...
        return dialog;
    }

    /**
     * 非抽象方法，默认实现为空，可选择性覆写，用于Dialog每次显示前的一些重置工作，例如EditText清空等
     *
     * @param dialog
     */
    @Override
    public void resetDialogPerShow(Dialog dialog) {
        super.resetDialogPerShow(dialog);

    }
}
