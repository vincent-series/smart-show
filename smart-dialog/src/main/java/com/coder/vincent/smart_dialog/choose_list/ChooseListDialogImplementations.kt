package com.coder.vincent.smart_dialog.choose_list

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.canShowDialog
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToString

internal class ChooseListDialogImplementations : ChooseListDialogFacade.Builder,
    ChooseListDialogFacade.Handle {
    private val config: ChooseListDialog.Config = ChooseListDialog.Config()

    private val updater: ChooseListDialogFacade.Updater = InnerClass()

    private val dialogFactory: ChooseListDialogFactory = ChooseListDialogFactory()

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

    override fun build(activity: Activity): ChooseListDialogFacade.Handle {
        if (!activity.canShowDialog()) {
            return this
        }
        dialog = dialogFactory.produceDialog(activity, config)
        dialog?.setOwnerActivity(activity)
        employConfig()
        return this
    }

    override fun updater(): ChooseListDialogFacade.Updater = updater

    override fun dimBehind(dim: Boolean): ChooseListDialogFacade.Builder = this.apply {
        config.dimBehind.update(value = dim, employ = false)
    }

    override fun cancelable(cancelable: Boolean): ChooseListDialogFacade.Builder = this.apply {
        config.cancelable.update(value = cancelable, employ = false)
    }

    override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): ChooseListDialogFacade.Builder =
        this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

    override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
            ChooseListDialogFacade.Builder = this.apply {
        config.dialogShowListener.update(value = onShowListener, employ = false)
    }

    override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
            ChooseListDialogFacade.Builder = this.apply {
        config.dialogDismissListener.update(value = onDismissListener, employ = false)
    }

    override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
            ChooseListDialogFacade.Builder = this.apply {
        config.dialogCancelListener.update(value = onCancelListener, employ = false)
    }

    override fun title(title: String): ChooseListDialogFacade.Builder = this.apply {
        config.title.update(value = title, employ = false)
    }

    override fun titleStyle(
        color: Int?,
        size: Float?,
        bold: Boolean?,
    ): ChooseListDialogFacade.Builder = this.apply {
        config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun items(items: List<String>): ChooseListDialogFacade.Builder = this.apply {
        config.items.update(value = items, employ = false)
    }

    override fun itemCenter(itemCenter: Boolean): ChooseListDialogFacade.Builder = this.apply {
        config.itemCenter.update(value = itemCenter, employ = false)
    }

    override fun itemLabelStyle(
        color: Int?,
        size: Float?,
        bold: Boolean?,
    ): ChooseListDialogFacade.Builder = this.apply {
        config.itemLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun iconColor(iconColor: Int): ChooseListDialogFacade.Builder = this.apply {
        config.iconColor.update(value = iconColor, employ = false)
    }

    override fun iconColorResource(@ColorRes iconColorResource: Int): ChooseListDialogFacade.Builder =
        this.apply {
            config.iconColor.update(value = iconColorResource.resourceToColor(), employ = false)
        }

    override fun iconPosition(iconPosition: Int): ChooseListDialogFacade.Builder = this.apply {
        config.iconPosition.update(value = iconPosition, employ = false)
    }

    override fun singleChoice(singleChoice: Boolean): ChooseListDialogFacade.Builder = this.apply {
        config.singleChoice.update(value = singleChoice, employ = false)
    }

    override fun defaultChoosePos(defaultChoosePos: List<Int>): ChooseListDialogFacade.Builder =
        this.apply {
            config.defaultChoosePos.update(value = defaultChoosePos, employ = false)
        }

    override fun confirmBtnLabel(confirmBtnLabel: String): ChooseListDialogFacade.Builder =
        this.apply {
            config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
        }

    override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
            ChooseListDialogFacade.Builder = this.apply {
        config.confirmBtnLabel.update(
            value = confirmBtnLabelResource.resourceToString(), employ =
            false
        )
    }

    override fun confirmBtnLabelStyle(
        color: Int?,
        size: Float?,
        bold: Boolean?,
    ): ChooseListDialogFacade.Builder = this.apply {
        config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override
    fun confirmBtnListener(confirmBtnListener: Function2<DialogInterface, List<ChosenItem>, Unit>):
            ChooseListDialogFacade.Builder = this.apply {
        config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
    }

    override fun cancelBtnLabel(cancelBtnLabel: String): ChooseListDialogFacade.Builder =
        this.apply {
            config.cancelBtnLabel.update(value = cancelBtnLabel, employ = false)
        }

    override fun cancelBtnLabelStyle(
        color: Int?,
        size: Float?,
        bold: Boolean?,
    ): ChooseListDialogFacade.Builder = this.apply {
        config.cancelBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun cancelBtnListener(cancelBtnListener: Function1<DialogInterface, Unit>):
            ChooseListDialogFacade.Builder = this.apply {
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
        config.items.employIfChanged()
        config.itemCenter.employIfChanged()
        config.itemLabelStyle.employIfChanged()
        config.iconColor.employIfChanged()
        config.iconPosition.employIfChanged()
        config.singleChoice.employIfChanged()
        config.defaultChoosePos.employIfChanged()
        config.confirmBtnLabel.employIfChanged()
        config.confirmBtnLabelStyle.employIfChanged()
        config.confirmBtnListener.employIfChanged()
        config.cancelBtnLabel.employIfChanged()
        config.cancelBtnLabelStyle.employIfChanged()
        config.cancelBtnListener.employIfChanged()
    }

    private inner class InnerClass : ChooseListDialogFacade.Updater {
        override fun dimBehind(dim: Boolean): ChooseListDialogFacade.Updater = this.apply {
            config.dimBehind.update(value = dim, employ = false)
        }

        override fun cancelable(cancelable: Boolean): ChooseListDialogFacade.Updater = this.apply {
            config.cancelable.update(value = cancelable, employ = false)
        }

        override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): ChooseListDialogFacade.Updater =
            this.apply {
                config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
            }

        override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
                ChooseListDialogFacade.Updater = this.apply {
            config.dialogShowListener.update(value = onShowListener, employ = false)
        }

        override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
                ChooseListDialogFacade.Updater = this.apply {
            config.dialogDismissListener.update(value = onDismissListener, employ = false)
        }

        override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
                ChooseListDialogFacade.Updater = this.apply {
            config.dialogCancelListener.update(value = onCancelListener, employ = false)
        }

        override fun title(title: String): ChooseListDialogFacade.Updater = this.apply {
            config.title.update(value = title, employ = false)
        }

        override fun titleStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): ChooseListDialogFacade.Updater = this.apply {
            config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun items(items: List<String>): ChooseListDialogFacade.Updater = this.apply {
            config.items.update(value = items, employ = false)
        }

        override fun itemCenter(itemCenter: Boolean): ChooseListDialogFacade.Updater = this.apply {
            config.itemCenter.update(value = itemCenter, employ = false)
        }

        override fun itemLabelStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): ChooseListDialogFacade.Updater = this.apply {
            config.itemLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun iconColor(iconColor: Int): ChooseListDialogFacade.Updater = this.apply {
            config.iconColor.update(value = iconColor, employ = false)
        }

        override fun iconColorResource(@ColorRes iconColorResource: Int): ChooseListDialogFacade.Updater =
            this.apply {
                config.iconColor.update(value = iconColorResource.resourceToColor(), employ = false)
            }

        override fun iconPosition(iconPosition: Int): ChooseListDialogFacade.Updater = this.apply {
            config.iconPosition.update(value = iconPosition, employ = false)
        }

        override fun singleChoice(singleChoice: Boolean): ChooseListDialogFacade.Updater =
            this.apply {
                config.singleChoice.update(value = singleChoice, employ = false)
            }

        override fun defaultChoosePos(defaultChoosePos: List<Int>): ChooseListDialogFacade.Updater =
            this.apply {
                config.defaultChoosePos.update(value = defaultChoosePos, employ = false)
            }

        override fun confirmBtnLabel(confirmBtnLabel: String): ChooseListDialogFacade.Updater =
            this.apply {
                config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
            }

        override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
                ChooseListDialogFacade.Updater = this.apply {
            config.confirmBtnLabel.update(
                value = confirmBtnLabelResource.resourceToString(), employ
                = false
            )
        }

        override fun confirmBtnLabelStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): ChooseListDialogFacade.Updater = this.apply {
            config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override
        fun confirmBtnListener(confirmBtnListener: Function2<DialogInterface, List<ChosenItem>, Unit>):
                ChooseListDialogFacade.Updater = this.apply {
            config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
        }

        override fun cancelBtnLabel(cancelBtnLabel: String): ChooseListDialogFacade.Updater =
            this.apply {
                config.cancelBtnLabel.update(value = cancelBtnLabel, employ = false)
            }

        override fun cancelBtnLabelStyle(
            color: Int?,
            size: Float?,
            bold: Boolean?,
        ): ChooseListDialogFacade.Updater = this.apply {
            config.cancelBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun cancelBtnListener(cancelBtnListener: Function1<DialogInterface, Unit>):
                ChooseListDialogFacade.Updater = this.apply {
            config.cancelBtnListener.update(value = cancelBtnListener, employ = false)
        }

        override fun commit(): ChooseListDialogFacade.Handle {
            employConfig()
            return this@ChooseListDialogImplementations
        }
    }
}
