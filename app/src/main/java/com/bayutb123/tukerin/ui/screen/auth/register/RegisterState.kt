package com.bayutb123.tukerin.ui.screen.auth.register

import com.bayutb123.tukerin.domain.model.User

sealed class RegisterState(
    val data: User?,
    val message: String?
) {
    object Loading : RegisterState(null, null)
    data class Success(val user: User, val msg: String?) : RegisterState(data = user, message = msg)
    data class Error(val errorMsg: String) : RegisterState(null, errorMsg)
    object Idle : RegisterState(null, null)
}
