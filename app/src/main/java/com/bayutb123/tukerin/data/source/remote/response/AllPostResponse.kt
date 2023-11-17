package com.bayutb123.tukerin.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AllPostResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("posts")
	val posts: List<PostsItem>?
)



data class Thumnail(

	@field:SerializedName("image_name")
	val imageName: String,

	@field:SerializedName("post_id")
	val postId: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("deleted_at")
	val deletedAt: Any
)

data class PostsItem(

	@field:SerializedName("author")
	val author: Author,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("thumnail")
	val thumnail: Thumnail,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("deleted_at")
	val deletedAt: Any,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("is_premium")
	val isPremium: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("image_id")
	val imageId: Int,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("longitude")
	val longitude: String
)
