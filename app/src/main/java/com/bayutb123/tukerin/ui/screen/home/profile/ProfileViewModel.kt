package com.bayutb123.tukerin.ui.screen.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _userState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Empty)
    val userState: StateFlow<ProfileState> get() = _userState.asStateFlow()
    fun updateUserData() {
        viewModelScope.launch {
            try {
                val id = dataStoreUseCase.getUserId() ?: return@launch
                when (val result = userUseCase.getUserProfile(id)) {
                    is NetworkResult.Success -> {
                        if (result.data != null) {
                            dataStoreUseCase.saveUser(result.data!!)
                        }
                    }

                    else -> {
                        // Do nothing
                    }
                }
            } finally {
                getUserDataFromLocal()
            }
        }
    }

    private fun getUserDataFromLocal() {
        viewModelScope.launch {
            val user = dataStoreUseCase.getUser()
            if (user != null) {
                _userState.value = ProfileState.Success(user)
            }
        }
    }

    suspend fun logout() {
        dataStoreUseCase.clearUser()
    }
}