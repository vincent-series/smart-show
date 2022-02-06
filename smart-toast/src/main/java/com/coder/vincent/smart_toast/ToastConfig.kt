package com.coder.vincent.smart_toast

interface ToastConfig {
    val alias: String
    var boundPageId: String
    var message: CharSequence
    var duration: Int
    var location: Location
    fun isSameContent(config: ToastConfig?): Boolean
    fun isSameLocation(config: ToastConfig?): Boolean
}

data class Location(
    val gravity: Int,
    val xOffset: Int,
    val yOffset: Int,
)