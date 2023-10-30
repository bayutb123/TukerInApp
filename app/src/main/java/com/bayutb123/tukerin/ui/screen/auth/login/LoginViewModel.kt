package com.bayutb123.tukerin.ui.screen.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel(){
    private val _state = mutableStateOf("")
    val state = _state

    fun login() {
        viewModelScope.launch {
            authUseCase.login("" , "")
            _state.value = "Logged in"
        }
    }
}