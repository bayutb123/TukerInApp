package com.bayutb123.tukerin.data.source.remote.service

import com.bayutb123.tukerin.data.source.remote.response.user.UserProfileResponse
import com.bayutb123.tukerin.data.source.remote.response.user.UserRatingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("user/get/{id}")
    suspend fun getUserProfile(
        @Path("id") id: Int
    ) : Response<UserProfileResponse>

    @GET("user/rating/{user_id}")
    suspend fun getUserRating(
        @Path("user_id") user_id: Int
    ) : Response<UserRatingResponse>
}