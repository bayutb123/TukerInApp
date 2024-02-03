package com.bayutb123.tukerin.data.source.remote.service

import com.bayutb123.tukerin.data.source.remote.response.detail.DetailPostResponse
import com.bayutb123.tukerin.data.source.remote.response.home.posts.CreatePostResponse
import com.bayutb123.tukerin.data.source.remote.response.home.posts.GetAllPostResponse
import com.bayutb123.tukerin.data.source.remote.response.home.suggestions.SuggestionsResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @Multipart
    @POST("post/create")
    suspend fun createPost(
        @Part("user_id") userId: Int,
        @Part("title") title: String,
        @Part("content") description: String,
        @Part images: List<MultipartBody.Part>,
        @Part("latitude") lat: Long,
        @Part("longitude") long: Long,
        @Part("price") price: Long
    ) : Response<CreatePostResponse>

}