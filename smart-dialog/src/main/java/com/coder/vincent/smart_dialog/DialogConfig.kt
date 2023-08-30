package com.coder.vincent.smart_dialog

import android.content.DialogInterface
import com.coder.vincent.series.common_lib.bean.KData

open class DialogConfig {
    val dimBehind = KData(true)
    val cancelable = KData(true)
    val cancelOnTouchOutside = KData(false)
    val dialogShowListener = KData<DialogInterface.OnShowListener>()
    val dialogDismissListener = KData<DialogInterface.OnDismissListener>()
    val dialogCancelListener = KData<DialogInterface.OnCancelListener>()
}