package com.bayutb123.tukerin.data.source.remote.service

import com.bayutb123.tukerin.data.source.remote.response.auth.login.LoginResponse
import com.bayutb123.tukerin.data.source.remote.response.auth.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
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

}