package com.bayutb123.tukerin.data.source.remote

import retrofit2.http.GET

interface ApiService {
    @GET("")
    suspend fun getPosts()
}