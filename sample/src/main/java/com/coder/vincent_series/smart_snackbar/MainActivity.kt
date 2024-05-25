package com.coder.vincent_series.smart_snackbar

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.graphics.drawable.TintAwareDrawable


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SnackBar.makeInternal(this,findViewById(R.id.test),"《Battle Royale》是由深作欣二执导的动作、剧情、奇幻、惊悚类电影，由深作健太、深作健太担任编剧，由藤原龙也、前田亚季主演。",SnackBar.LENGTH_INDEFINITE)
            .setAction("《Battle Royale》是由深作欣二执导的动作、剧情、奇幻、惊悚类电影，由深作健太、深作健太担任编剧，由藤原龙也、前田亚季主演。",{})
            .show()
    }
}