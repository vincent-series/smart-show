package com.coder.vincent.smart_dialog.java;

import android.content.DialogInterface;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public interface InputTextConfirmListener extends Function2<DialogInterface, String, Unit> {
    @Override
    default Unit invoke(DialogInterface dialog, String content) {
        onInputTextConfirmed(dialog, content);
        return Unit.INSTANCE;
    }

    void onInputTextConfirmed(DialogInterface dialog, String content);
}
