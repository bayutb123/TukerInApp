package com.bayutb123.tukerin.ui.screen.post.newpost

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.data.source.remote.request.CreatePostRequest
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.domain.usecase.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    fun createPost(title: String, description: String, uris: List<Uri>, lat: Double, long: Double, price: Long, context: Context) {
        viewModelScope.launch {
            val userId = dataStoreUseCase.getUserId()
            val requestBody = userId?.let {
                CreatePostRequest(
                    it, title, description, uris, lat, long, price
                )
            }
            when (val request = requestBody?.let { postUseCase.createPost(it, context) }) {
                is NetworkResult.Success -> {
                    Timber.tag("Result").d(request.message.toString())
                }
                is NetworkResult.Error -> {
                    Timber.tag("Result").d(request.message.toString())
                }
                else -> {
                    Timber.tag("ElseResult").d(request.toString())
                }
            }
        }
    }
}