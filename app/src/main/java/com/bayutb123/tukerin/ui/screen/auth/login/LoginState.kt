package com.bayutb123.tukerin.ui.screen.auth.login

import com.bayutb123.tukerin.domain.model.User

sealed class LoginState(
    val data: User? = null,
    val message: String? = null
) {
    class Success(data: User) : LoginState(data)
    class Loading(data: User? = null) : LoginState(data)
    class Error(message: String) : LoginState(null, message)
}