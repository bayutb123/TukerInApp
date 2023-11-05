package com.bayutb123.tukerin.ui.screen.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.domain.usecase.AuthUseCase
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val dataStoreUseCase: DataStoreUseCase
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
                    is NetworkResult.Success -> {
                        result.data?.let {
                            _state.value = RegisterState.Success(
                                user = it,
                                msg = "Account registered successfully"
                            )
                            dataStoreUseCase.saveUser(it)
                        }
                    }
                    is NetworkResult.Error -> {
                        result.message.let { msg ->
                            _state.value = RegisterState.Error(
                                errorMsg = if (msg == 409) {
                                    "Email already registered"
                                } else if (email == "" || password == "" || name == "") {
                                    "One or more field are empty"
                                } else {
                                    "Server error $msg"
                                }
                            )
                        }
                    }
                    else -> {
                        if (email == "" || password == "" || name == "") {
                            _state.value = RegisterState.Error(
                                errorMsg = "Other error"
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