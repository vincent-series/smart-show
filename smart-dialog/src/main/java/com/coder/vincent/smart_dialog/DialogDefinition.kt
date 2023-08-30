package com.coder.vincent.smart_dialog

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.StyleRes
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.dpToPx
import kotlin.math.min

interface DialogDefinition<CONFIG : DialogConfig> {
    @StyleRes
    fun dialogStyle(): Int = R.style.smart_show_dialog

    fun setupWindowAttributes(window: Window) {
        window.attributes.width = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    fun setupRootViewLayoutParams(lp: FrameLayout.LayoutParams) {
        lp.width = min(Toolkit.screenWidth() - 70.dpToPx(), 290.dpToPx())
        lp.height = FrameLayout.LayoutParams.WRAP_CONTENT
        56.dpToPx().let {
            lp.topMargin = it
            lp.bottomMargin = it
        }
    }

    fun dialogView(inflater: LayoutInflater, config: CONFIG, dialog: DialogInterface): View
}
