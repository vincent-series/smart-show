package com.coder.vincent.smart_dialog

import android.content.DialogInterface
import com.coder.vincent.smart_dialog.choose_list.ChosenItem
import com.coder.vincent.smart_dialog.click_list.ClickedItem

typealias ConfirmBtnListener = (dialog: DialogInterface) -> Unit

class DefaultConfirmBtnListener : ConfirmBtnListener {
    override fun invoke(dialog: DialogInterface) {
        dialog.dismiss()
    }
}

typealias CancelBtnListener = (dialog: DialogInterface) -> Unit

class DefaultCancelBtnListener : CancelBtnListener {
    override fun invoke(dialog: DialogInterface) {
        dialog.dismiss()
    }
}

typealias ItemClickedListener = (dialog: DialogInterface, clickedItem: ClickedItem) -> Unit

typealias ItemChosenListener = (dialog: DialogInterface, chosenItems: List<ChosenItem>) -> Unit

typealias InputTextConfirmListener = (dialog: DialogInterface, content: String) -> Unit

typealias InputNumberConfirmListener = (dialog: DialogInterface, number: String) -> Unit