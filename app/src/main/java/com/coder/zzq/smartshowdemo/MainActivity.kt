package com.coder.zzq.smartshowdemo

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.smart_dialog.click_list.ClickListAdapter
import com.coder.zzq.smartshowdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).apply {
            val adapter = ClickListAdapter()
            adapter.setItemCenter(true, false)
            adapter.setItems(
                listOf(
                    "classic toast",
                    "emotion toast",
                    "snackbar",
                    "topbar",
                    "dialog"
                ), false
            )
            listView.adapter = adapter
            listView.onItemClickListener =
                OnItemClickListener { _, _, position, _ ->
                    when (position) {
                        0 -> onToastClick()
                        1 -> onTypeToastClick()
                        2 -> onSnackbarClick()
                        3 -> onTopBarClick()
                        4 -> onDialogClick()
                    }
                }
        }.root)
    }

    fun onToastClick() {
        startActivity(Intent(this, TestToastActivity::class.java))
    }

    private fun onSnackbarClick() {
        startActivity(Intent(this, TestSnackbarActivity::class.java))
    }

    private fun onTopBarClick() {
        startActivity(Intent(this, TestTopbarActivity::class.java))
    }

    fun onTypeToastClick() {
        startActivity(Intent(this, TestTypeToastActivity::class.java))
    }

    private fun onDialogClick() {
        startActivity(Intent(this, TestDialogActivity::class.java))
    }
}