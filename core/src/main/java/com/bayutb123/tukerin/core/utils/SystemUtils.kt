package com.bayutb123.tukerin.core.utils

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

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

    @Suppress("MissingPermission")
    fun getUserLongLatAlt(
        context: Context,
        onLongLatAltObtained: (Double, Double, Double) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                    return CancellationTokenSource().token
                }

                override fun isCancellationRequested(): Boolean {
                    return false
                }

            }
        ).addOnSuccessListener { latLngAlt ->
            if (latLngAlt != null) {
                onLongLatAltObtained(latLngAlt.latitude, latLngAlt.longitude, latLngAlt.altitude)
            } else {
                onFailure("Location is not available")
            }
        }.addOnFailureListener { exception ->
            onFailure(exception.message ?: "Error fetching location")
        }
    }
}