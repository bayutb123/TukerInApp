package com.bayutb123.tukerin.data.source.remote.response.home.posts

data class Author(
    val api_token: String,
    val created_at: String,
    val email: String,
    val id: Int,
    val is_premium_user: Int,
    val name: String,
    val profile_photo_path: Any,
    val updated_at: String,
    val verified_at: Any
)