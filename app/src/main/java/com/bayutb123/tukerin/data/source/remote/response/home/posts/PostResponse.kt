package com.bayutb123.tukerin.data.source.remote.response.home.posts

data class PostResponse(
    val author: Author,
    val city: String,
    val content: String,
    val created_at: String,
    val deleted_at: String,
    val id: Int,
    val is_premium: Int,
    val latitude: Double,
    val longitude: Double,
    val price: Long,
    val status: String,
    val is_published: Int,
    val thumnail: Thumnail?,
    val title: String,
    val updated_at: String,
    val user_id: Int,
    val can_trade_in: Int,
)