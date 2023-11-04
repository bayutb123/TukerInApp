package com.bayutb123.tukerin.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.domain.usecase.AuthUseCase
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel(){
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state = _state.asStateFlow()
    private val _authStatus = MutableStateFlow<AuthState>(AuthState.Loading)
    val authStatus = _authStatus.asStateFlow()

    init {
        checkAuth()
    }
    private fun checkAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            val token = dataStoreUseCase.getToken()
            val id = dataStoreUseCase.getUserId()
            if (token != null && id != null) {
                _authStatus.value = AuthState.Authenticated(token, id)
            } else {
                _authStatus.value = AuthState.Unauthenticated
            }
        }
    }

    fun login(email: String, password: String) {
        _state.value = LoginState.Loading()
        viewModelScope.launch {
            when(val result = authUseCase.login(email, password)) {
                is NetworkResult.Success -> {
                    result.data?.let {
                        _state.value = LoginState.Success(it)
                        dataStoreUseCase.saveUser(it)
                    }
                }
                is NetworkResult.Error -> {
                    _state.value = LoginState.Error(
                        message = if (result.message == 401) {
                            "Email or password not valid"
                        } else if(email == "" || password == "") {
                            "One or more field are empty"
                        } else {
                            "Server error ${result.message}"
                        }
                    )
                }
                else -> {
                    if (email == "" || password == "") {
                        _state.value = LoginState.Error(
                            message = "Other error"
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