package com.coder.vincent.smart_dialog.java;

import android.content.DialogInterface;

import com.coder.vincent.smart_dialog.choose_list.ChosenItem;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public interface ItemChosenListener extends Function2<DialogInterface, List<ChosenItem>, Unit> {

    @Override
    default Unit invoke(DialogInterface dialog, List<ChosenItem> chosenItems) {
        onItemChosen(dialog, chosenItems);
        return Unit.INSTANCE;
    }

    void onItemChosen(DialogInterface dialog, List<ChosenItem> chosenItems);
}
