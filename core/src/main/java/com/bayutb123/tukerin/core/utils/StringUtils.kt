package com.bayutb123.tukerin.core.utils

object StringUtils {
    fun preparePhoneNumber(value: String?) : String? {
        // convert to with +62 if starts with 0 and start with 8
        if (value == null) return null
        return if (value.startsWith("0")) {
            "+62" + value.substring(1)
        } else {
            "+62$value"
        }
    }

    fun keepSymbols(value: String) : String {
        // keep \n, 	, \r
        return value.replace("\n", "/\n/").replace("\r", "/\r/")
    }
}