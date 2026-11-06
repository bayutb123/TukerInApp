package com.bayutb123.tukerin.core.utils

object DoubleUtils {
    fun Double.toInteger(): Int {
        return Math.round(this).toInt()
    }
}