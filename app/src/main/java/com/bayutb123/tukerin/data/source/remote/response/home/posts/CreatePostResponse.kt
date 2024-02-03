package com.bayutb123.tukerin.data.source.remote.response.home.posts

import com.google.gson.annotations.SerializedName

data class CreatePostResponse(

	@field:SerializedName("post")
	val post: Post,

	@field:SerializedName("first_image")
	val firstImage: FirstImage,

	@field:SerializedName("message")
	val message: String
)

data class Post(

	@field:SerializedName("is_premium")
	val isPremium: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("longitude")
	val longitude: String
)

data class FirstImage(

	@field:SerializedName("image_name")
	val imageName: String,

	@field:SerializedName("post_id")
	val postId: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int
)
