package com.bayutb123.tukerin.data

sealed class Resource<out T> {
    data class Success<out T>(val result: T) : Resource<T>()
    data class Failed(val errorCode: Int) : Resource<Int>()
    data class Exception(val exception: kotlin.Exception) : Resource<kotlin.Exception>()
}