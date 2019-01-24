package com.coder.zzq.smartshow.dialog.creator.type;

import android.support.annotation.ColorInt;

public interface IInputTextDialogCreator extends ITitleCreator<IInputTextDialogCreator>,
        IConfirmBtnCreator<IInputTextDialogCreator>, ICancelBtnCreator<IInputTextDialogCreator>,
        INormalDialogCreator<IInputTextDialogCreator> {
    IInputTextDialogCreator hint(CharSequence hintMsg);

    IInputTextDialogCreator inputAtMost(int num);

    IInputTextDialogCreator inputCountMarkColor(@ColorInt int color);
}
