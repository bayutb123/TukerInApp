package com.bayutb123.tukerin.ui.screen.auth.login

sealed class AuthState {
    data class Authenticated(val token: String, val id: Int) : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
}
