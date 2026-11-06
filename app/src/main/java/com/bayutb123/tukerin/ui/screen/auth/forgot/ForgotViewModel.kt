package com.bayutb123.tukerin.ui.screen.auth.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val repository: AuthRepository
)  : ViewModel() {
    private val _state = MutableStateFlow<ForgotState>(ForgotState.Idle)
    val state = _state.asStateFlow()

    fun forgotPassword(email: String) {
        _state.value = ForgotState.Loading
        viewModelScope.launch {
            try {
                // TODO: Implement forgotPassword
                // simulate network request
                delay(3000)
                _state.value = ForgotState.Success("Password reset link sent to $email")
            } catch (e: IOException) {
                _state.value = ForgotState.Error(e.message.toString())
            } catch (e: Exception) {
                _state.value = ForgotState.Error(e.message.toString())
            }
        }
    }

    fun setIdle() {
        viewModelScope.launch {
            _state.value = ForgotState.Idle
        }
    }
}