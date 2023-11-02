package com.bayutb123.tukerin.ui.screen.home.dashboard

sealed class DashboardState {
    data class Success<out T>(val data: List<T>) : DashboardState()
    data class Failed(val code: Int) : DashboardState()
    object Loading : DashboardState()
    data class Exception(val exception: Throwable) : DashboardState()
    object Empty : DashboardState()

}
