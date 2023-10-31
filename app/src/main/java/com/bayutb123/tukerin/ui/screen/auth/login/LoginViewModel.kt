package com.bayutb123.tukerin.ui.screen.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.repository.Resource
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
    private val _state = MutableStateFlow<LoginState>(LoginState.Loading())
    val state = _state.asStateFlow()

    fun login() {
        _state.value = LoginState.Loading()
        viewModelScope.launch {
            val result = authUseCase.login("" , "")
            if (result != null) {
                _state.value = LoginState.Success(result)
            } else {
                _state.value = LoginState.Error("User not found")
            }
        }
    }
}