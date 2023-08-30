package com.coder.vincent.smart_dialog.click_list

import android.app.Activity
import android.content.DialogInterface
import androidx.annotation.StringRes

interface ClickListDialogFacade {
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
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): T

        fun items(items: List<String>): T

        fun itemCenter(itemCenter: Boolean): T

        fun itemStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): T


        fun itemClickedListener(itemClickedListener: Function2<DialogInterface, ClickedItem, Unit>):
                T
    }
}
