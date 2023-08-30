package com.coder.vincent.smart_dialog

import android.app.Activity
import android.app.Dialog
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog

fun Activity.createDialogInstance(@StyleRes style: Int): Dialog =
    if (this is AppCompatActivity)
        AppCompatDialog(this, style)
    else
        Dialog(this, style)
