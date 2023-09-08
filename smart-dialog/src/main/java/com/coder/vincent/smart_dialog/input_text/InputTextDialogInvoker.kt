package com.coder.vincent.smart_dialog.input_text

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.canShowDialog
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToString

internal class InputTextDialogInvoker : InputTextDialogFacade.Builder,
    InputTextDialogFacade.Handle {
    private val config: InputTextDialog.Config = InputTextDialog.Config()

    private val updater: InputTextDialogFacade.Updater = InnerClass()

    private val dialogFactory: InputTextDialogFactory = InputTextDialogFactory()

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

    override fun build(activity: Activity): InputTextDialogFacade.Handle {
        if (!activity.canShowDialog()) {
            return this
        }
        dialog = dialogFactory.produceDialog(activity, config)
        dialog?.setOwnerActivity(activity)
        employConfig()
        return this
    }

    override fun updater(): InputTextDialogFacade.Updater = updater

    override fun dimBehind(dim: Boolean): InputTextDialogFacade.Builder = this.apply {
        config.dimBehind.update(value = dim, employ = false)
    }

    override fun cancelable(cancelable: Boolean): InputTextDialogFacade.Builder = this.apply {
        config.cancelable.update(value = cancelable, employ = false)
    }

    override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): InputTextDialogFacade.Builder =
        this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

    override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
            InputTextDialogFacade.Builder = this.apply {
        config.dialogShowListener.update(value = onShowListener, employ = false)
    }

    override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
            InputTextDialogFacade.Builder = this.apply {
        config.dialogDismissListener.update(value = onDismissListener, employ = false)
    }

    override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
            InputTextDialogFacade.Builder = this.apply {
        config.dialogCancelListener.update(value = onCancelListener, employ = false)
    }

    override fun title(title: String): InputTextDialogFacade.Builder = this.apply {
        config.title.update(value = title, employ = false)
    }

    override fun titleResource(@StringRes titleResource: Int): InputTextDialogFacade.Builder =
        this.apply {
            config.title.update(value = titleResource.resourceToString(), employ = false)
        }

    override fun titleStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): InputTextDialogFacade.Builder = this.apply {
        config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun defaultFilledText(defaultFilledText: String): InputTextDialogFacade.Builder =
        this.apply {
            config.defaultFilledText.update(value = defaultFilledText, employ = false)
        }

    override fun hint(hint: String): InputTextDialogFacade.Builder = this.apply {
        config.hint.update(value = hint, employ = false)
    }

    override fun hintResource(@StringRes hintResource: Int): InputTextDialogFacade.Builder =
        this.apply {
            config.hint.update(value = hintResource.resourceToString(), employ = false)
        }

    override fun mostInputNum(mostInputNum: Int): InputTextDialogFacade.Builder = this.apply {
        config.mostInputNum.update(value = mostInputNum, employ = false)
    }

    override fun inputNumMarkColor(inputNumMarkColor: Int): InputTextDialogFacade.Builder =
        this.apply {
            config.inputNumMarkColor.update(value = inputNumMarkColor, employ = false)
        }

    override fun inputNumMarkColorResource(@ColorRes inputNumMarkColorResource: Int):
            InputTextDialogFacade.Builder = this.apply {
        config.inputNumMarkColor.update(
            value = inputNumMarkColorResource.resourceToColor(),
            employ = false
        )
    }

    override fun confirmBtnLabel(confirmBtnLabel: String): InputTextDialogFacade.Builder =
        this.apply {
            config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
        }

    override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
            InputTextDialogFacade.Builder = this.apply {
        config.confirmBtnLabel.update(
            value = confirmBtnLabelResource.resourceToString(), employ =
            false
        )
    }

    override fun confirmBtnLabelStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): InputTextDialogFacade.Builder = this.apply {
        config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun confirmBtnListener(confirmBtnListener: InputTextConfirmListener):
            InputTextDialogFacade.Builder = this.apply {
        config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
    }

    override fun cancelBtnLabel(cancelBtnLabel: String): InputTextDialogFacade.Builder =
        this.apply {
            config.cancelBtnLabel.update(value = cancelBtnLabel, employ = false)
        }

    override fun cancelBtnLabelResource(@StringRes cancelBtnLabelResource: Int):
            InputTextDialogFacade.Builder = this.apply {
        config.cancelBtnLabel.update(
            value = cancelBtnLabelResource.resourceToString(), employ =
            false
        )
    }

    override fun cancelBtnLabelStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): InputTextDialogFacade.Builder = this.apply {
        config.cancelBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun cancelBtnListener(cancelBtnListener: Function1<DialogInterface, Unit>):
            InputTextDialogFacade.Builder = this.apply {
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
        config.defaultFilledText.employIfChanged()
        config.hint.employIfChanged()
        config.mostInputNum.employIfChanged()
        config.inputNumMarkColor.employIfChanged()
        config.confirmBtnLabel.employIfChanged()
        config.confirmBtnLabelStyle.employIfChanged()
        config.confirmBtnListener.employIfChanged()
        config.cancelBtnLabel.employIfChanged()
        config.cancelBtnLabelStyle.employIfChanged()
        config.cancelBtnListener.employIfChanged()
    }

    private inner class InnerClass : InputTextDialogFacade.Updater {
        override fun dimBehind(dim: Boolean): InputTextDialogFacade.Updater = this.apply {
            config.dimBehind.update(value = dim, employ = false)
        }

        override fun cancelable(cancelable: Boolean): InputTextDialogFacade.Updater = this.apply {
            config.cancelable.update(value = cancelable, employ = false)
        }

        override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): InputTextDialogFacade.Updater =
            this.apply {
                config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
            }

        override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
                InputTextDialogFacade.Updater = this.apply {
            config.dialogShowListener.update(value = onShowListener, employ = false)
        }

        override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
                InputTextDialogFacade.Updater = this.apply {
            config.dialogDismissListener.update(value = onDismissListener, employ = false)
        }

        override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
                InputTextDialogFacade.Updater = this.apply {
            config.dialogCancelListener.update(value = onCancelListener, employ = false)
        }

        override fun title(title: String): InputTextDialogFacade.Updater = this.apply {
            config.title.update(value = title, employ = false)
        }

        override fun titleResource(@StringRes titleResource: Int): InputTextDialogFacade.Updater =
            this.apply {
                config.title.update(value = titleResource.resourceToString(), employ = false)
            }

        override fun titleStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): InputTextDialogFacade.Updater = this.apply {
            config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun defaultFilledText(defaultFilledText: String): InputTextDialogFacade.Updater =
            this.apply {
                config.defaultFilledText.update(value = defaultFilledText, employ = false)
            }

        override fun hint(hint: String): InputTextDialogFacade.Updater = this.apply {
            config.hint.update(value = hint, employ = false)
        }

        override fun hintResource(@StringRes hintResource: Int): InputTextDialogFacade.Updater =
            this.apply {
                config.hint.update(value = hintResource.resourceToString(), employ = false)
            }

        override fun mostInputNum(mostInputNum: Int): InputTextDialogFacade.Updater = this.apply {
            config.mostInputNum.update(value = mostInputNum, employ = false)
        }

        override fun inputNumMarkColor(inputNumMarkColor: Int): InputTextDialogFacade.Updater =
            this.apply {
                config.inputNumMarkColor.update(value = inputNumMarkColor, employ = false)
            }

        override fun inputNumMarkColorResource(@ColorRes inputNumMarkColorResource: Int):
                InputTextDialogFacade.Updater = this.apply {
            config.inputNumMarkColor.update(
                value = inputNumMarkColorResource.resourceToColor(),
                employ = false
            )
        }

        override fun confirmBtnLabel(confirmBtnLabel: String): InputTextDialogFacade.Updater =
            this.apply {
                config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
            }

        override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
                InputTextDialogFacade.Updater = this.apply {
            config.confirmBtnLabel.update(
                value = confirmBtnLabelResource.resourceToString(), employ
                = false
            )
        }

        override fun confirmBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): InputTextDialogFacade.Updater = this.apply {
            config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun confirmBtnListener(confirmBtnListener: InputTextConfirmListener):
                InputTextDialogFacade.Updater = this.apply {
            config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
        }

        override fun cancelBtnLabel(cancelBtnLabel: String): InputTextDialogFacade.Updater =
            this.apply {
                config.cancelBtnLabel.update(value = cancelBtnLabel, employ = false)
            }

        override fun cancelBtnLabelResource(@StringRes cancelBtnLabelResource: Int):
                InputTextDialogFacade.Updater = this.apply {
            config.cancelBtnLabel.update(
                value = cancelBtnLabelResource.resourceToString(), employ =
                false
            )
        }

        override fun cancelBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): InputTextDialogFacade.Updater = this.apply {
            config.cancelBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun cancelBtnListener(cancelBtnListener: Function1<DialogInterface, Unit>):
                InputTextDialogFacade.Updater = this.apply {
            config.cancelBtnListener.update(value = cancelBtnListener, employ = false)
        }

        override fun commit(): InputTextDialogFacade.Handle {
            employConfig()
            return this@InputTextDialogInvoker
        }
    }
}
