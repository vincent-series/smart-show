package com.coder.vincent.smart_snackbar

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

object SmartSnackBar {
    @JvmStatic
    fun create(activity: AppCompatActivity): SnackBarFacade.Overall =
        SnackBarInvoker(activity.window.decorView as ViewGroup)
}