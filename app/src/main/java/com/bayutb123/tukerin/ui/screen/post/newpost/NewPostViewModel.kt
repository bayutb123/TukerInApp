package com.bayutb123.tukerin.ui.screen.post.newpost

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.data.source.remote.request.CreatePostRequest
import com.bayutb123.tukerin.data.source.remote.response.ResponseCode
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.domain.usecase.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<NewPostState> = MutableStateFlow(NewPostState.Idle)
    val state: StateFlow<NewPostState> = _state.asStateFlow()
    fun createPost(
        title: String,
        description: String,
        uris: List<Uri>,
        lat: Double,
        long: Double,
        price: Long,
        context: Context
    ) {
        viewModelScope.launch {
            _state.value = NewPostState.Loading
            val userId = dataStoreUseCase.getUserId()
            val requestBody = userId?.let {
                CreatePostRequest(
                    it, title, description, uris, lat, long, price
                )
            }
            when (val request = requestBody?.let { postUseCase.createPost(it, context) }) {
                is NetworkResult.Success -> {
                    if (request.data != null) {
                        _state.value = NewPostState.Success(ResponseCode.CREATED)
                    } else {
                        _state.value = NewPostState.Failed(ResponseCode.INTERNAL_SERVER_ERROR)
                    }
                }

                is NetworkResult.Error -> {
                    _state.value = NewPostState.Failed(ResponseCode.BAD_REQUEST)
                }

                else -> {
                    _state.value = NewPostState.Failed(ResponseCode.UNKNOWN)
                }
            }
        }
    }
}