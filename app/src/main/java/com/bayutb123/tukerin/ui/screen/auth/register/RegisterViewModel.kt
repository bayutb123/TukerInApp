package com.bayutb123.tukerin.ui.screen.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        password: String
    ) {
        viewModelScope.launch {
            val user = authUseCase.register(name, email, password)
            if (user != null) {
                _state.value = RegisterState.Success(
                    user = user,
                    msg = "Register success"
                )
            } else {
                _state.value = RegisterState.Error("Register failed")
            }
        }
    }

    fun resetState() {
        _state.value = RegisterState.Idle
    }

}