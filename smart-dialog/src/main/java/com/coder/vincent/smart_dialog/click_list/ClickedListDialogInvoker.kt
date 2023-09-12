package com.coder.vincent.smart_dialog.click_list

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.canShowDialog
import com.coder.vincent.series.common_lib.resourceToString
import com.coder.vincent.series.common_lib.resourceToStringArray
import com.coder.vincent.smart_dialog.ItemClickedListener

internal class ClickedListDialogInvoker : ClickedListDialogFacade.Builder,
    ClickedListDialogFacade.Handle {
    private val config: ClickedListDialog.Config = ClickedListDialog.Config()

    private val updater: ClickedListDialogFacade.Updater = InnerClass()

    private val dialogFactory: ClickedListDialogFactory = ClickedListDialogFactory()

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

    override fun isShowing(): Boolean = dialog?.isShowing == true

    override fun build(activity: Activity): ClickedListDialogFacade.Handle {
        if (!activity.canShowDialog()) {
            return this
        }
        dialog = dialogFactory.produceDialog(activity, config)
        dialog?.setOwnerActivity(activity)
        employConfig()
        return this
    }

    override fun updater(): ClickedListDialogFacade.Updater = updater

    override fun dimBehind(dim: Boolean): ClickedListDialogFacade.Builder = this.apply {
        config.dimBehind.update(value = dim, employ = false)
    }

    override fun cancelable(cancelable: Boolean): ClickedListDialogFacade.Builder = this.apply {
        config.cancelable.update(value = cancelable, employ = false)
    }

    override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): ClickedListDialogFacade.Builder =
        this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

    override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
            ClickedListDialogFacade.Builder = this.apply {
        config.dialogShowListener.update(value = onShowListener, employ = false)
    }

    override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
            ClickedListDialogFacade.Builder = this.apply {
        config.dialogDismissListener.update(value = onDismissListener, employ = false)
    }

    override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
            ClickedListDialogFacade.Builder = this.apply {
        config.dialogCancelListener.update(value = onCancelListener, employ = false)
    }

    override fun title(title: String): ClickedListDialogFacade.Builder = this.apply {
        config.title.update(value = title, employ = false)
    }

    override fun titleResource(@StringRes titleResource: Int): ClickedListDialogFacade.Builder =
        this.apply {
            config.title.update(value = titleResource.resourceToString(), employ = false)
        }

    override fun titleStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): ClickedListDialogFacade.Builder = this.apply {
        config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun items(items: List<String>): ClickedListDialogFacade.Builder = this.apply {
        config.items.update(value = items, employ = false)
    }

    override fun itemsResource(itemsResource: Int): ClickedListDialogFacade.Builder = this.apply {
        config.items.update(value = itemsResource.resourceToStringArray(), employ = false)
    }

    override fun itemStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): ClickedListDialogFacade.Builder = this.apply {
        config.itemStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override
    fun itemClickedListener(itemClickedListener: ItemClickedListener):
            ClickedListDialogFacade.Builder = this.apply {
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
        config.itemStyle.employIfChanged()
        config.itemClickedListener.employIfChanged()
    }

    private inner class InnerClass : ClickedListDialogFacade.Updater {
        override fun dimBehind(dim: Boolean): ClickedListDialogFacade.Updater = this.apply {
            config.dimBehind.update(value = dim, employ = false)
        }

        override fun cancelable(cancelable: Boolean): ClickedListDialogFacade.Updater = this.apply {
            config.cancelable.update(value = cancelable, employ = false)
        }

        override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): ClickedListDialogFacade.Updater =
            this.apply {
                config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
            }

        override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
                ClickedListDialogFacade.Updater = this.apply {
            config.dialogShowListener.update(value = onShowListener, employ = false)
        }

        override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
                ClickedListDialogFacade.Updater = this.apply {
            config.dialogDismissListener.update(value = onDismissListener, employ = false)
        }

        override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
                ClickedListDialogFacade.Updater = this.apply {
            config.dialogCancelListener.update(value = onCancelListener, employ = false)
        }

        override fun title(title: String): ClickedListDialogFacade.Updater = this.apply {
            config.title.update(value = title, employ = false)
        }

        override fun titleResource(@StringRes titleResource: Int): ClickedListDialogFacade.Updater =
            this.apply {
                config.title.update(value = titleResource.resourceToString(), employ = false)
            }

        override fun titleStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): ClickedListDialogFacade.Updater = this.apply {
            config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun items(items: List<String>): ClickedListDialogFacade.Updater = this.apply {
            config.items.update(value = items, employ = false)
        }

        override fun itemsResource(itemsResource: Int): ClickedListDialogFacade.Updater = this.apply {
            config.items.update(value = itemsResource.resourceToStringArray(), employ = false)
        }

        override fun itemStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): ClickedListDialogFacade.Updater = this.apply {
            config.itemStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override
        fun itemClickedListener(itemClickedListener: ItemClickedListener):
                ClickedListDialogFacade.Updater = this.apply {
            config.itemClickedListener.update(value = itemClickedListener, employ = false)
        }

        override fun commit(): ClickedListDialogFacade.Handle {
            employConfig()
            return this@ClickedListDialogInvoker
        }
    }
}
