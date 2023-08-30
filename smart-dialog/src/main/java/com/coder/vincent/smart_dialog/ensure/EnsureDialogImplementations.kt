package com.coder.vincent.smart_dialog.ensure

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.canShowDialog
import com.coder.vincent.series.common_lib.resourceToString

internal class EnsureDialogImplementations : EnsureDialogFacade.Builder, EnsureDialogFacade.Handle {
    private val config: EnsureDialog.Config = EnsureDialog.Config()

    private val updater: EnsureDialogFacade.Updater = InnerClass()

    private val dialogFactory: EnsureDialogFactory = EnsureDialogFactory()

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

    override fun build(activity: Activity): EnsureDialogFacade.Handle {
        if (!activity.canShowDialog()) {
            return this
        }
        dialog = dialogFactory.produceDialog(activity, config)
        dialog?.setOwnerActivity(activity)
        employConfig()
        return this
    }

    override fun updater(): EnsureDialogFacade.Updater = updater

    override fun dimBehind(dim: Boolean): EnsureDialogFacade.Builder = this.apply {
        config.dimBehind.update(value = dim, employ = false)
    }

    override fun cancelable(cancelable: Boolean): EnsureDialogFacade.Builder = this.apply {
        config.cancelable.update(value = cancelable, employ = false)
    }

    override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): EnsureDialogFacade.Builder =
        this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

    override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
            EnsureDialogFacade.Builder = this.apply {
        config.dialogShowListener.update(value = onShowListener, employ = false)
    }

    override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
            EnsureDialogFacade.Builder = this.apply {
        config.dialogDismissListener.update(value = onDismissListener, employ = false)
    }

    override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
            EnsureDialogFacade.Builder = this.apply {
        config.dialogCancelListener.update(value = onCancelListener, employ = false)
    }

    override fun title(title: String): EnsureDialogFacade.Builder = this.apply {
        config.title.update(value = title, employ = false)
    }

    override fun titleResource(@StringRes titleResource: Int): EnsureDialogFacade.Builder =
        this.apply {
            config.title.update(value = titleResource.resourceToString(), employ = false)
        }

    override fun titleStyle(
        color: Int?,
        size: Float?,
        bold: Boolean?,
    ): EnsureDialogFacade.Builder = this.apply {
        config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun message(message: String): EnsureDialogFacade.Builder = this.apply {
        config.message.update(value = message, employ = false)
    }

    override fun messageResource(@StringRes messageResource: Int): EnsureDialogFacade.Builder =
        this.apply {
            config.message.update(value = messageResource.resourceToString(), employ = false)
        }

    override fun messageStyle(
        color: Int?,
        size: Float?,
        bold: Boolean?,
    ): EnsureDialogFacade.Builder = this.apply {
        config.messageStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun confirmBtnLabel(confirmBtnLabel: String): EnsureDialogFacade.Builder = this.apply {
        config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
    }

    override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
            EnsureDialogFacade.Builder = this.apply {
        config.confirmBtnLabel.update(
            value = confirmBtnLabelResource.resourceToString(), employ =
            false
        )
    }

    override fun confirmBtnLabelStyle(
        color: Int?,
        size: Float?,
        bold: Boolean?,
    ): EnsureDialogFacade.Builder = this.apply {
        config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun confirmBtnListener(confirmBtnListener: Function1<DialogInterface, Unit>):
            EnsureDialogFacade.Builder = this.apply {
        config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
    }

    override fun delayToConfirm(delayToConfirm: Int): EnsureDialogFacade.Builder = this.apply {
        config.delayToConfirm.update(value = delayToConfirm, employ = false)
    }

    override fun cancelBtnLabel(cancelBtnLabel: String): EnsureDialogFacade.Builder = this.apply {
        config.cancelBtnLabel.update(value = cancelBtnLabel, employ = false)
    }

    override fun cancelBtnLabelResource(@StringRes cancelBtnLabelResource: Int):
            EnsureDialogFacade.Builder = this.apply {
        config.cancelBtnLabel.update(
            value = cancelBtnLabelResource.resourceToString(), employ =
            false
        )
    }

    override fun cancelBtnLabelStyle(
        color: Int?,
        size: Float?,
        bold: Boolean?,
    ): EnsureDialogFacade.Builder = this.apply {
        config.cancelBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun cancelBtnListener(cancelBtnListener: Function1<DialogInterface, Unit>):
            EnsureDialogFacade.Builder = this.apply {
        config.cancelBtnListener.update(value = cancelBtnListener, employ = false)
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
        config.delayToConfirm.employIfChanged()
        config.cancelBtnLabel.employIfChanged()
        config.cancelBtnLabelStyle.employIfChanged()
        config.cancelBtnListener.employIfChanged()
    }

    private inner class InnerClass : EnsureDialogFacade.Updater {
        override fun dimBehind(dim: Boolean): EnsureDialogFacade.Updater = this.apply {
            config.dimBehind.update(value = dim, employ = false)
        }

        override fun cancelable(cancelable: Boolean): EnsureDialogFacade.Updater = this.apply {
            config.cancelable.update(value = cancelable, employ = false)
        }

        override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): EnsureDialogFacade.Updater =
            this.apply {
                config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
            }

        override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
                EnsureDialogFacade.Updater = this.apply {
            config.dialogShowListener.update(value = onShowListener, employ = false)
        }

        override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
                EnsureDialogFacade.Updater = this.apply {
            config.dialogDismissListener.update(value = onDismissListener, employ = false)
        }

        override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
                EnsureDialogFacade.Updater = this.apply {
            config.dialogCancelListener.update(value = onCancelListener, employ = false)
        }

        override fun title(title: String): EnsureDialogFacade.Updater = this.apply {
            config.title.update(value = title, employ = false)
        }

        override fun titleResource(@StringRes titleResource: Int): EnsureDialogFacade.Updater =
            this.apply {
                config.title.update(value = titleResource.resourceToString(), employ = false)
            }

        override fun titleStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): EnsureDialogFacade.Updater = this.apply {
            config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun message(message: String): EnsureDialogFacade.Updater = this.apply {
            config.message.update(value = message, employ = false)
        }

        override fun messageResource(@StringRes messageResource: Int): EnsureDialogFacade.Updater =
            this.apply {
                config.message.update(value = messageResource.resourceToString(), employ = false)
            }

        override fun messageStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): EnsureDialogFacade.Updater = this.apply {
            config.messageStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun confirmBtnLabel(confirmBtnLabel: String): EnsureDialogFacade.Updater =
            this.apply {
                config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
            }

        override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
                EnsureDialogFacade.Updater = this.apply {
            config.confirmBtnLabel.update(
                value = confirmBtnLabelResource.resourceToString(), employ
                = false
            )
        }

        override fun confirmBtnLabelStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): EnsureDialogFacade.Updater = this.apply {
            config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun confirmBtnListener(confirmBtnListener: Function1<DialogInterface, Unit>):
                EnsureDialogFacade.Updater = this.apply {
            config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
        }

        override fun delayToConfirm(delayToConfirm: Int): EnsureDialogFacade.Updater = this.apply {
            config.delayToConfirm.update(value = delayToConfirm, employ = false)
        }

        override fun cancelBtnLabel(cancelBtnLabel: String): EnsureDialogFacade.Updater =
            this.apply {
                config.cancelBtnLabel.update(value = cancelBtnLabel, employ = false)
            }

        override fun cancelBtnLabelResource(@StringRes cancelBtnLabelResource: Int):
                EnsureDialogFacade.Updater = this.apply {
            config.cancelBtnLabel.update(
                value = cancelBtnLabelResource.resourceToString(), employ =
                false
            )
        }

        override fun cancelBtnLabelStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): EnsureDialogFacade.Updater = this.apply {
            config.cancelBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun cancelBtnListener(cancelBtnListener: Function1<DialogInterface, Unit>):
                EnsureDialogFacade.Updater = this.apply {
            config.cancelBtnListener.update(value = cancelBtnListener, employ = false)
        }

        override fun commit(): EnsureDialogFacade.Handle {
            employConfig()
            return this@EnsureDialogImplementations
        }
    }
}
