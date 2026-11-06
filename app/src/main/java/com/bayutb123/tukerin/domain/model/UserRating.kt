package com.bayutb123.tukerin.domain.model

data class UserRating(
    val name: String,
    val rating: Double,
    val reviewCount: Int,
    val points: Int
)