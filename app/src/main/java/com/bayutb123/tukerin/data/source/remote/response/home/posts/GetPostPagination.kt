package com.bayutb123.tukerin.data.source.remote.response.home.posts

import com.google.gson.annotations.SerializedName

data class GetPostPagination (

    @field:SerializedName("per_page")
    val perPage: Int,

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("last_page")
    val lastPage: Int,

    @field:SerializedName("current_page")
    val currentPage: Int
)