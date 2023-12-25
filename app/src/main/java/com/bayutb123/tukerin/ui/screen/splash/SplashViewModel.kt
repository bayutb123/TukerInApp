package com.bayutb123.tukerin.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.ui.screen.auth.login.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel (){
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState = _authState
    init {
        checkAuthState()
    }
    private fun checkAuthState() {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            delay(5000)
            val token = dataStoreUseCase.getToken()
            val userId = dataStoreUseCase.getUserId()
            if (token != null && userId != null) {
                _authState.value = AuthState.Authenticated(
                    token = token,
                    id = userId
                )
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }
}