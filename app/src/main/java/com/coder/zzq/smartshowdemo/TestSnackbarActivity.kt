package com.coder.zzq.smartshowdemo

import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.smart_dialog.click_list.ClickListAdapter
import com.coder.vincent.smart_toast.SmartToast.classic
import com.coder.zzq.smartshow.snackbar.SmartSnackbar
import com.coder.zzq.smartshowdemo.databinding.ActivityTestSnackbarBinding

class TestSnackbarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityTestSnackbarBinding.inflate(layoutInflater).apply {
            val adapter = ClickListAdapter()
            adapter.setItemCenter(itemCenter = true, notify = false)
            adapter.setItems(
                listOf(
                    "short snackbar",
                    "long snackbar",
                    "indefinite snackbar"
                ), false
            )
            listView.adapter = adapter
            listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
                when (position) {
                    0 -> onShortClick()
                    1 -> onLongClick()
                    2 -> onIndefiniteClick()
                }
            }
        }.root)
    }


    private fun onIndefiniteClick() {
        SmartSnackbar.get(this).showIndefinite(
            "为该库Star一下好么", "好的"
        ) { classic().showInCenter("Thank you") }
    }

    private fun onLongClick() {
        SmartSnackbar.get(this).show(R.string.banana)
    }

    private fun onShortClick() {
        SmartSnackbar.get(this).show(R.string.apple)
    }
}