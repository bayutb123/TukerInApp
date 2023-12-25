package com.bayutb123.tukerin.ui.utils

import java.text.SimpleDateFormat
import java.util.Locale

class Date {
    fun formatStringDate(date: String): String {

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        return try {
            val parsedDate = inputFormat.parse(date)
            outputFormat.format(parsedDate!!)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}