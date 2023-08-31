package com.coder.vincent.smart_dialog.input_num

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.canShowDialog
import com.coder.vincent.series.common_lib.resourceToString

internal class InputNumberDialogInvoker : InputNumberDialogFacade.Builder,
    InputNumberDialogFacade.Handle {
    private val config: InputNumberDialog.Config = InputNumberDialog.Config()

    private val updater: InputNumberDialogFacade.Updater = InnerClass()

    private val dialogFactory: InputNumberDialogFactory = InputNumberDialogFactory()

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

    override fun build(activity: Activity): InputNumberDialogFacade.Handle {
        if (!activity.canShowDialog()) {
            return this
        }
        dialog = dialogFactory.produceDialog(activity, config)
        dialog?.setOwnerActivity(activity)
        employConfig()
        return this
    }

    override fun updater(): InputNumberDialogFacade.Updater = updater

    override fun dimBehind(dim: Boolean): InputNumberDialogFacade.Builder = this.apply {
        config.dimBehind.update(value = dim, employ = false)
    }

    override fun cancelable(cancelable: Boolean): InputNumberDialogFacade.Builder = this.apply {
        config.cancelable.update(value = cancelable, employ = false)
    }

    override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): InputNumberDialogFacade.Builder =
        this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

    override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
            InputNumberDialogFacade.Builder = this.apply {
        config.dialogShowListener.update(value = onShowListener, employ = false)
    }

    override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
            InputNumberDialogFacade.Builder = this.apply {
        config.dialogDismissListener.update(value = onDismissListener, employ = false)
    }

    override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
            InputNumberDialogFacade.Builder = this.apply {
        config.dialogCancelListener.update(value = onCancelListener, employ = false)
    }

    override fun title(title: String): InputNumberDialogFacade.Builder = this.apply {
        config.title.update(value = title, employ = false)
    }

    override fun titleResource(@StringRes titleResource: Int): InputNumberDialogFacade.Builder =
        this.apply {
            config.title.update(value = titleResource.resourceToString(), employ = false)
        }

    override fun titleStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): InputNumberDialogFacade.Builder = this.apply {
        config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun defaultFilledNumber(defaultFilledNumber: String): InputNumberDialogFacade.Builder =
        this.apply {
            config.defaultFilledNumber.update(value = defaultFilledNumber, employ = false)
        }

    override fun hint(hint: String): InputNumberDialogFacade.Builder = this.apply {
        config.hint.update(value = hint, employ = false)
    }

    override fun hintResource(@StringRes hintResource: Int): InputNumberDialogFacade.Builder =
        this.apply {
            config.hint.update(value = hintResource.resourceToString(), employ = false)
        }

    override fun numberType(numberType: NumberType): InputNumberDialogFacade.Builder = this.apply {
        config.numberType.update(value = numberType, employ = false)
    }

    override fun numberSigned(numberSigned: Boolean): InputNumberDialogFacade.Builder = this.apply {
        config.numberSigned.update(value = numberSigned, employ = false)
    }

    override fun numberUnit(numberUnit: String): InputNumberDialogFacade.Builder = this.apply {
        config.numberUnit.update(value = numberUnit, employ = false)
    }

    override fun numberUnitResource(@StringRes numberUnitResource: Int):
            InputNumberDialogFacade.Builder = this.apply {
        config.numberUnit.update(value = numberUnitResource.resourceToString(), employ = false)
    }

    override fun confirmBtnLabel(confirmBtnLabel: String): InputNumberDialogFacade.Builder =
        this.apply {
            config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
        }

    override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
            InputNumberDialogFacade.Builder = this.apply {
        config.confirmBtnLabel.update(
            value = confirmBtnLabelResource.resourceToString(), employ =
            false
        )
    }

    override fun confirmBtnLabelStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): InputNumberDialogFacade.Builder = this.apply {
        config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun confirmBtnListener(confirmBtnListener: Function2<DialogInterface, String, Unit>):
            InputNumberDialogFacade.Builder = this.apply {
        config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
    }

    override fun cancelBtnLabel(cancelBtnLabel: String): InputNumberDialogFacade.Builder =
        this.apply {
            config.cancelBtnLabel.update(value = cancelBtnLabel, employ = false)
        }

    override fun cancelBtnLabelResource(@StringRes cancelBtnLabelResource: Int):
            InputNumberDialogFacade.Builder = this.apply {
        config.cancelBtnLabel.update(
            value = cancelBtnLabelResource.resourceToString(), employ =
            false
        )
    }

    override fun cancelBtnLabelStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): InputNumberDialogFacade.Builder = this.apply {
        config.cancelBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun cancelBtnListener(cancelBtnListener: Function1<DialogInterface, Unit>):
            InputNumberDialogFacade.Builder = this.apply {
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
        config.defaultFilledNumber.employIfChanged()
        config.hint.employIfChanged()
        config.numberType.employIfChanged()
        config.numberSigned.employIfChanged()
        config.numberUnit.employIfChanged()
        config.confirmBtnLabel.employIfChanged()
        config.confirmBtnLabelStyle.employIfChanged()
        config.confirmBtnListener.employIfChanged()
        config.cancelBtnLabel.employIfChanged()
        config.cancelBtnLabelStyle.employIfChanged()
        config.cancelBtnListener.employIfChanged()
    }

    private inner class InnerClass : InputNumberDialogFacade.Updater {
        override fun dimBehind(dim: Boolean): InputNumberDialogFacade.Updater = this.apply {
            config.dimBehind.update(value = dim, employ = false)
        }

        override fun cancelable(cancelable: Boolean): InputNumberDialogFacade.Updater = this.apply {
            config.cancelable.update(value = cancelable, employ = false)
        }

        override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean):
                InputNumberDialogFacade.Updater = this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

        override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
                InputNumberDialogFacade.Updater = this.apply {
            config.dialogShowListener.update(value = onShowListener, employ = false)
        }

        override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
                InputNumberDialogFacade.Updater = this.apply {
            config.dialogDismissListener.update(value = onDismissListener, employ = false)
        }

        override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
                InputNumberDialogFacade.Updater = this.apply {
            config.dialogCancelListener.update(value = onCancelListener, employ = false)
        }

        override fun title(title: String): InputNumberDialogFacade.Updater = this.apply {
            config.title.update(value = title, employ = false)
        }

        override fun titleResource(@StringRes titleResource: Int): InputNumberDialogFacade.Updater =
            this.apply {
                config.title.update(value = titleResource.resourceToString(), employ = false)
            }

        override fun titleStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): InputNumberDialogFacade.Updater = this.apply {
            config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun defaultFilledNumber(defaultFilledNumber: String): InputNumberDialogFacade.Updater =
            this.apply {
                config.defaultFilledNumber.update(value = defaultFilledNumber, employ = false)
            }

        override fun hint(hint: String): InputNumberDialogFacade.Updater = this.apply {
            config.hint.update(value = hint, employ = false)
        }

        override fun hintResource(@StringRes hintResource: Int): InputNumberDialogFacade.Updater =
            this.apply {
                config.hint.update(value = hintResource.resourceToString(), employ = false)
            }

        override fun numberType(numberType: NumberType): InputNumberDialogFacade.Updater =
            this.apply {
                config.numberType.update(value = numberType, employ = false)
            }

        override fun numberSigned(numberSigned: Boolean): InputNumberDialogFacade.Updater =
            this.apply {
                config.numberSigned.update(value = numberSigned, employ = false)
            }

        override fun numberUnit(numberUnit: String): InputNumberDialogFacade.Updater = this.apply {
            config.numberUnit.update(value = numberUnit, employ = false)
        }

        override fun numberUnitResource(@StringRes numberUnitResource: Int):
                InputNumberDialogFacade.Updater = this.apply {
            config.numberUnit.update(value = numberUnitResource.resourceToString(), employ = false)
        }

        override fun confirmBtnLabel(confirmBtnLabel: String): InputNumberDialogFacade.Updater =
            this.apply {
                config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
            }

        override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
                InputNumberDialogFacade.Updater = this.apply {
            config.confirmBtnLabel.update(
                value = confirmBtnLabelResource.resourceToString(), employ
                = false
            )
        }

        override fun confirmBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): InputNumberDialogFacade.Updater = this.apply {
            config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun confirmBtnListener(confirmBtnListener: Function2<DialogInterface, String, Unit>):
                InputNumberDialogFacade.Updater = this.apply {
            config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
        }

        override fun cancelBtnLabel(cancelBtnLabel: String): InputNumberDialogFacade.Updater =
            this.apply {
                config.cancelBtnLabel.update(value = cancelBtnLabel, employ = false)
            }

        override fun cancelBtnLabelResource(@StringRes cancelBtnLabelResource: Int):
                InputNumberDialogFacade.Updater = this.apply {
            config.cancelBtnLabel.update(
                value = cancelBtnLabelResource.resourceToString(), employ =
                false
            )
        }

        override fun cancelBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): InputNumberDialogFacade.Updater = this.apply {
            config.cancelBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun cancelBtnListener(cancelBtnListener: Function1<DialogInterface, Unit>):
                InputNumberDialogFacade.Updater = this.apply {
            config.cancelBtnListener.update(value = cancelBtnListener, employ = false)
        }

        override fun commit(): InputNumberDialogFacade.Handle {
            employConfig()
            return this@InputNumberDialogInvoker
        }
    }
}
