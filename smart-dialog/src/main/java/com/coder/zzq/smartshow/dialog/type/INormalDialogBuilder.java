package com.coder.zzq.smartshow.dialog.type;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.DialogContentCallback;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface INormalDialogBuilder<B> {
    int MODE_ONLY_CONFIRM = 0;
    int MODE_BOTN_CONFIRM_AND_CANCEL = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_ONLY_CONFIRM, MODE_BOTN_CONFIRM_AND_CANCEL})
    @interface BUTTON_MODE {
    }

    B title(CharSequence title);

    B buttonMode(@BUTTON_MODE int buttonMode);

    B confirmBtn(CharSequence label, DialogBtnClickListener clickListener);

    B confirmBtnTextStyle(@ColorInt int textColor, float textSizeSp);

    B cancelBtn(CharSequence label, DialogBtnClickListener clickListener);

    B cancelBtnTextStyle(@ColorInt int textColor, float textSizeSp);

    B delaySecondsConfirm(int delaySeconds);

    B cancelable(boolean b);

    B cancelableOnTouchOutside(boolean b);

    B processContent(DialogContentCallback callback);

    boolean createAndShow(Activity activity, int tag);
}
