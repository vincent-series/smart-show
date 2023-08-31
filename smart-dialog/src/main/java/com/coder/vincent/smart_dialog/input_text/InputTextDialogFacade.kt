package com.coder.vincent.smart_dialog.input_text

import android.app.Activity
import android.content.DialogInterface
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface InputTextDialogFacade {
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

        fun defaultFilledText(defaultFilledText: String): T

        fun hint(hint: String): T

        fun hintResource(@StringRes hintResource: Int): T

        fun mostInputNum(mostInputNum: Int): T

        fun inputNumMarkColor(inputNumMarkColor: Int): T

        fun inputNumMarkColorResource(@ColorRes inputNumMarkColorResource: Int): T

        fun confirmBtnLabel(confirmBtnLabel: String): T

        fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int): T

        fun confirmBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): T

        fun confirmBtnListener(confirmBtnListener: Function2<DialogInterface, String, Unit>): T

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
