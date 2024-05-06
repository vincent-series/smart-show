package com.coder.vincent.smart_toast.bean

class Duration private constructor(val value: Int) {
    companion object {
        @JvmField
        val SHORT: Duration = Duration(2000)

        @JvmField
        val LONG: Duration = Duration(3500)

        @JvmField
        val INDEFINITE: Duration = Duration(0)

        @JvmStatic
        fun of(value: Int): Duration = Duration(value)
    }
}