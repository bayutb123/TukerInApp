package com.bayutb123.tukerin.data.source.remote.response.message

import com.google.gson.annotations.SerializedName

data class AllMessageResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("is_read")
	val isRead: Int,

	@field:SerializedName("attachments")
	val attachments: List<AttachmentsItem>,

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

data class AttachmentsItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("message_id")
	val messageId: Int,

	@field:SerializedName("id")
	val id: Int
)
