package com.bayutb123.tukerin.core.utils

import android.util.Log

object InputConverter {
    fun Boolean.convertToInt(): Int {
        return if (this) 1 else 0
    }

    fun String.removeDoubleQuotes(): String {
        Log.d("InputConverter", "removeDoubleQuotes: $this")
        Log.d("InputConverter", "removeDoubleQuotes: ${this.replace("\"", "")}")
        return this.replace("\"", "")
    }
}