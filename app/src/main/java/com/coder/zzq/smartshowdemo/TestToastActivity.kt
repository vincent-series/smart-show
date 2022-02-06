package com.coder.zzq.smartshowdemo

import android.os.Bundle
import android.view.Gravity
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.smart_dialog.click_list.ClickListAdapter
import com.coder.vincent.smart_toast.SmartToast
import com.coder.zzq.smartshowdemo.databinding.ActivityTestToastBinding

class TestToastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityTestToastBinding.inflate(layoutInflater).apply {
                val adapter = ClickListAdapter()
                adapter.setItemCenter(true)
                adapter.setItems(listOf(*resources.getStringArray(R.array.test_plain_toast_items)))
                listView.adapter = adapter
                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        when (position) {
                            0 -> onShowClick()
                            1 -> onShowAnotherClick()
                            2 -> onShowAtTopClick()
                            3 -> onShowInCenterClick()
                            4 -> onShowAtLocationClick()
                        }
                    }
            }.root
        )
    }

    private fun onShowClick() {
        SmartToast.classic().show(R.string.apple)
    }

    private fun onShowAnotherClick() {
        SmartToast.classic().show(R.string.mango)
    }

    private fun onShowAtTopClick() {
        SmartToast.classic().showAtTop(R.string.banana)
    }

    private fun onShowInCenterClick() {
        SmartToast.classic().showInCenter(R.string.orange)
    }

    private fun onShowAtLocationClick() {
        SmartToast.classic().showAtLocation(R.string.litchi, Gravity.LEFT or Gravity.TOP, 10f, 90f)
    }
}