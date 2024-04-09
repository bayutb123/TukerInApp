package com.bayutb123.tukerin.data.source.remote.service

import com.bayutb123.tukerin.data.source.remote.response.user.UserProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("user/get/{id}")
    suspend fun getUserProfile(
        @Path("id") id: Int
    ) : Response<UserProfileResponse>
}