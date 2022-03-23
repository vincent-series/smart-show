package com.coder.zzq.smartshowdemo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.smart_dialog.click_list.ClickListAdapter
import com.coder.vincent.smart_snackbar.SmartSnackBar
import com.coder.zzq.smartshowdemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).apply {
            val location = IntArray(2)
            findViewById<View>(android.R.id.content).getLocationOnScreen(location)
            val adapter = ClickListAdapter()
            adapter.setItemCenter(itemCenter = true, notify = false)
            adapter.setItems(
                listOf(
                    "classic toast",
                    "emotion toast",
                    "snackbar",
                    "dialog"
                ), false
            )
            listView.adapter = adapter
            listView.onItemClickListener =
                OnItemClickListener { _, _, position, _ ->
                    when (position) {
                        0 -> onToastClick()
                        1 -> onTypeToastClick()
                        2 -> onSnackBarClick()
                        3 -> onDialogClick()
                    }
                }
        }.root)
    }

    fun onToastClick() {
        startActivity(Intent(this, ClassicToastDemoActivity::class.java))
    }


    private fun onSnackBarClick() {
        SmartSnackBar.top(this)
            .config()
            .actionLabelColor(Color.CYAN)
            .actionLabelColorResource(R.color.colorAccent)
            .actionLabelSizeSp(15f)
            .actionLabelBold(true)
            .apply()
            .show("setup action text style")

    }

    fun onTypeToastClick() {
        startActivity(Intent(this, EmotionToastDemoActivity::class.java))
    }

    private fun onDialogClick() {
        startActivity(Intent(this, TestDialogActivity::class.java))
    }
}