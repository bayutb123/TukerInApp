package com.bayutb123.tukerin.core.utils

object StringUtils {
    fun preparePhoneNumber(value: String) : String {
        // convert to with +62
        return if (value.startsWith("0")) {
            "+62${value.substring(1)}"
        } else {
            value
        }
    }
}