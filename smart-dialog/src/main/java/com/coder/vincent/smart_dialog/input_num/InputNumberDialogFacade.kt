package com.coder.vincent.smart_dialog.input_num

import android.app.Activity
import android.content.DialogInterface
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.coder.vincent.smart_dialog.InputNumberConfirmListener

interface InputNumberDialogFacade {
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

        fun defaultFilledNumber(defaultFilledNumber: String): T

        fun hint(hint: String): T

        fun hintResource(@StringRes hintResource: Int): T

        fun numberType(numberType: NumberType): T

        fun numberSigned(numberSigned: Boolean): T

        fun numberUnit(numberUnit: String): T

        fun numberUnitResource(@StringRes numberUnitResource: Int): T

        fun confirmBtnLabel(confirmBtnLabel: String): T

        fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int): T

        fun confirmBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): T

        fun confirmBtnListener(confirmBtnListener: InputNumberConfirmListener): T

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
