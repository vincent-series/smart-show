package com.coder.zzq.smartshowdemo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.smart_toast.SmartToast
import com.coder.zzq.smartshowdemo.databinding.ActivityEmotionToastDemoBinding

class EmotionToastDemoActivity : AppCompatActivity() {
    var bgColor: Int = Color.parseColor("#cc000000")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityEmotionToastDemoBinding.inflate(layoutInflater).apply {
            bgColorGroup.setOnCheckedChangeListener { _, checkedId ->
                bgColor =
                    if (checkedId == R.id.bg_color_default) Color.parseColor("#cc000000") else R.color.colorAccent.resourceToColor()
            }
            showInfo.setOnClickListener {
                onInfoClick()
            }
            showWarning.setOnClickListener {
                onWarningClick()
            }
            showSuccess.setOnClickListener {
                onSuccessClick()
            }
            showError.setOnClickListener {
                onErrorClick()
            }
            showFail.setOnClickListener {
                onFailClick()
            }
            showComplete.setOnClickListener {
                onCompleteClick()
            }
            showForbid.setOnClickListener {
                onForbidClick()
            }
            showWaiting.setOnClickListener {
                onWaitingClick()
            }
        }.root)
    }

    private fun onInfoClick() {
        SmartToast.emotion()
            .config()
            .backgroundColor(bgColor)
            .apply()
            .info(R.string.net_fine)
    }

    private fun onSuccessClick() {
        SmartToast.emotion()
            .config()
            .backgroundColor(bgColor)
            .apply()
            .success(R.string.delete_succ)
    }

    private fun onErrorClick() {
        SmartToast.emotion()
            .config()
            .backgroundColor(bgColor)
            .apply()
            .error(R.string.data_parse_error)
    }

    private fun onWarningClick() {
        SmartToast.emotion()
            .config()
            .backgroundColor(bgColor)
            .apply()
            .warning(R.string.power_low)
    }

    private fun onFailClick() {
        SmartToast.emotion()
            .config()
            .backgroundColor(bgColor)
            .apply()
            .fail(R.string.save_fail)
    }

    private fun onCompleteClick() {
        SmartToast.emotion()
            .config()
            .backgroundColor(bgColor)
            .apply()
            .complete(R.string.download_complete)
    }

    private fun onForbidClick() {
        SmartToast.emotion()
            .config()
            .backgroundColor(bgColor)
            .apply()
            .forbid(R.string.forbid_op)
    }

    private fun onWaitingClick() {
        SmartToast.emotion()
            .config()
            .backgroundColor(bgColor)
            .apply()
            .waiting(R.string.wait_to_download)
    }
}