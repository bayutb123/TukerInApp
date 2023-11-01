package com.bayutb123.tukerin.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.source.remote.Resource
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
            when(val result = authUseCase.login(email, password)) {
                is Resource.Success -> {
                    _state.value = LoginState.Success(
                        result.result as User
                    )
                }
                is Resource.Failed -> {
                    _state.value = LoginState.Error(
                        message = if (result.errorCode == 401) {
                            "Email or password not valid"
                        } else {
                            "Server error ${result.errorCode}"
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