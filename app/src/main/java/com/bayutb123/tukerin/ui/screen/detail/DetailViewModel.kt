package com.bayutb123.tukerin.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.usecase.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val postUseCase: PostUseCase
) :ViewModel() {
    private val _post = MutableStateFlow<Post?>(null)
    val post = _post.asStateFlow()

    fun getPost(postId: Int) {
        viewModelScope.launch {
            val result = postUseCase.getPost(postId)
            when (result) {
                is com.bayutb123.tukerin.data.NetworkResult.Success -> {
                    _post.value = result.data
                }
                is com.bayutb123.tukerin.data.NetworkResult.Error -> {
                    _post.value = null
                }

                else -> {
                    _post.value = null
                }
            }
        }
    }
}