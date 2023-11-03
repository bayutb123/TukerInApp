package com.bayutb123.tukerin.ui.screen.home.dashboard

sealed class SearchState {
    object Loading : SearchState()
    data class Success(val data: List<String>) : SearchState()
    data class Empty(val message: String) : SearchState()
}