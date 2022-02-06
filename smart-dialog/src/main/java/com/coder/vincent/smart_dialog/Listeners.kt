package com.coder.vincent.smart_dialog

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatDialog
import com.coder.vincent.smart_dialog.choose_list.ChosenItem
import com.coder.vincent.smart_dialog.click_list.ClickedItem

typealias ConfirmBtnListener = (dialog: AppCompatDialog) -> Unit

class DefaultConfirmBtnListener : ConfirmBtnListener {
    override fun invoke(dialog: AppCompatDialog) {
        dialog.dismiss()
    }
}

typealias CancelBtnListener = (dialog: AppCompatDialog) -> Unit

class DefaultCancelBtnListener : CancelBtnListener {
    override fun invoke(dialog: AppCompatDialog) {
        dialog.dismiss()
    }
}

class DefaultInputOnShowListener : DialogInterface.OnShowListener {
    override fun onShow(dialog: DialogInterface) {}
}

typealias ItemClickedListener = (dialog: AppCompatDialog, clickedItem: ClickedItem) -> Unit

typealias ItemChosenListener = (dialog: AppCompatDialog, chosenItems: List<ChosenItem>) -> Unit