package com.bayutb123.tukerin.data.source.remote.response.user

data class UserRatingResponse(
    val name: String,
    val count: Int,
    val message: String,
    val points: Int,
    val rating: Int
)

