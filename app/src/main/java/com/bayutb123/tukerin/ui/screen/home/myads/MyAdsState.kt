package com.bayutb123.tukerin.ui.screen.home.myads

import com.bayutb123.tukerin.domain.model.Post

sealed class MyAdsState {
    data object Loading : MyAdsState()
    data class Success(val data: List<Post>) : MyAdsState()
    data class Error(val msg: String) : MyAdsState()
}