package com.bayutb123.tukerin.data.source.remote.response.home.posts

import com.bayutb123.tukerin.domain.model.PostCategory

fun GetPostCategoriesResponse.toModel() : List<PostCategory> {
    return this.categories.map { category->
        PostCategory(
            id = category.id,
            parentId = category.parentId ?: 0,
            name = category.name,
            description = category.description,
            createdAt = category.createdAt,
            updatedAt = category.updatedAt
        )
    }
}