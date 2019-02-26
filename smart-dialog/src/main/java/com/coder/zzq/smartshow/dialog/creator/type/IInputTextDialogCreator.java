package com.coder.zzq.smartshow.dialog.creator.type;

import android.support.annotation.ColorInt;

public interface IInputTextDialogCreator extends ITitleCreator<IInputTextDialogCreator>,
        IConfirmBtnCreator<IInputTextDialogCreator>, ICancelBtnCreator<IInputTextDialogCreator>,
        INormalDialogCreator<IInputTextDialogCreator> {
    int INPUT_NO_LIMIT = -1;

    IInputTextDialogCreator textOfDefaultFill(CharSequence defaultText);

    IInputTextDialogCreator hint(CharSequence hintMsg);

    IInputTextDialogCreator inputAtMost(int num);

    IInputTextDialogCreator inputCountMarkColor(@ColorInt int color);

    IInputTextDialogCreator clearInputPerShow(boolean clear);
}
