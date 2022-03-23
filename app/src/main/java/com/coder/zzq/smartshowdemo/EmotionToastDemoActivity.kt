package com.coder.zzq.smartshowdemo

import android.graphics.Color
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.smart_dialog.click_list.ClickListAdapter
import com.coder.vincent.smart_toast.SmartToast
import com.coder.zzq.smartshowdemo.databinding.ActivityEmotionToastDemoBinding

class EmotionToastDemoActivity : AppCompatActivity() {
    var bgColor: Int = Color.parseColor("#cc000000")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityEmotionToastDemoBinding.inflate(layoutInflater).apply {
            val adapter = ClickListAdapter()
            adapter.setItemCenter(true)
            adapter.setItems(
                listOf(
                    "info",
                    "warning",
                    "success",
                    "error",
                    "fail",
                    "complete",
                    "forbid",
                    "waiting"
                )
            )
            listView.adapter = adapter
            listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                when (position) {
                    0 -> onInfoClick()
                    1 -> onWarningClick()
                    2 -> onSuccessClick()
                    3 -> onErrorClick()
                    4 -> onFailClick()
                    5 -> onCompleteClick()
                    6 -> onForbidClick()
                    7 -> onWaitingClick()
                }
            }
            bgColorGroup.setOnCheckedChangeListener { _, checkedId ->
                bgColor =
                    if (checkedId == R.id.bg_color_default) Color.parseColor("#cc000000") else R.color.colorPrimary.resourceToColor()
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