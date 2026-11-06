package com.bayutb123.tukerin.ui.utils

import android.content.Context
import android.location.Geocoder
import java.util.Locale

object LocationUtils {
    fun convertLatLongToAddress(context: Context, lat: Double, long: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat, long, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                val locality = address.locality ?: "Unknown Location"
                val subLocality = address.subAdminArea ?: ""

                // You can customize the string as per your requirement
                return "$subLocality\n$locality".trim()
            }
        }
        return "Unknown Location"
    }

    fun convertLatLongToCityName(context: Context, lat: Double, long: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat, long, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                val subLocality = address.subAdminArea ?: ""

                // You can customize the string as per your requirement
                return subLocality.trim()
            }
        }
        return "Unknown Location"
    }
}