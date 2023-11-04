package com.bayutb123.tukerin.data

sealed class NetworkResult <T> (
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(code: Int, data: T? = null) : NetworkResult<T>(data, "Error code: $code")
    class Loading<T> : NetworkResult<T>()
}
