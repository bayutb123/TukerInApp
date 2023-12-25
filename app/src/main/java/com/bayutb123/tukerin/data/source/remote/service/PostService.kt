package com.bayutb123.tukerin.data.source.remote.service

import com.bayutb123.tukerin.data.source.remote.response.auth.LoginResponse
import com.bayutb123.tukerin.data.source.remote.response.auth.RegisterResponse
import com.bayutb123.tukerin.data.source.remote.response.detail.DetailPostResponse
import com.bayutb123.tukerin.data.source.remote.response.home.GetAllPostResponse
import com.bayutb123.tukerin.data.source.remote.response.home.SuggestionsResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {
    @GET("post/all/{user_id}")
    suspend fun getAllPosts(
        @Path("user_id") userId: Int,
        @Query("page") page: Int
    ) : Response<GetAllPostResponse>

    @GET("post/search/{query}/{user_id}")
    suspend fun searchPost(
        @Path("query") query: String,
        @Path("user_id") userId: Int
    ) : Response<GetAllPostResponse>

    @GET("post/search/suggestion/{query}/{user_id}")
    suspend fun getSuggestions(
        @Path("query") query: String,
        @Path("user_id") userId: Int
    ) : Response<SuggestionsResponse>

    @GET("post/get/{post_id}")
    suspend fun getPost(
        @Path("post_id") postId: Int
    ) : Response<DetailPostResponse>

}