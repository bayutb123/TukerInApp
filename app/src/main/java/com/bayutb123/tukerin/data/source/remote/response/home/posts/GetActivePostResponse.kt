package com.bayutb123.tukerin.data.source.remote.response.home.posts

data class GetActivePostResponse(
    val message: String,
    val posts: List<Post>
)

data class Post(
    val author: AuthorX,
    val city: String,
    val content: String,
    val created_at: String,
    val deleted_at: String,
    val id: Int,
    val is_premium: Int,
    val latitude: String,
    val longitude: String,
    val price: Long,
    val status: String,
    val is_published: Int,
    val thumnail: ThumnailX,
    val title: String,
    val updated_at: String,
    val user_id: Int,
    val can_trade_in: Int,
)

data class AuthorX(
    val api_token: String,
    val created_at: String,
    val email: String,
    val id: Int,
    val is_premium_user: String,
    val name: String,
    val profile_photo_path: Any,
    val rating: Int,
    val updated_at: String,
    val verified_at: Any
)

data class ThumnailX(
    val created_at: String,
    val deleted_at: Any,
    val id: Int,
    val image_name: String,
    val post_id: String,
    val updated_at: String
)