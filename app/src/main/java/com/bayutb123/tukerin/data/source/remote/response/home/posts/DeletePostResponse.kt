package com.bayutb123.tukerin.data.source.remote.response.home.posts

import com.google.gson.annotations.SerializedName

data class DeletePostResponse(

	@field:SerializedName("post")
	val post: DeletedPost? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DeletedPost(

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: Any? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("is_premium")
	val isPremium: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("can_trade_in")
	val canTradeIn: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)
