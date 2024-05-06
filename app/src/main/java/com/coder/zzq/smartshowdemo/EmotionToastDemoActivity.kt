package com.coder.zzq.smartshowdemo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.smart_toast.SmartToast
import com.coder.vincent.smart_toast.bean.Duration
import com.coder.zzq.smartshowdemo.databinding.ActivityEmotionToastDemoBinding

class EmotionToastDemoActivity : AppCompatActivity() {
    var bgColor: Int = Color.parseColor("#cc000000")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityEmotionToastDemoBinding.inflate(layoutInflater).apply {
            showToast.setOnClickListener {
                bgColor = when (bgColorGroup.checkedRadioButtonId) {
                    R.id.bg_color_one -> R.color.colorPrimary.resourceToColor()
                    R.id.bg_color_two -> R.color.colorAccent.resourceToColor()
                    else -> Color.parseColor("#cc000000")
                }
                val shorDuration = durationGroup.checkedRadioButtonId == R.id.duration_short
                val msgColor =
                    if (msgColorGroup.checkedRadioButtonId == R.id.color_default) Color.WHITE else Color.RED
                val msgSize =
                    if (msgSizeGroup.checkedRadioButtonId == R.id.size_default) 14f else 20f
                SmartToast.emotion()
                    .config()
                    .backgroundColor(bgColor)
                    .messageStyle(msgColor, msgSize, msgBoldGroup.checkedRadioButtonId == R.id.bold)
//                    .duration(if (shorDuration) Duration.SHORT else Duration.LONG)
                    .duration(Duration.INDEFINITE)
                    .commit()
                    .apply {
                        when (emotionGroup.checkedRadioButtonId) {
                            R.id.emotion_info -> info(R.string.net_fine)
                            R.id.emotion_success -> success(R.string.delete_succ)
                            R.id.emotion_error -> error(R.string.data_parse_error)
                            R.id.emotion_warning -> warning(R.string.power_low)
                            R.id.emotion_waiting -> waiting(R.string.wait_to_download)
                            R.id.emotion_fail -> fail(R.string.save_fail)
                            R.id.emotion_complete -> complete(R.string.download_complete)
                            R.id.emotion_forbid -> forbid(R.string.forbid_op)
                        }
                    }
            }
        }.root)
    }
}