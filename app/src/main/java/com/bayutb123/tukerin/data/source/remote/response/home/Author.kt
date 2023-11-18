package com.bayutb123.tukerin.data.source.remote.response.home

import com.google.gson.annotations.SerializedName

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
    val profilePhotoPath: String,

    @field:SerializedName("email")
    val email: String
)