package com.bayutb123.tukerin.ui.screen.auth.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.repository.Resource
import com.bayutb123.tukerin.data.source.remote.NetworkResult
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel(){
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        _state.value = LoginState.Loading()
        viewModelScope.launch {
            val result = authUseCase.login(email, password)
            if (result != null) {
                _state.value = LoginState.Success(result)
                Log.d("LoginViewModel", "login: ${result.email}")
            } else {
                _state.value = LoginState.Error("Error")
                Log.d("LoginViewModel", "login: Error")
            }
        }
    }

    fun resetState() {
        _state.value = LoginState.Idle
    }
}