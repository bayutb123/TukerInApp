package com.bayutb123.tukerin.data.source.remote.response.home.posts

import com.google.gson.annotations.SerializedName

data class GetPostCategoriesResponse(

	@field:SerializedName("categories")
	val categories: List<CategoriesItem>,

	@field:SerializedName("message")
	val message: String
)

data class CategoriesItem(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("parent_id")
	val parentId: Int? = null,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int
)
