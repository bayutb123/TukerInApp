package com.bayutb123.tukerin.data.source.remote

sealed class NetworkResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()
    data class Error(val errorMsg: String) : NetworkResult<String>()
    data class Exception(val e: Throwable) : NetworkResult<Nothing>()
}
