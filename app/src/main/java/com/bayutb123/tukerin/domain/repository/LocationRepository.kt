package com.bayutb123.tukerin.domain.repository

interface LocationRepository {

    suspend fun convertLatLongToAddress(lat: Double, long: Double): String

}