package com.coder.vincent.smart_toast.compact

import android.annotation.SuppressLint
import android.os.Build
import android.os.Handler
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.smart_toast.factory.ToastConfig
import java.lang.reflect.Field

@Suppress("DEPRECATION")
internal class DecoratedToast(
    toastView: ViewBinding,
    config: ToastConfig,
    configApplyCallback: (ViewBinding, ToastConfig) -> Unit
) : AbsCompactToast(toastView, config, configApplyCallback) {
    private val toast = Toast(Toolkit.context()).apply {
        view = view()
        duration =
            if (config.duration.value.toLong() == TOAST_DURATION_LONG) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        setGravity(config().location.gravity, config().location.xOffset, config().location.yOffset)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            injectSafeHandler(this)
        }
    }

    override fun show() {
        toast.show()
    }

    override fun cancel() {
        toast.cancel()
    }
}

@SuppressLint("SoonBlockedPrivateApi")
private fun injectSafeHandler(toast: Toast) {
    try {
        val tnField: Field = Toast::class.java.getDeclaredField("mTN")
        tnField.isAccessible = true
        val tn = tnField[toast]
        val handlerField = tn.javaClass.getDeclaredField("mHandler")
        handlerField.isAccessible = true
        val handlerOfTn = handlerField[tn] as Handler
        handlerField[tn] = SafeHandler(handlerOfTn)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}