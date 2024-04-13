package com.bayutb123.tukerin.ui.screen.post.newpost

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.data.source.remote.request.CreatePostRequest
import com.bayutb123.tukerin.data.source.remote.response.ResponseCode
import com.bayutb123.tukerin.domain.model.PostCategory
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.domain.usecase.PostCategoryUseCase
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
    private val postCategoryUseCase: PostCategoryUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _newPostState: MutableStateFlow<NewPostState> = MutableStateFlow(NewPostState.Idle)
    private val _postCategories: MutableStateFlow<List<PostCategory>> = MutableStateFlow(emptyList())
    private val _subCategories: MutableStateFlow<SubCategoryState> = MutableStateFlow(SubCategoryState.Empty)
    val postCategories: StateFlow<List<PostCategory>> = _postCategories.asStateFlow()
    val subCategories: StateFlow<SubCategoryState> = _subCategories.asStateFlow()
    val newPostState: StateFlow<NewPostState> = _newPostState.asStateFlow()
    fun createPost(
        title: String,
        description: String,
        uris: List<Uri>,
        lat: Double,
        long: Double,
        price: Long,
        type: String,
        canTrade: Boolean,
        context: Context
    ) {
        viewModelScope.launch {
            _newPostState.value = NewPostState.Loading
            val userId = dataStoreUseCase.getUserId()
            val requestBody = userId?.let {
                CreatePostRequest(
                    it, title, description, uris, lat, long, price, type, canTrade
                )
            }
            when (val request = requestBody?.let { postUseCase.createPost(it, context) }) {
                is NetworkResult.Success -> {
                    if (request.data != null) {
                        _newPostState.value = NewPostState.Success(ResponseCode.CREATED)
                    } else {
                        _newPostState.value = NewPostState.Failed(ResponseCode.INTERNAL_SERVER_ERROR)
                    }
                }

                is NetworkResult.Error -> {
                    _newPostState.value = NewPostState.Failed(ResponseCode.BAD_REQUEST)
                }

                else -> {
                    _newPostState.value = NewPostState.Failed(ResponseCode.UNKNOWN)
                }
            }
        }
    }

    fun getPostCategories() {
        viewModelScope.launch {
            when (val result = postCategoryUseCase.getPostCategories()) {
                is NetworkResult.Success -> {
                    if (result.data != null) {
                        val resultList: MutableList<PostCategory> = result.data!!.toMutableList()
                        for (item in result.data!!) {
                            if (item.id == 1 || item.parentId != 0) {
                                resultList.remove(item)
                                Log.d("NewPostViewModel", "getPostCategories: removed ${item.name}")
                            }
                        }
                        _postCategories.value = resultList
                    }
                }
                is NetworkResult.Error -> {
                    _postCategories.value = emptyList()
                }
                else -> {
                    _postCategories.value = emptyList()
                }
            }
        }
    }

    fun getPostSubCategory(categoryId: Int) {
        _subCategories.value = SubCategoryState.Loading
        viewModelScope.launch {
            when (val result = postCategoryUseCase.getPostSubCategory(categoryId)) {
                is NetworkResult.Success -> {
                    if (result.data != null) {
                        _subCategories.value = SubCategoryState.Success(result.data?.toList() as List<PostCategory>)
                    }
                }
                is NetworkResult.Error -> {
                    _subCategories.value = SubCategoryState.Error("Error: ${result.message}")
                }
                else -> {
                    _subCategories.value = SubCategoryState.Empty
                }
            }
        }
    }

}