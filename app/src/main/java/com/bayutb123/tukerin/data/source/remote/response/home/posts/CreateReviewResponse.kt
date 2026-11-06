package com.bayutb123.tukerin.data.source.remote.response.home.posts

import com.google.gson.annotations.SerializedName

data class CreateReviewResponse(

	@field:SerializedName("review")
	val review: Review? = null,

	@field:SerializedName("message")
	val message: String
)

data class Review(

	@field:SerializedName("post_id")
	val postId: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("post_owner_id")
	val postOwnerId: String,

	@field:SerializedName("review")
	val review: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("point")
	val point: Int
)
