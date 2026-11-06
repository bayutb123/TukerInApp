package com.bayutb123.tukerin.ui.screen.post.newpost

import com.bayutb123.tukerin.domain.model.PostCategory

sealed class SubCategoryState(data: List<PostCategory>) {
    data class Success(val data: List<PostCategory>) : SubCategoryState(data)
    data class Error(val message: String) : SubCategoryState(emptyList())
    data object Loading : SubCategoryState(emptyList())
    data object Empty : SubCategoryState(emptyList())
}