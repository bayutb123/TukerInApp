package com.bayutb123.tukerin.ui.screen.home.profile

import com.bayutb123.tukerin.domain.model.User

sealed class ProfileState(val user: User? = null, val message: String? = null) {
    data class Success(val result: User) : ProfileState(result, null)
    data class Error(val msg: String) : ProfileState(message = msg)
    data object Loading : ProfileState()
    data object Empty : ProfileState()
}
