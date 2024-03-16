package com.bayutb123.tukerin.core.utils

import android.content.Context
import android.location.LocationManager

object SystemUtils {
    fun getUserLocation(context: Context, onLocationObtained: (Double, Double) -> Unit) {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                onLocationObtained(location.latitude, location.longitude)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}