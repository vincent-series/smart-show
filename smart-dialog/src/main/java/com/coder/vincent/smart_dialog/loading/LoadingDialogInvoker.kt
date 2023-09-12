package com.coder.vincent.smart_dialog.loading

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.canShowDialog
import com.coder.vincent.series.common_lib.resourceToString

internal class LoadingDialogInvoker : LoadingDialogFacade.Builder, LoadingDialogFacade.Handle {
    private val config: LoadingDialog.Config = LoadingDialog.Config()

    private val updater: LoadingDialogFacade.Updater = InnerClass()

    private val dialogFactory: LoadingDialogFactory = LoadingDialogFactory()

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

    override fun build(activity: Activity): LoadingDialogFacade.Handle {
        if (!activity.canShowDialog()) {
            return this
        }
        dialog = dialogFactory.produceDialog(activity, config)
        dialog?.setOwnerActivity(activity)
        employConfig()
        return this
    }

    override fun updater(): LoadingDialogFacade.Updater = updater

    override fun dimBehind(dim: Boolean): LoadingDialogFacade.Builder = this.apply {
        config.dimBehind.update(value = dim, employ = false)
    }

    override fun cancelable(cancelable: Boolean): LoadingDialogFacade.Builder = this.apply {
        config.cancelable.update(value = cancelable, employ = false)
    }

    override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): LoadingDialogFacade.Builder =
        this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

    override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
            LoadingDialogFacade.Builder = this.apply {
        config.dialogShowListener.update(value = onShowListener, employ = false)
    }

    override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
            LoadingDialogFacade.Builder = this.apply {
        config.dialogDismissListener.update(value = onDismissListener, employ = false)
    }

    override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
            LoadingDialogFacade.Builder = this.apply {
        config.dialogCancelListener.update(value = onCancelListener, employ = false)
    }

    override fun message(message: String): LoadingDialogFacade.Builder = this.apply {
        config.message.update(value = message, employ = false)
    }

    override fun messageResource(@StringRes messageResource: Int): LoadingDialogFacade.Builder =
        this.apply {
            config.message.update(value = messageResource.resourceToString(), employ = false)
        }

    override fun messageStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): LoadingDialogFacade.Builder = this.apply {
        config.messageStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun boxSize(boxSize: BoxSize): LoadingDialogFacade.Builder = this.apply {
        config.boxSize.update(value = boxSize, employ = false)
    }

    private fun employConfig() {
        config.dimBehind.employIfChanged()
        config.cancelable.employIfChanged()
        config.cancelOnTouchOutside.employIfChanged()
        config.dialogShowListener.employIfChanged()
        config.dialogDismissListener.employIfChanged()
        config.dialogCancelListener.employIfChanged()
        config.message.employIfChanged()
        config.messageStyle.employIfChanged()
        config.boxSize.employIfChanged()
    }

    private inner class InnerClass : LoadingDialogFacade.Updater {
        override fun dimBehind(dim: Boolean): LoadingDialogFacade.Updater = this.apply {
            config.dimBehind.update(value = dim, employ = false)
        }

        override fun cancelable(cancelable: Boolean): LoadingDialogFacade.Updater = this.apply {
            config.cancelable.update(value = cancelable, employ = false)
        }

        override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): LoadingDialogFacade.Updater =
            this.apply {
                config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
            }

        override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
                LoadingDialogFacade.Updater = this.apply {
            config.dialogShowListener.update(value = onShowListener, employ = false)
        }

        override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
                LoadingDialogFacade.Updater = this.apply {
            config.dialogDismissListener.update(value = onDismissListener, employ = false)
        }

        override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
                LoadingDialogFacade.Updater = this.apply {
            config.dialogCancelListener.update(value = onCancelListener, employ = false)
        }

        override fun message(message: String): LoadingDialogFacade.Updater = this.apply {
            config.message.update(value = message, employ = false)
        }

        override fun messageResource(@StringRes messageResource: Int): LoadingDialogFacade.Updater =
            this.apply {
                config.message.update(value = messageResource.resourceToString(), employ = false)
            }

        override fun messageStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): LoadingDialogFacade.Updater = this.apply {
            config.messageStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun boxSize(boxSize: BoxSize): LoadingDialogFacade.Updater = this.apply {
            config.boxSize.update(value = boxSize, employ = false)
        }

        override fun commit(): LoadingDialogFacade.Handle {
            employConfig()
            return this@LoadingDialogInvoker
        }
    }
}
