package com.bayutb123.tukerin.ui.screen.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel(){

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state = _state.asStateFlow()

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        _state.value = RegisterState.Loading
        if (password == confirmPassword) {
            viewModelScope.launch {
                when (val result  = authUseCase.register(name, email, password)) {
                    is Resource.Success -> {
                        result.result.let {
                            _state.value = RegisterState.Success(
                                user = result.result as User,
                                msg = "Account registered successfully"
                            )
                        }
                    }
                    is Resource.Failed -> {
                        result.errorCode.let {
                            _state.value = RegisterState.Error(
                                errorMsg = if (it == 409) {
                                    "Email already registered"
                                } else {
                                    "Server error $it"
                                }
                            )
                        }
                    }
                    else -> {
                        if (email == "" || password == "" || name == "") {
                            _state.value = RegisterState.Error(
                                errorMsg = "One or more field are empty"
                            )
                        }
                    }
                }
            }
        } else {
            _state.value = RegisterState.Error(
                errorMsg = "Password are not match"
            )
        }
    }

    fun resetState() {
        _state.value = RegisterState.Idle
    }

}