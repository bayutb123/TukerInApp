package com.bayutb123.tukerin.data.source.remote.response.home.posts

data class Pagination(
    val current_page: Int,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)