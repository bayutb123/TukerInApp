package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.domain.repository.PostRepository
import javax.inject.Inject

class PostCategoryUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun getPostCategories() = postRepository.getPostCategories()
    suspend fun getPostSubCategory(categoryId: Int) = postRepository.getPostSubCategory(categoryId)
}