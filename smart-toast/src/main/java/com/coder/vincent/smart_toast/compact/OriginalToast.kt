package com.coder.vincent.smart_toast.compact

import android.os.Build
import android.view.View
import android.widget.Toast
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.smart_toast.bean.Duration
import com.coder.vincent.smart_toast.factory.ToastConfig


internal class OriginalToast(val identity: View, var config: ToastConfig) : CompactToast {
    private val toast: Toast =
        Toast.makeText(
            Toolkit.context(),
            config.message,
            if (config.duration.value >= Duration.LONG.value) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        )
    private var visibilityObserver: ToastVisibilityObserver? = null
    private var showing: Boolean = false

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            toast.addCallback(object : Toast.Callback() {
                override fun onToastShown() {
                    showing = true
                    visibilityObserver?.onToastVisibilityChanged(identity, true)
                }

                override fun onToastHidden() {
                    showing = false
                    visibilityObserver?.onToastVisibilityChanged(identity, false)
                }
            })
        }
    }

    override fun config(): ToastConfig = config

    override fun updateConfig(config: ToastConfig) {
        this.config = config
    }

    override fun view(): View = identity


    override fun show() {
        toast.show()
    }

    override fun cancel() {
        toast.cancel()
    }

    override fun setVisibilityObserver(visibilityObserver: ToastVisibilityObserver) {
        this.visibilityObserver = visibilityObserver;
    }

    override fun removeVisibilityObserver(visibilityObserver: ToastVisibilityObserver) {
        this.visibilityObserver = null
    }

    override fun isShowing(): Boolean = showing

}