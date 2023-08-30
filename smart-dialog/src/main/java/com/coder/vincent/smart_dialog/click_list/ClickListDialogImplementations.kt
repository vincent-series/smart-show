package com.coder.vincent.smart_dialog.click_list

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import androidx.`annotation`.StringRes
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.canShowDialog
import com.coder.vincent.series.common_lib.resourceToString
import kotlin.Boolean
import kotlin.Float
import kotlin.Function2
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.collections.List

internal class ClickListDialogImplementations : ClickListDialogFacade.Builder,
    ClickListDialogFacade.Handle {
    private val config: ClickListDialog.Config = ClickListDialog.Config()

    private val updater: ClickListDialogFacade.Updater = InnerClass()

    private val dialogFactory: ClickListDialogFactory = ClickListDialogFactory()

    private var dialog: Dialog? = null

    override fun show() {
        kotlin.runCatching { if (dialog?.ownerActivity?.canShowDialog() == true) dialog?.show() }
    }

    override fun dismiss() {
        if (isShowing()) {
            kotlin.runCatching { dialog?.dismiss() }
        }
    }

    override fun cancel() {
        dialog?.cancel()
    }

    override fun isShowing(): Boolean = dialog?.isShowing() == true

    override fun build(activity: Activity): ClickListDialogFacade.Handle {
        if (!activity.canShowDialog()) {
            return this
        }
        dialog = dialogFactory.produceDialog(activity, config)
        dialog?.setOwnerActivity(activity)
        employConfig()
        return this
    }

    override fun updater(): ClickListDialogFacade.Updater = updater

    override fun dimBehind(dim: Boolean): ClickListDialogFacade.Builder = this.apply {
        config.dimBehind.update(value = dim, employ = false)
    }

    override fun cancelable(cancelable: Boolean): ClickListDialogFacade.Builder = this.apply {
        config.cancelable.update(value = cancelable, employ = false)
    }

    override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): ClickListDialogFacade.Builder =
        this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

    override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
            ClickListDialogFacade.Builder = this.apply {
        config.dialogShowListener.update(value = onShowListener, employ = false)
    }

    override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
            ClickListDialogFacade.Builder = this.apply {
        config.dialogDismissListener.update(value = onDismissListener, employ = false)
    }

    override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
            ClickListDialogFacade.Builder = this.apply {
        config.dialogCancelListener.update(value = onCancelListener, employ = false)
    }

    override fun title(title: String): ClickListDialogFacade.Builder = this.apply {
        config.title.update(value = title, employ = false)
    }

    override fun titleResource(@StringRes titleResource: Int): ClickListDialogFacade.Builder =
        this.apply {
            config.title.update(value = titleResource.resourceToString(), employ = false)
        }

    override fun titleStyle(
        color: Int?,
        size: Float?,
        bold: Boolean?,
    ): ClickListDialogFacade.Builder = this.apply {
        config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun items(items: List<String>): ClickListDialogFacade.Builder = this.apply {
        config.items.update(value = items, employ = false)
    }

    override fun itemCenter(itemCenter: Boolean): ClickListDialogFacade.Builder = this.apply {
        config.itemCenter.update(value = itemCenter, employ = false)
    }

    override fun itemStyle(
        color: Int?,
        size: Float?,
        bold: Boolean?,
    ): ClickListDialogFacade.Builder = this.apply {
        config.itemStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override
    fun itemClickedListener(itemClickedListener: Function2<DialogInterface, ClickedItem, Unit>):
            ClickListDialogFacade.Builder = this.apply {
        config.itemClickedListener.update(value = itemClickedListener, employ = false)
    }

    private fun employConfig() {
        config.dimBehind.employIfChanged()
        config.cancelable.employIfChanged()
        config.cancelOnTouchOutside.employIfChanged()
        config.dialogShowListener.employIfChanged()
        config.dialogDismissListener.employIfChanged()
        config.dialogCancelListener.employIfChanged()
        config.title.employIfChanged()
        config.titleStyle.employIfChanged()
        config.items.employIfChanged()
        config.itemCenter.employIfChanged()
        config.itemStyle.employIfChanged()
        config.itemClickedListener.employIfChanged()
    }

    private inner class InnerClass : ClickListDialogFacade.Updater {
        override fun dimBehind(dim: Boolean): ClickListDialogFacade.Updater = this.apply {
            config.dimBehind.update(value = dim, employ = false)
        }

        override fun cancelable(cancelable: Boolean): ClickListDialogFacade.Updater = this.apply {
            config.cancelable.update(value = cancelable, employ = false)
        }

        override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): ClickListDialogFacade.Updater =
            this.apply {
                config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
            }

        override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
                ClickListDialogFacade.Updater = this.apply {
            config.dialogShowListener.update(value = onShowListener, employ = false)
        }

        override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
                ClickListDialogFacade.Updater = this.apply {
            config.dialogDismissListener.update(value = onDismissListener, employ = false)
        }

        override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
                ClickListDialogFacade.Updater = this.apply {
            config.dialogCancelListener.update(value = onCancelListener, employ = false)
        }

        override fun title(title: String): ClickListDialogFacade.Updater = this.apply {
            config.title.update(value = title, employ = false)
        }

        override fun titleResource(@StringRes titleResource: Int): ClickListDialogFacade.Updater =
            this.apply {
                config.title.update(value = titleResource.resourceToString(), employ = false)
            }

        override fun titleStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): ClickListDialogFacade.Updater = this.apply {
            config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun items(items: List<String>): ClickListDialogFacade.Updater = this.apply {
            config.items.update(value = items, employ = false)
        }

        override fun itemCenter(itemCenter: Boolean): ClickListDialogFacade.Updater = this.apply {
            config.itemCenter.update(value = itemCenter, employ = false)
        }

        override fun itemStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): ClickListDialogFacade.Updater = this.apply {
            config.itemStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override
        fun itemClickedListener(itemClickedListener: Function2<DialogInterface, ClickedItem, Unit>):
                ClickListDialogFacade.Updater = this.apply {
            config.itemClickedListener.update(value = itemClickedListener, employ = false)
        }

        override fun commit(): ClickListDialogFacade.Handle {
            employConfig()
            return this@ClickListDialogImplementations
        }
    }
}
