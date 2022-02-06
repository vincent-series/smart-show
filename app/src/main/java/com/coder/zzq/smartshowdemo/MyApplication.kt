package com.coder.zzq.smartshowdemo

import android.app.Application
import com.coder.vincent.series.common_lib.VincentLibDevTool
import com.coder.zzq.smartshow.core.SmartShow
import com.coder.zzq.smartshow.snackbar.SmartSnackbar
import com.coder.zzq.smartshow.topbar.SmartTopbar
import com.coder.zzq.toolkit.Toolkit

/**
 * Created by 朱志强 on 2017/11/12.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        sContext = this
        SmartShow.init(this)
        Toolkit.setEnablePrintLog(true)
        SmartSnackbar.setting()
            .backgroundColorRes(R.color.colorPrimary)
            .dismissOnLeave(true)
        SmartTopbar.setting()
            .msgTextColorRes(R.color.white)
            .actionColorRes(R.color.colorAccent)
            .dismissOnLeave(true)
        VincentLibDevTool.printDevLog = true
    }

    companion object {
        var sContext: MyApplication? = null
    }
}