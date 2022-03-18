package com.coder.vincent.smart_snackbar

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout

object SmartSnackBar {
    @JvmStatic
    fun create(activity: AppCompatActivity): SnackBarFacade.Overall =
        SnackBarInvoker(activity.window.decorView as ViewGroup)

    @JvmStatic
    fun create(coordinatorLayout: CoordinatorLayout): SnackBarFacade.Overall =
        SnackBarInvoker(coordinatorLayout)
}