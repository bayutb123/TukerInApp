package com.bayutb123.tukerin.ui.utils

import android.content.Context
import android.net.ConnectivityManager

class Connection(
    context: Context
) {
    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isConnected(): Boolean {
        // check if the device is connected to the internet
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}