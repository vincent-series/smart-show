package com.coder.vincent.smart_dialog.notification

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.canShowDialog
import com.coder.vincent.series.common_lib.resourceToString
import com.coder.vincent.smart_dialog.ConfirmBtnListener

internal class NotificationDialogInvoker : NotificationDialogFacade.Builder,
    NotificationDialogFacade.Handle {
    private val config: NotificationDialog.Config = NotificationDialog.Config()

    private val updater: NotificationDialogFacade.Updater = InnerClass()

    private val dialogFactory: NotificationDialogFactory = NotificationDialogFactory()

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

    override fun build(activity: Activity): NotificationDialogFacade.Handle {
        if (!activity.canShowDialog()) {
            return this
        }
        dialog = dialogFactory.produceDialog(activity, config)
        dialog?.setOwnerActivity(activity)
        employConfig()
        return this
    }

    override fun updater(): NotificationDialogFacade.Updater = updater

    override fun dimBehind(dim: Boolean): NotificationDialogFacade.Builder = this.apply {
        config.dimBehind.update(value = dim, employ = false)
    }

    override fun cancelable(cancelable: Boolean): NotificationDialogFacade.Builder = this.apply {
        config.cancelable.update(value = cancelable, employ = false)
    }

    override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): NotificationDialogFacade.Builder =
        this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

    override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
            NotificationDialogFacade.Builder = this.apply {
        config.dialogShowListener.update(value = onShowListener, employ = false)
    }

    override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
            NotificationDialogFacade.Builder = this.apply {
        config.dialogDismissListener.update(value = onDismissListener, employ = false)
    }

    override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
            NotificationDialogFacade.Builder = this.apply {
        config.dialogCancelListener.update(value = onCancelListener, employ = false)
    }

    override fun title(title: String): NotificationDialogFacade.Builder = this.apply {
        config.title.update(value = title, employ = false)
    }

    override fun titleResource(@StringRes titleResource: Int): NotificationDialogFacade.Builder =
        this.apply {
            config.title.update(value = titleResource.resourceToString(), employ = false)
        }

    override fun titleStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): NotificationDialogFacade.Builder = this.apply {
        config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun message(message: String): NotificationDialogFacade.Builder = this.apply {
        config.message.update(value = message, employ = false)
    }

    override fun messageResource(@StringRes messageResource: Int): NotificationDialogFacade.Builder =
        this.apply {
            config.message.update(value = messageResource.resourceToString(), employ = false)
        }

    override fun messageStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): NotificationDialogFacade.Builder = this.apply {
        config.messageStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun confirmBtnLabel(confirmBtnLabel: String): NotificationDialogFacade.Builder =
        this.apply {
            config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
        }

    override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
            NotificationDialogFacade.Builder = this.apply {
        config.confirmBtnLabel.update(
            value = confirmBtnLabelResource.resourceToString(), employ =
            false
        )
    }

    override fun confirmBtnLabelStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): NotificationDialogFacade.Builder = this.apply {
        config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun confirmBtnListener(confirmBtnListener: ConfirmBtnListener):
            NotificationDialogFacade.Builder = this.apply {
        config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
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
        config.message.employIfChanged()
        config.messageStyle.employIfChanged()
        config.confirmBtnLabel.employIfChanged()
        config.confirmBtnLabelStyle.employIfChanged()
        config.confirmBtnListener.employIfChanged()
    }

    private inner class InnerClass : NotificationDialogFacade.Updater {
        override fun dimBehind(dim: Boolean): NotificationDialogFacade.Updater = this.apply {
            config.dimBehind.update(value = dim, employ = false)
        }

        override fun cancelable(cancelable: Boolean): NotificationDialogFacade.Updater =
            this.apply {
                config.cancelable.update(value = cancelable, employ = false)
            }

        override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean):
                NotificationDialogFacade.Updater = this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

        override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
                NotificationDialogFacade.Updater = this.apply {
            config.dialogShowListener.update(value = onShowListener, employ = false)
        }

        override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
                NotificationDialogFacade.Updater = this.apply {
            config.dialogDismissListener.update(value = onDismissListener, employ = false)
        }

        override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
                NotificationDialogFacade.Updater = this.apply {
            config.dialogCancelListener.update(value = onCancelListener, employ = false)
        }

        override fun title(title: String): NotificationDialogFacade.Updater = this.apply {
            config.title.update(value = title, employ = false)
        }

        override fun titleResource(@StringRes titleResource: Int): NotificationDialogFacade.Updater =
            this.apply {
                config.title.update(value = titleResource.resourceToString(), employ = false)
            }

        override fun titleStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): NotificationDialogFacade.Updater = this.apply {
            config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun message(message: String): NotificationDialogFacade.Updater = this.apply {
            config.message.update(value = message, employ = false)
        }

        override fun messageResource(@StringRes messageResource: Int): NotificationDialogFacade.Updater =
            this.apply {
                config.message.update(value = messageResource.resourceToString(), employ = false)
            }

        override fun messageStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): NotificationDialogFacade.Updater = this.apply {
            config.messageStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun confirmBtnLabel(confirmBtnLabel: String): NotificationDialogFacade.Updater =
            this.apply {
                config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
            }

        override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
                NotificationDialogFacade.Updater = this.apply {
            config.confirmBtnLabel.update(
                value = confirmBtnLabelResource.resourceToString(), employ
                = false
            )
        }

        override fun confirmBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): NotificationDialogFacade.Updater = this.apply {
            config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun confirmBtnListener(confirmBtnListener: ConfirmBtnListener):
                NotificationDialogFacade.Updater = this.apply {
            config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
        }

        override fun commit(): NotificationDialogFacade.Handle {
            employConfig()
            return this@NotificationDialogInvoker
        }
    }
}
