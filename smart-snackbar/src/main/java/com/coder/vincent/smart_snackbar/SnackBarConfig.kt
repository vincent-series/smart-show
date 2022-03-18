package com.coder.vincent.smart_snackbar

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import com.coder.vincent.smart_snackbar.bean.AnimationMode
import com.coder.vincent.smart_snackbar.bean.Duration

internal class SnackBarConfig {
    lateinit var message: String
    lateinit var actionLabel: String
    lateinit var actionReaction: (View) -> Unit
    lateinit var targetParent: ViewGroup

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
