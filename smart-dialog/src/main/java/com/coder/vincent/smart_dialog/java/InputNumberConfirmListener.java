package com.coder.vincent.smart_dialog.java;

import android.content.DialogInterface;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public interface InputNumberConfirmListener extends Function2<DialogInterface, String, Unit> {
    @Override
    default Unit invoke(DialogInterface dialog, String number) {
        onInputNumberConfirmed(dialog, number);
        return Unit.INSTANCE;
    }

    void onInputNumberConfirmed(DialogInterface dialog, String number);
}
