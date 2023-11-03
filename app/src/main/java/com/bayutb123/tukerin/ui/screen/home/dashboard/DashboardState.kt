package com.bayutb123.tukerin.ui.screen.home.dashboard

import com.bayutb123.tukerin.domain.model.Post

sealed class DashboardState {
    data class Success(val data: List<Post>) : DashboardState()
    data class Failed(val code: Int) : DashboardState()
    object Loading : DashboardState()
    object Empty : DashboardState()

}
