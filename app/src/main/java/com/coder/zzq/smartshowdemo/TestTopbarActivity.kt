package com.coder.zzq.smartshowdemo

import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.smart_dialog.click_list.ClickListAdapter
import com.coder.vincent.smart_toast.SmartToast.classic
import com.coder.zzq.smartshow.topbar.SmartTopbar
import com.coder.zzq.smartshowdemo.databinding.ActivityTestTopbarBinding

class TestTopbarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityTestTopbarBinding.inflate(layoutInflater).apply {
            val adapter = ClickListAdapter()
            adapter.setItemCenter(itemCenter = true, notify = false)
            adapter.setItems(
                listOf(
                    "short topbar",
                    "long topbar",
                    "indefinite topbar"
                ), false
            )
            listView.adapter = adapter
            listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
                when (position) {
                    0 -> onShortClick()
                    1 -> onLongClick()
                    2 -> onIndefinite()
                }
            }
        }.root)
    }

    private fun onShortClick() {
        SmartTopbar.get(this).show(R.string.apple)
    }

    private fun onLongClick() {
        SmartTopbar.get(this).showLong(R.string.banana)
    }

    private fun onIndefinite() {
        SmartTopbar.get(this)
            .showIndefinite("为该库Star一下好么", "好的") { classic().showAtTop("Thank you") }
    }
}