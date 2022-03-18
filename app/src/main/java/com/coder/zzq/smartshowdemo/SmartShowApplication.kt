package com.coder.zzq.smartshowdemo

import android.app.Application
import com.coder.vincent.series.common_lib.VincentLibDevTool


/**
 * Created by 朱志强 on 2017/11/12.
 */
class SmartShowApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        VincentLibDevTool.printDevLog = true
    }
}