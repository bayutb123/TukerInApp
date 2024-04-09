package com.bayutb123.tukerin.data.source.remote.response.auth.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: UserRegister
)

data class UserRegister(

	@field:SerializedName("trx_points")
	val trxPoints: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("verified_at")
	val verifiedAt: Any,

	@field:SerializedName("api_token")
	val apiToken: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("is_premium_user")
	val isPremiumUser: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("profile_photo_path")
	val profilePhotoPath: Any,

	@field:SerializedName("email")
	val email: String
)
