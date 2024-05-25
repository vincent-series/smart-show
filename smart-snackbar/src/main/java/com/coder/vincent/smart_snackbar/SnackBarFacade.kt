package com.coder.vincent.smart_snackbar

import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.IconPosition
import com.coder.vincent.smart_snackbar.bean.SnackBarStyle

interface SnackBarFacade {
    interface Overall : ShowApi {
        fun config(): ConfigSetter
    }

    interface ShowApi {
        fun show(msg: String)

        fun show(msg: String, actionLabel: String)

        fun show(msg: String, actionLabel: String, actionReaction: (View) -> Unit)

        fun show(@StringRes msg: Int)

        fun show(@StringRes msg: Int, @StringRes actionLabel: Int)

        fun show(@StringRes msg: Int, @StringRes actionLabel: Int, actionReaction: (View) -> Unit)

        fun showLong(msg: String)

        fun showLong(msg: String, actionLabel: String)

        fun showLong(msg: String, actionLabel: String, actionReaction: (View) -> Unit)

        fun showLong(@StringRes msg: Int)

        fun showLong(@StringRes msg: Int, @StringRes actionLabel: Int)

        fun showLong(
            @StringRes msg: Int,
            @StringRes actionLabel: Int,
            actionReaction: (View) -> Unit
        )

        fun showIndefinite(msg: String)

        fun showIndefinite(msg: String, actionLabel: String)

        fun showIndefinite(msg: String, actionLabel: String, actionReaction: (View) -> Unit)

        fun showIndefinite(@StringRes msg: Int)

        fun showIndefinite(@StringRes msg: Int, @StringRes actionLabel: Int)

        fun showIndefinite(
            @StringRes msg: Int,
            @StringRes actionLabel: Int,
            actionReaction: (View) -> Unit
        )
    }

    interface ConfigSetter {
        fun themeStyle(style: SnackBarStyle): ConfigSetter
        fun backgroundColor(@ColorInt color: Int): ConfigSetter
        fun backgroundColorResource(@ColorRes colorResId: Int): ConfigSetter
        fun icon(@DrawableRes iconResId: Int): ConfigSetter
        fun iconPosition(iconPosition: IconPosition): ConfigSetter
        fun iconSizeDp(size: Float): ConfigSetter
        fun iconPaddingDp(padding: Float): ConfigSetter
        fun messageColor(@ColorInt color: Int): ConfigSetter
        fun messageColorResource(@ColorRes colorResId: Int): ConfigSetter
        fun messageBold(bold: Boolean): ConfigSetter
        fun messageSizeSp(size: Float): ConfigSetter
        fun actionLabelColor(@ColorInt color: Int): ConfigSetter
        fun actionLabelColorResource(@ColorRes colorResId: Int): ConfigSetter
        fun actionLabelBold(bold: Boolean): ConfigSetter
        fun actionLabelSizeSp(size: Float): ConfigSetter
        fun commit(): ShowApi
    }
}