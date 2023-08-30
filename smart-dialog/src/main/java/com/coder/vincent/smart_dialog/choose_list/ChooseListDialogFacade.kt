package com.coder.vincent.smart_dialog.choose_list

import android.app.Activity
import android.content.DialogInterface
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ChooseListDialogFacade {
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

        fun titleStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): T

        fun items(items: List<String>): T

        fun itemCenter(itemCenter: Boolean): T

        fun itemLabelStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): T

        fun iconColor(iconColor: Int): T

        fun iconColorResource(@ColorRes iconColorResource: Int): T

        fun iconPosition(iconPosition: Int): T

        fun singleChoice(singleChoice: Boolean): T

        fun defaultChoosePos(defaultChoosePos: List<Int>): T

        fun confirmBtnLabel(confirmBtnLabel: String): T

        fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int): T

        fun confirmBtnLabelStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): T


        fun confirmBtnListener(confirmBtnListener: Function2<DialogInterface, List<ChosenItem>, Unit>):
                T

        fun cancelBtnLabel(cancelBtnLabel: String): T

        fun cancelBtnLabelStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): T

        fun cancelBtnListener(cancelBtnListener: Function1<DialogInterface, Unit>): T
    }
}
