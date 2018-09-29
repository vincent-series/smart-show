package com.coder.zzq.smartshow.dialog.type;

import android.support.annotation.IntDef;
import android.view.View;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IDialogShow<Type> {

    int ONLY_ENSURE_BUTTON = 0;
    int BOTH_ENSURE_AND_CANCEL = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ONLY_ENSURE_BUTTON, BOTH_ENSURE_AND_CANCEL})
    @interface ButtonMode {

    }


    Type title(CharSequence titel);

    Type buttonMode(@ButtonMode int mode);

    Type ensureBtn(CharSequence text);

    Type ensureClick(View.OnClickListener listener);

    Type cancelBtn(CharSequence text);

    Type cancelClick(View.OnClickListener listener);

    void show();


    interface DialogBtnProcessor {
        void processBtn(boolean isEnsure, TextView btn);
    }

}
