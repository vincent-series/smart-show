package com.coder.vincent.smart_dialog

import com.coder.vincent.smart_dialog.choose_list.ChooseListDialogFacade
import com.coder.vincent.smart_dialog.choose_list.ChooseListDialogInvoker
import com.coder.vincent.smart_dialog.click_list.ClickListDialogFacade
import com.coder.vincent.smart_dialog.click_list.ClickListDialogInvoker
import com.coder.vincent.smart_dialog.ensure.EnsureDialogFacade
import com.coder.vincent.smart_dialog.ensure.EnsureDialogInvoker
import com.coder.vincent.smart_dialog.input_num.InputNumberDialogFacade
import com.coder.vincent.smart_dialog.input_num.InputNumberDialogInvoker
import com.coder.vincent.smart_dialog.input_text.InputTextDialogFacade
import com.coder.vincent.smart_dialog.input_text.InputTextDialogInvoker
import com.coder.vincent.smart_dialog.loading.LoadingDialogFacade
import com.coder.vincent.smart_dialog.loading.LoadingDialogInvoker
import com.coder.vincent.smart_dialog.notification.NotificationDialogFacade
import com.coder.vincent.smart_dialog.notification.NotificationDialogInvoker

object SmartDialog {
    @JvmStatic
    fun builderOfNotification(): NotificationDialogFacade.Builder = NotificationDialogInvoker()

    @JvmStatic
    fun builderOfEnsure(): EnsureDialogFacade.Builder = EnsureDialogInvoker()

    @JvmStatic
    fun builderOfInputText(): InputTextDialogFacade.Builder = InputTextDialogInvoker()

    @JvmStatic
    fun builderOfInputNumber(): InputNumberDialogFacade.Builder = InputNumberDialogInvoker()

    @JvmStatic
    fun builderOfClickList(): ClickListDialogFacade.Builder = ClickListDialogInvoker()

    @JvmStatic
    fun builderOfChooseList(): ChooseListDialogFacade.Builder = ChooseListDialogInvoker()

    @JvmStatic
    fun builderOfLoading(): LoadingDialogFacade.Builder = LoadingDialogInvoker()
}