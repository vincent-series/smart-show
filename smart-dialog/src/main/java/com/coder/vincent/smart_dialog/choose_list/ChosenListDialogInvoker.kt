package com.coder.vincent.smart_dialog.choose_list

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.IconPosition
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.canShowDialog
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToString
import com.coder.vincent.series.common_lib.resourceToStringArray
import com.coder.vincent.smart_dialog.CancelBtnListener
import com.coder.vincent.smart_dialog.ItemChosenListener

internal class ChosenListDialogInvoker : ChosenListDialogFacade.Builder,
    ChosenListDialogFacade.Handle {
    private val config: ChosenListDialog.Config = ChosenListDialog.Config()

    private val updater: ChosenListDialogFacade.Updater = InnerClass()

    private val dialogFactory: ChosenListDialogFactory = ChosenListDialogFactory()

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

    override fun build(activity: Activity): ChosenListDialogFacade.Handle {
        if (!activity.canShowDialog()) {
            return this
        }
        dialog = dialogFactory.produceDialog(activity, config)
        dialog?.setOwnerActivity(activity)
        employConfig()
        return this
    }

    override fun updater(): ChosenListDialogFacade.Updater = updater

    override fun dimBehind(dim: Boolean): ChosenListDialogFacade.Builder = this.apply {
        config.dimBehind.update(value = dim, employ = false)
    }

    override fun cancelable(cancelable: Boolean): ChosenListDialogFacade.Builder = this.apply {
        config.cancelable.update(value = cancelable, employ = false)
    }

    override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): ChosenListDialogFacade.Builder =
        this.apply {
            config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
        }

    override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
            ChosenListDialogFacade.Builder = this.apply {
        config.dialogShowListener.update(value = onShowListener, employ = false)
    }

    override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
            ChosenListDialogFacade.Builder = this.apply {
        config.dialogDismissListener.update(value = onDismissListener, employ = false)
    }

    override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
            ChosenListDialogFacade.Builder = this.apply {
        config.dialogCancelListener.update(value = onCancelListener, employ = false)
    }

    override fun title(title: String): ChosenListDialogFacade.Builder = this.apply {
        config.title.update(value = title, employ = false)
    }

    override fun titleResource(@StringRes titleResource: Int): ChosenListDialogFacade.Builder =
        this.apply {
            config.title.update(value = titleResource.resourceToString(), employ = false)
        }

    override fun titleStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): ChosenListDialogFacade.Builder = this.apply {
        config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun items(items: List<String>): ChosenListDialogFacade.Builder = this.apply {
        config.items.update(value = items, employ = false)
    }

    override fun itemsResource(itemsResource: Int): ChosenListDialogFacade.Builder = this.apply {
        config.items.update(value = itemsResource.resourceToStringArray(), employ = false)
    }

    override fun itemCenter(itemCenter: Boolean): ChosenListDialogFacade.Builder = this.apply {
        config.itemCenter.update(value = itemCenter, employ = false)
    }

    override fun itemLabelStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): ChosenListDialogFacade.Builder = this.apply {
        config.itemLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun iconColor(iconColor: Int): ChosenListDialogFacade.Builder = this.apply {
        config.iconColor.update(value = iconColor, employ = false)
    }

    override fun iconColorResource(@ColorRes iconColorResource: Int): ChosenListDialogFacade.Builder =
        this.apply {
            config.iconColor.update(value = iconColorResource.resourceToColor(), employ = false)
        }

    override fun iconPosition(iconPosition: IconPosition): ChosenListDialogFacade.Builder =
        this.apply {
            config.iconPosition.update(value = iconPosition, employ = false)
        }

    override fun singleChoice(singleChoice: Boolean): ChosenListDialogFacade.Builder = this.apply {
        config.singleChoice.update(value = singleChoice, employ = false)
    }

    override fun defaultChosenPos(defaultChosenPos: List<Int>): ChosenListDialogFacade.Builder =
        this.apply {
            config.defaultChosenPos.update(value = defaultChosenPos, employ = false)
        }

    override fun confirmBtnLabel(confirmBtnLabel: String): ChosenListDialogFacade.Builder =
        this.apply {
            config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
        }

    override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
            ChosenListDialogFacade.Builder = this.apply {
        config.confirmBtnLabel.update(
            value = confirmBtnLabelResource.resourceToString(), employ =
            false
        )
    }

    override fun confirmBtnLabelStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): ChosenListDialogFacade.Builder = this.apply {
        config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override
    fun confirmBtnListener(confirmBtnListener: ItemChosenListener):
            ChosenListDialogFacade.Builder = this.apply {
        config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
    }

    override fun cancelBtnLabel(cancelBtnLabel: String): ChosenListDialogFacade.Builder =
        this.apply {
            config.cancelBtnLabel.update(value = cancelBtnLabel, employ = false)
        }

    override fun cancelBtnLabelResource(cancelBtnLabelResource: Int): ChosenListDialogFacade.Builder =
        this.apply {
            config.cancelBtnLabel.update(
                value = cancelBtnLabelResource.resourceToString(),
                employ = false
            )
        }

    override fun cancelBtnLabelStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): ChosenListDialogFacade.Builder = this.apply {
        config.cancelBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
    }

    override fun cancelBtnListener(cancelBtnListener: CancelBtnListener):
            ChosenListDialogFacade.Builder = this.apply {
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
        config.defaultChosenPos.employIfChanged()
        config.confirmBtnLabel.employIfChanged()
        config.confirmBtnLabelStyle.employIfChanged()
        config.confirmBtnListener.employIfChanged()
        config.cancelBtnLabel.employIfChanged()
        config.cancelBtnLabelStyle.employIfChanged()
        config.cancelBtnListener.employIfChanged()
    }

    private inner class InnerClass : ChosenListDialogFacade.Updater {
        override fun dimBehind(dim: Boolean): ChosenListDialogFacade.Updater = this.apply {
            config.dimBehind.update(value = dim, employ = false)
        }

        override fun cancelable(cancelable: Boolean): ChosenListDialogFacade.Updater = this.apply {
            config.cancelable.update(value = cancelable, employ = false)
        }

        override fun cancelOnTouchOutside(cancelOnTouchOutside: Boolean): ChosenListDialogFacade.Updater =
            this.apply {
                config.cancelOnTouchOutside.update(value = cancelOnTouchOutside, employ = false)
            }

        override fun dialogShowListener(onShowListener: DialogInterface.OnShowListener):
                ChosenListDialogFacade.Updater = this.apply {
            config.dialogShowListener.update(value = onShowListener, employ = false)
        }

        override fun dialogDismissListener(onDismissListener: DialogInterface.OnDismissListener):
                ChosenListDialogFacade.Updater = this.apply {
            config.dialogDismissListener.update(value = onDismissListener, employ = false)
        }

        override fun dialogCancelListener(onCancelListener: DialogInterface.OnCancelListener):
                ChosenListDialogFacade.Updater = this.apply {
            config.dialogCancelListener.update(value = onCancelListener, employ = false)
        }

        override fun title(title: String): ChosenListDialogFacade.Updater = this.apply {
            config.title.update(value = title, employ = false)
        }

        override fun titleResource(titleResource: Int): ChosenListDialogFacade.Updater =
            this.apply {
                config.title.update(value = titleResource.resourceToString(), employ = false)
            }

        override fun titleStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): ChosenListDialogFacade.Updater = this.apply {
            config.titleStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun items(items: List<String>): ChosenListDialogFacade.Updater = this.apply {
            config.items.update(value = items, employ = false)
        }

        override fun itemsResource(itemsResource: Int): ChosenListDialogFacade.Updater =
            this.apply {
                config.items.update(value = itemsResource.resourceToStringArray(), employ = false)
            }

        override fun itemCenter(itemCenter: Boolean): ChosenListDialogFacade.Updater = this.apply {
            config.itemCenter.update(value = itemCenter, employ = false)
        }

        override fun itemLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): ChosenListDialogFacade.Updater = this.apply {
            config.itemLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun iconColor(iconColor: Int): ChosenListDialogFacade.Updater = this.apply {
            config.iconColor.update(value = iconColor, employ = false)
        }

        override fun iconColorResource(@ColorRes iconColorResource: Int): ChosenListDialogFacade.Updater =
            this.apply {
                config.iconColor.update(value = iconColorResource.resourceToColor(), employ = false)
            }

        override fun iconPosition(iconPosition: IconPosition): ChosenListDialogFacade.Updater =
            this.apply {
                config.iconPosition.update(value = iconPosition, employ = false)
            }

        override fun singleChoice(singleChoice: Boolean): ChosenListDialogFacade.Updater =
            this.apply {
                config.singleChoice.update(value = singleChoice, employ = false)
            }

        override fun defaultChosenPos(defaultChosenPos: List<Int>): ChosenListDialogFacade.Updater =
            this.apply {
                config.defaultChosenPos.update(value = defaultChosenPos, employ = false)
            }

        override fun confirmBtnLabel(confirmBtnLabel: String): ChosenListDialogFacade.Updater =
            this.apply {
                config.confirmBtnLabel.update(value = confirmBtnLabel, employ = false)
            }

        override fun confirmBtnLabelResource(@StringRes confirmBtnLabelResource: Int):
                ChosenListDialogFacade.Updater = this.apply {
            config.confirmBtnLabel.update(
                value = confirmBtnLabelResource.resourceToString(), employ
                = false
            )
        }

        override fun confirmBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): ChosenListDialogFacade.Updater = this.apply {
            config.confirmBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override
        fun confirmBtnListener(confirmBtnListener: ItemChosenListener):
                ChosenListDialogFacade.Updater = this.apply {
            config.confirmBtnListener.update(value = confirmBtnListener, employ = false)
        }

        override fun cancelBtnLabel(cancelBtnLabel: String): ChosenListDialogFacade.Updater =
            this.apply {
                config.cancelBtnLabel.update(value = cancelBtnLabel, employ = false)
            }

        override fun cancelBtnLabelResource(cancelBtnLabelResource: Int): ChosenListDialogFacade.Updater =
            this.apply {
                config.cancelBtnLabel.update(
                    value = cancelBtnLabelResource.resourceToString(),
                    employ = false
                )
            }

        override fun cancelBtnLabelStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): ChosenListDialogFacade.Updater = this.apply {
            config.cancelBtnLabelStyle.update(value = TextStyle(color, size, bold), employ = false)
        }

        override fun cancelBtnListener(cancelBtnListener: CancelBtnListener):
                ChosenListDialogFacade.Updater = this.apply {
            config.cancelBtnListener.update(value = cancelBtnListener, employ = false)
        }

        override fun commit(): ChosenListDialogFacade.Handle {
            employConfig()
            return this@ChosenListDialogInvoker
        }
    }
}
