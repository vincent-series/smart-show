package com.coder.zzq.smartshowdemo

import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.smart_dialog.click_list.ClickListAdapter
import com.coder.vincent.smart_toast.SmartToast
import com.coder.zzq.smartshowdemo.databinding.ActivityTestTypeToastBinding

class TestTypeToastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityTestTypeToastBinding.inflate(layoutInflater).apply {
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
        }.root)
    }

    private fun onInfoClick() {
        SmartToast.emotion().info(R.string.net_fine)
    }

    private fun onSuccessClick() {
        SmartToast.emotion().success(R.string.delete_succ)
    }

    private fun onErrorClick() {
        SmartToast.emotion().error(R.string.data_parse_error)
    }

    private fun onWarningClick() {
        SmartToast.emotion().warning(R.string.power_low)
    }

    private fun onFailClick() {
        SmartToast.emotion().fail(R.string.save_fail)
    }

    private fun onCompleteClick() {
        SmartToast.emotion().complete(R.string.download_complete)
    }

    private fun onForbidClick() {
        SmartToast.emotion().forbid(R.string.forbid_op)
    }

    private fun onWaitingClick() {
        SmartToast.emotion().waiting(R.string.wait_to_download)
    }
}