package com.bayutb123.tukerin.ui.screen.home.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.domain.model.UserRating
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
                            val rating = getUserRating()
                            rating.let {
                                result.data!!.rating = it?.rating ?: 0.0
                                result.data!!.trxPoints = it?.points ?: 0
                            }
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

    private suspend fun getUserRating(): UserRating? {
        val id = dataStoreUseCase.getUserId() ?: return null
        when (val result = userUseCase.getUserRating(id)) {
            is NetworkResult.Success -> {
                if (result.data != null) {
                    return result.data!!
                }
            }

            else -> {
                // Do nothing
            }
        }

        return null
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    suspend fun logout() {
        dataStoreUseCase.clearUser()
    }
}