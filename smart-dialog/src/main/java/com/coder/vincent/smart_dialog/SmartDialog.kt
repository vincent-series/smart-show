package com.coder.vincent.smart_dialog

import com.coder.vincent.smart_dialog.choose_list.ChosenListDialogFacade
import com.coder.vincent.smart_dialog.choose_list.ChosenListDialogInvoker
import com.coder.vincent.smart_dialog.click_list.ClickedListDialogFacade
import com.coder.vincent.smart_dialog.click_list.ClickedListDialogInvoker
import com.coder.vincent.smart_dialog.ensure.AcknowledgeDialogFacade
import com.coder.vincent.smart_dialog.ensure.AcknowledgeDialogInvoker
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
    fun builderOfAcknowledge(): AcknowledgeDialogFacade.Builder = AcknowledgeDialogInvoker()

    @JvmStatic
    fun builderOfInputText(): InputTextDialogFacade.Builder = InputTextDialogInvoker()

    @JvmStatic
    fun builderOfInputNumber(): InputNumberDialogFacade.Builder = InputNumberDialogInvoker()

    @JvmStatic
    fun builderOfClickedList(): ClickedListDialogFacade.Builder = ClickedListDialogInvoker()

    @JvmStatic
    fun builderOfChosenList(): ChosenListDialogFacade.Builder = ChosenListDialogInvoker()

    @JvmStatic
    fun builderOfLoading(): LoadingDialogFacade.Builder = LoadingDialogInvoker()
}