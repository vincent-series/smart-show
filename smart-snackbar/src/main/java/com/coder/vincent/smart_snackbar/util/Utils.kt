package com.coder.vincent.smart_snackbar.util

import com.coder.vincent.series.common_lib.application
import com.coder.vincent.series.common_lib.dpToPx
import com.google.android.material.snackbar.Snackbar

object Utils {
    fun statusBarHeight(): Int {
        val resourceId =
            application.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId == 0)
            24f.dpToPx()
        else
            application.resources.getDimensionPixelSize(resourceId)
    }

    fun actionBarHeight(): Int {
        val resourceId = application.resources.getIdentifier(
            "abc_action_bar_default_height_material",
            "dimen",
            "android"
        )
        return if (resourceId == 0)
            56f.dpToPx()
        else
            application.resources.getDimensionPixelSize(resourceId)
    }
}