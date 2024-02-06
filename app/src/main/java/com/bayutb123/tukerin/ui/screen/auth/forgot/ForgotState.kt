package com.bayutb123.tukerin.ui.screen.auth.forgot

sealed class ForgotState {
    data class Success(val data: String) : ForgotState()
    data object Loading : ForgotState()
    data object Idle : ForgotState()
    data class Error(val message: String) : ForgotState()
}
