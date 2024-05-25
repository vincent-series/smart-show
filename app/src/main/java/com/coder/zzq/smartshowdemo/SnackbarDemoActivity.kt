package com.coder.zzq.smartshowdemo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.series.common_lib.bean.IconPosition
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.smart_snackbar.SmartSnackBar
import com.coder.vincent.smart_snackbar.bean.SnackBarStyle
import com.coder.zzq.smartshowdemo.databinding.ActivitySnackbarDemoBinding

class SnackbarDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivitySnackbarDemoBinding.inflate(layoutInflater).apply {
                showToast.setOnClickListener {
                    val style = if (styleGroup.checkedRadioButtonId == R.id.style_classic)
                        SnackBarStyle.CLASSIC
                    else
                        SnackBarStyle.AUTO
                    val bgColor = when (bgColorGroup.checkedRadioButtonId) {
                        R.id.bg_color_one -> R.color.colorPrimary.resourceToColor()
                        R.id.bg_color_two -> R.color.colorAccent.resourceToColor()
                        else -> Color.parseColor("#323232")
                    }
                    val icon = if (iconGroup.checkedRadioButtonId == R.id.with_icon)
                        com.coder.vincent.smart_toast.R.drawable.ic_smart_toast_success
                    else
                        0
                    val iconPosition =
                        if (iconPositionGroup.checkedRadioButtonId == R.id.icon_position_left) IconPosition.LEFT else IconPosition.RIGHT
                    if (positionGroup.checkedRadioButtonId == R.id.position_top) {
                        SmartSnackBar.top(this@SnackbarDemoActivity)
                            .config()
                            .themeStyle(style)
                            .backgroundColor(bgColor)
                            .icon(icon)
                            .iconSizeDp(18f)
                            .iconPosition(iconPosition)
                            .commit()
                            .show("top snackbar")
                    } else {
                        SmartSnackBar.bottom(this@SnackbarDemoActivity)
                            .config()
                            .themeStyle(style)
                            .backgroundColor(bgColor)
                            .icon(icon)
                            .iconSizeDp(18f)
                            .iconPosition(iconPosition)
                            .commit()
                            .show("bottom snackbar","你好")
                    }
                }
            }.root
        )

    }
}