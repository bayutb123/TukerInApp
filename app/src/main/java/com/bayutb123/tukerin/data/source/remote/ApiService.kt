package com.bayutb123.tukerin.data.source.remote

import com.bayutb123.tukerin.data.source.remote.response.AllPostResponse
import com.bayutb123.tukerin.data.source.remote.response.LoginResponse
import com.bayutb123.tukerin.data.source.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<LoginResponse>

    @POST("user/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<RegisterResponse>

    @GET("post/all/{user_id}")
    suspend fun getAllPosts(
        @Path("user_id") userId: Int
    ) : Response<AllPostResponse>

    @GET("post/search/{query}/{user_id}")
    suspend fun searchPost(
        @Path("query") query: String,
        @Path("user_id") userId: Int
    ) : Response<AllPostResponse>
}