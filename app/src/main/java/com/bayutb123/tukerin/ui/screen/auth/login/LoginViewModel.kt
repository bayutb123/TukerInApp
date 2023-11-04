package com.bayutb123.tukerin.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.NetworkResult
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
            when(val result = authUseCase.login(email, password)) {
                is NetworkResult.Success -> {
                    result.data?.let {
                        _state.value = LoginState.Success(it)
                    }
                }
                is NetworkResult.Error -> {
                    _state.value = LoginState.Error(
                        message = if (result.message == 401) {
                            "Email or password not valid"
                        } else {
                            "Server error ${result.message}"
                        }
                    )
                }
                else -> {
                    if (email == "" || password == "") {
                        _state.value = LoginState.Error(
                            message = "One or more field are empty"
                        )
                    }
                }
            }
        }
    }

    fun resetState() {
        _state.value = LoginState.Idle
    }
}