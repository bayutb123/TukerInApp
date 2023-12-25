package com.bayutb123.tukerin.domain.model

data class Message(
    val id: Int,
    val sender: String,
    val receiver: String,
    val message: String,
    val attachment: List<String>
)
