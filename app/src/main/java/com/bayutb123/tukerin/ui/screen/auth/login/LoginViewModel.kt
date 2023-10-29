package com.bayutb123.tukerin.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import com.bayutb123.tukerin.ui.screen.auth.firebase.GoogleSignInResult
import com.bayutb123.tukerin.ui.screen.auth.firebase.GoogleSignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(GoogleSignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: GoogleSignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                errorMessages = result.errorMessages
            )
        }
    }

    fun resetState() {
        _state.update {
            GoogleSignInState()
        }
    }
}