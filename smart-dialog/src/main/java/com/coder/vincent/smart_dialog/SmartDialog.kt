package com.coder.vincent.smart_dialog

import com.coder.vincent.smart_dialog.choose_list.ChooseListDialogInvoker
import com.coder.vincent.smart_dialog.click_list.ClickListDialogInvoker
import com.coder.vincent.smart_dialog.ensure.EnsureDialogInvoker
import com.coder.vincent.smart_dialog.input_num.InputNumberDialogInvoker
import com.coder.vincent.smart_dialog.input_text.InputTextDialogInvoker
import com.coder.vincent.smart_dialog.loading.LoadingDialogInvoker
import com.coder.vincent.smart_dialog.notification.NotificationDialogInvoker

object SmartDialog {
    @JvmStatic
    fun notification() = NotificationDialogInvoker()

    @JvmStatic
    fun ensure() = EnsureDialogInvoker()

    @JvmStatic
    fun inputText() = InputTextDialogInvoker()

    @JvmStatic
    fun inputNumber() = InputNumberDialogInvoker()

    @JvmStatic
    fun clickList() = ClickListDialogInvoker()

    @JvmStatic
    fun chooseList() = ChooseListDialogInvoker()

    @JvmStatic
    fun loading() = LoadingDialogInvoker()
}