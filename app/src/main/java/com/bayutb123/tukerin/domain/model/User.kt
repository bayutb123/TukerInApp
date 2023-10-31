package com.bayutb123.tukerin.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val token: String,
    val isPremium: Boolean
)
