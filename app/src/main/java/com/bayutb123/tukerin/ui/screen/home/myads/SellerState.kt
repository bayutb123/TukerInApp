package com.bayutb123.tukerin.ui.screen.home.myads

import com.bayutb123.tukerin.domain.model.UserRating

sealed class SellerState {
    data class Seller(val userRating: UserRating) : SellerState()
    data class Error(val message: String) : SellerState()
    data object Loading : SellerState()
}
