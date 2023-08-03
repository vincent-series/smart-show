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
                    .messageColor(msgColor)
                    .messageSize(msgSize)
                    .messageBold(msgBoldGroup.checkedRadioButtonId == R.id.bold)
                    .commit()
                    .apply {
                        when (emotionGroup.checkedRadioButtonId) {
                            R.id.emotion_info ->
                                if (shorDuration)
                                    info(R.string.net_fine)
                                else
                                    infoLong(R.string.net_fine)

                            R.id.emotion_success ->
                                if (shorDuration)
                                    success(R.string.delete_succ)
                                else
                                    successLong(R.string.delete_succ)

                            R.id.emotion_error ->
                                if (shorDuration)
                                    error(R.string.data_parse_error)
                                else
                                    errorLong(R.string.data_parse_error)

                            R.id.emotion_warning ->
                                if (shorDuration)
                                    warning(R.string.power_low)
                                else
                                    warningLong(R.string.power_low)

                            R.id.emotion_waiting ->
                                if (shorDuration)
                                    waiting(R.string.wait_to_download)
                                else
                                    waitingLong(R.string.wait_to_download)

                            R.id.emotion_fail ->
                                if (shorDuration)
                                    fail(R.string.save_fail)
                                else
                                    failLong(R.string.save_fail)

                            R.id.emotion_complete ->
                                if (shorDuration)
                                    complete(R.string.download_complete)
                                else
                                    completeLong(R.string.download_complete)

                            R.id.emotion_forbid ->
                                if (shorDuration)
                                    forbid(R.string.forbid_op)
                                else
                                    forbidLong(R.string.forbid_op)
                        }
                    }
            }
        }.root)
    }
}