package com.coder.vincent.smart_dialog.notification

import android.app.Activity
import android.content.DialogInterface
import androidx.`annotation`.StringRes
import kotlin.Boolean
import kotlin.Float
import kotlin.Function1
import kotlin.Int
import kotlin.String
import kotlin.Unit

 interface NotificationDialogFacade {
     interface Builder : ConfigSetter<Builder> {
         fun build(activity: Activity): NotificationDialogFacade.Handle
    }

     interface Handle {
         fun show()

         fun dismiss()

         fun cancel()

         fun isShowing(): Boolean

         fun updater(): Updater
    }

     interface Updater : ConfigSetter<Updater> {
         fun commit(): NotificationDialogFacade.Handle
    }

     interface ConfigSetter<T> {
         fun dimBehind(dim: Boolean): T

         fun cancelable(cancelable: Boolean): T

         fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): T

         fun dialogShowListener(onShowListener: DialogInterface.OnShowListener): T

         fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener): T

         fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener): T

         fun title(title: String): T

         fun titleResource(@StringRes titleResource: Int): T

         fun titleStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): T

         fun message(message: String): T

         fun messageResource(@StringRes messageResource: Int): T

         fun messageStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): T

         fun confirmBtnLabel(confirmBtnLabel: String): T

         fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int): T

         fun confirmBtnLabelStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): T

         fun confirmBtnListener(confirmBtnListener: Function1<DialogInterface, Unit>): T
    }
}
