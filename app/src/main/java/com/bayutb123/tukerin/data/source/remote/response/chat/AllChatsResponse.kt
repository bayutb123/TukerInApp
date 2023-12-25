package com.bayutb123.tukerin.data.source.remote.response.chat

import com.google.gson.annotations.SerializedName

data class AllChatsResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("receiver")
	val receiver: Receiver,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("receiver_id")
	val receiverId: Int,

	@field:SerializedName("context")
	val context: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("last_message")
	val lastMessage: LastMessage,

	@field:SerializedName("id")
	val id: Int
)

data class Receiver(

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

data class LastMessage(

	@field:SerializedName("is_read")
	val isRead: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("receiver_id")
	val receiverId: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("sender_id")
	val senderId: Int,

	@field:SerializedName("chat_id")
	val chatId: Int
)
