package com.coder.vincent.smart_dialog.ensure

import android.app.Activity
import android.content.DialogInterface
import androidx.annotation.ColorInt
import androidx.annotation.StringRes

interface EnsureDialogFacade {
    interface Builder : ConfigSetter<Builder> {
        fun build(activity: Activity): Handle
    }

    interface Handle {
        fun show()

        fun dismiss()

        fun cancel()

        fun isShowing(): Boolean

        fun updater(): Updater
    }

    interface Updater : ConfigSetter<Updater> {
        fun commit(): Handle
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
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): T

        fun message(message: String): T

        fun messageResource(@StringRes messageResource: Int): T

        fun messageStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): T

        fun confirmBtnLabel(confirmBtnLabel: String): T

        fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int): T

        fun confirmBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): T

        fun confirmBtnListener(confirmBtnListener: Function1<DialogInterface, Unit>): T

        fun delayToConfirm(delayToConfirm: Int): T

        fun cancelBtnLabel(cancelBtnLabel: String): T

        fun cancelBtnLabelResource(@StringRes cancelBtnLabelResource: Int): T

        fun cancelBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): T

        fun cancelBtnListener(cancelBtnListener: Function1<DialogInterface, Unit>): T
    }
}
