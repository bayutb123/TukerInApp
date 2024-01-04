package com.bayutb123.tukerin.data.source.remote.response.auth.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: UserRegister
)

data class UserRegister(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("api_token")
	val apiToken: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)
