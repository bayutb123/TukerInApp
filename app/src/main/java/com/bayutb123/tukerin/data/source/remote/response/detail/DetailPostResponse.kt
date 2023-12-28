package com.bayutb123.tukerin.data.source.remote.response.detail

import com.google.gson.annotations.SerializedName

data class DetailPostResponse(

    @field:SerializedName("post")
	val post: DetailPost,

    @field:SerializedName("message")
	val message: String
)

data class DetailPost(

	@field:SerializedName("author_name")
	val authorName: String,

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("deleted_at")
	val deletedAt: String?,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("is_premium")
	val isPremium: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("price")
	val price: Long,

	@field:SerializedName("author_email")
	val authorEmail: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("image_id")
	val imageId: Int,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("longitude")
	val longitude: String
)
