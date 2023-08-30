package com.coder.vincent.smart_dialog

import com.coder.vincent.smart_dialog.choose_list.ChooseListDialogFacade
import com.coder.vincent.smart_dialog.choose_list.ChooseListDialogImplementations
import com.coder.vincent.smart_dialog.click_list.ClickListDialogFacade
import com.coder.vincent.smart_dialog.click_list.ClickListDialogImplementations
import com.coder.vincent.smart_dialog.ensure.EnsureDialogFacade
import com.coder.vincent.smart_dialog.ensure.EnsureDialogImplementations
import com.coder.vincent.smart_dialog.input_num.InputNumberDialogFacade
import com.coder.vincent.smart_dialog.input_num.InputNumberDialogImplementations
import com.coder.vincent.smart_dialog.input_text.InputTextDialogFacade
import com.coder.vincent.smart_dialog.input_text.InputTextDialogImplementations
import com.coder.vincent.smart_dialog.loading.LoadingDialogFacade
import com.coder.vincent.smart_dialog.loading.LoadingDialogImplementations
import com.coder.vincent.smart_dialog.notification.NotificationDialogFacade
import com.coder.vincent.smart_dialog.notification.NotificationDialogImplementations

object SmartDialog {
    @JvmStatic
    fun builderOfNotification(): NotificationDialogFacade.Builder =
        NotificationDialogImplementations()

    @JvmStatic
    fun builderOfEnsure(): EnsureDialogFacade.Builder = EnsureDialogImplementations()

    @JvmStatic
    fun builderOfInputText(): InputTextDialogFacade.Builder =
        InputTextDialogImplementations()

    @JvmStatic
    fun builderOfInputNumber(): InputNumberDialogFacade.Builder =
        InputNumberDialogImplementations()


    @JvmStatic
    fun builderOfClickList(): ClickListDialogFacade.Builder =
        ClickListDialogImplementations()

    @JvmStatic
    fun builderOfChooseList(): ChooseListDialogFacade.Builder =
        ChooseListDialogImplementations()

    @JvmStatic
    fun builderOfLoading(): LoadingDialogFacade.Builder = LoadingDialogImplementations()
}