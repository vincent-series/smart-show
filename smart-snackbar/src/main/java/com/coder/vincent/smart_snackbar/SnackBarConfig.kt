package com.coder.vincent.smart_snackbar

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.smart_snackbar.bean.AnimationMode
import com.coder.vincent.smart_snackbar.bean.Duration

internal class SnackBarConfig {
    lateinit var message: String
    lateinit var actionLabel: String
    lateinit var actionReaction: (View) -> Unit
    lateinit var targetParent: ViewGroup

    @ColorInt
    var backgroundColor = Color.parseColor("#323232")

    @DrawableRes
    var icon: Int? = null

    @SnackBarIconPosition
    var iconPosition: Int = SNACK_BAR_ICON_POSITION_LEFT
    var iconSize: Int = 0
    var iconPadding: Int = 8f.dpToPx()

    @ColorInt
    var messageColor: Int? = null
    var messageSize: Float? = null
    var messageBold: Boolean? = null

    @ColorInt
    var actionLabelColor: Int? = null
    var actionLabelSize: Float? = null
    var actionLabelBold: Boolean? = null

    @SnackBarPosition
    var position: Int = SNACK_BAR_POSITION_BOTTOM
    var duration: Duration = Duration.SHORT
    var animationMode: AnimationMode = AnimationMode.SLIDE
}

@Retention(AnnotationRetention.SOURCE)
@IntDef(SNACK_BAR_POSITION_BOTTOM, SNACK_BAR_POSITION_TOP)
annotation class SnackBarPosition

const val SNACK_BAR_POSITION_TOP = 0
const val SNACK_BAR_POSITION_BOTTOM = 1

@Retention(AnnotationRetention.SOURCE)
@IntDef(SNACK_BAR_ICON_POSITION_LEFT, SNACK_BAR_ICON_POSITION_RIGHT)
annotation class SnackBarIconPosition

const val SNACK_BAR_ICON_POSITION_LEFT = 0
const val SNACK_BAR_ICON_POSITION_RIGHT = 1
