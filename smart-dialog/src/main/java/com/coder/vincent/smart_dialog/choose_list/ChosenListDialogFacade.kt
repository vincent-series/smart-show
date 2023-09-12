package com.coder.vincent.smart_dialog.choose_list

import android.app.Activity
import android.content.DialogInterface
import androidx.annotation.ArrayRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.IconPosition
import com.coder.vincent.smart_dialog.CancelBtnListener
import com.coder.vincent.smart_dialog.ItemChosenListener

interface ChosenListDialogFacade {
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

        fun items(items: List<String>): T

        fun itemsResource(@ArrayRes itemsResource: Int): T

        fun itemCenter(itemCenter: Boolean): T

        fun itemLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): T

        fun iconColor(iconColor: Int): T

        fun iconColorResource(@ColorRes iconColorResource: Int): T

        fun iconPosition(iconPosition: IconPosition): T

        fun singleChoice(singleChoice: Boolean): T

        fun defaultChosenPos(defaultChosenPos: List<Int>): T

        fun confirmBtnLabel(confirmBtnLabel: String): T

        fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int): T

        fun confirmBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): T


        fun confirmBtnListener(confirmBtnListener: ItemChosenListener):
                T

        fun cancelBtnLabel(cancelBtnLabel: String): T

        fun cancelBtnLabelResource(@StringRes cancelBtnLabelResource: Int): T

        fun cancelBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): T

        fun cancelBtnListener(cancelBtnListener: CancelBtnListener): T
    }
}
