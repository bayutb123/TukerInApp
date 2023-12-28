package com.bayutb123.tukerin.data.source.remote.response.home

import com.google.gson.annotations.SerializedName

data class GetAllPostResponse(

	@field:SerializedName("pagination")
	val pagination: Pagination,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("posts")
	val posts: List<PostsItem>
)

data class Pagination(

	@field:SerializedName("per_page")
	val perPage: Int,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("last_page")
	val lastPage: Int,

	@field:SerializedName("current_page")
	val currentPage: Int
)

data class PostsItem(

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("author")
	val author: Author,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("thumnail")
	val thumbnail: Thumbnail,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("deleted_at")
	val deletedAt: Any,

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

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("longitude")
	val longitude: String
)

data class Thumbnail(

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

data class Author(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("verified_at")
	val verifiedAt: Any,

	@field:SerializedName("api_token")
	val apiToken: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("is_premium_user")
	val isPremiumUser: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("profile_photo_path")
	val profilePhotoPath: Any,

	@field:SerializedName("email")
	val email: String
)
