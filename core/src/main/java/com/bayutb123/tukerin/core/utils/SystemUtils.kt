package com.bayutb123.tukerin.core.utils

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.util.Locale

object SystemUtils {
    fun getUserLocation(context: Context, onCityObtained: (String, String) -> Unit, onFailure: (String) -> Unit) {
        getUserLongLatAlt(context, onLongLatAltObtained = { latitude, longitude, _ ->
            val city = getCity(context, latitude, longitude)
            val localArea = getLocalArea(context, latitude, longitude)
            onCityObtained(city, localArea)
        }, onFailure = onFailure)
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

    private fun getCity(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return addresses?.get(0)?.subAdminArea?.replace("Kota ", "") ?: ""
    }

    private fun getLocalArea(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return addresses?.get(0)?.locality?.replace("Kecamatan ", "") ?: ""
    }
}