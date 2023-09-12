package com.coder.vincent.smart_dialog.java;

import android.content.DialogInterface;

import com.coder.vincent.smart_dialog.click_list.ClickedItem;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public interface ItemClickedListener extends Function2<DialogInterface, ClickedItem, Unit> {
    @Override
    default Unit invoke(DialogInterface dialog, ClickedItem clickedItem) {
        onItemClicked(dialog, clickedItem);
        return Unit.INSTANCE;
    }

    void onItemClicked(DialogInterface dialog, ClickedItem clickedItem);
}
