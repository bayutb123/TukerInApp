package com.bayutb123.tukerin.ui.utils

fun String.toPhoneNumber(): String {
    return if (this.startsWith("0")) {
        this.substring(1)
    } else {
        this
    }
}