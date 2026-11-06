package com.bayutb123.tukerin.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val token: String,
    val isPremium: Boolean,
    var rating: Double,
    var trxPoints: Int,
)
