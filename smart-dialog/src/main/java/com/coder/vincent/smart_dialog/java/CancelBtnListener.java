package com.coder.vincent.smart_dialog.java;

import android.content.DialogInterface;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public interface CancelBtnListener extends Function1<DialogInterface, Unit> {
    @Override
    default Unit invoke(DialogInterface dialog) {
        onCancelBtnClicked(dialog);
        return Unit.INSTANCE;
    }

    void onCancelBtnClicked(DialogInterface dialog);
}
