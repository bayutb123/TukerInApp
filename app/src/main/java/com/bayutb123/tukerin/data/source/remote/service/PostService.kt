package com.bayutb123.tukerin.data.source.remote.service

import com.bayutb123.tukerin.data.source.remote.response.detail.DetailPostResponse
import com.bayutb123.tukerin.data.source.remote.response.home.posts.CreatePostResponse
import com.bayutb123.tukerin.data.source.remote.response.home.posts.CreateReviewResponse
import com.bayutb123.tukerin.data.source.remote.response.home.posts.DeletePostResponse
import com.bayutb123.tukerin.data.source.remote.response.home.posts.GetAllPostResponse
import com.bayutb123.tukerin.data.source.remote.response.home.posts.GetPostCategoriesResponse
import com.bayutb123.tukerin.data.source.remote.response.home.posts.UpdatePostResponse
import com.bayutb123.tukerin.data.source.remote.response.home.suggestions.SuggestionsResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
        @Part image: Array<MultipartBody.Part>,
        @Part("latitude") lat: Double,
        @Part("longitude") long: Double,
        @Part("price") price: Long,
        @Part("type") type: String,
        @Part("can_trade_in") canTrade: Int
    ) : Response<CreatePostResponse>

    @GET("my/post/all/{userId}")
    suspend fun getMyPosts(
        @Path("userId") userId: Int,
        @Query("page") page: Int
    ) : Response<GetAllPostResponse>

    @GET("post/category/all")
    suspend fun getPostCategories() : Response<GetPostCategoriesResponse>

    @GET("post/category/{parent_id}")
    suspend fun getPostSubCategory(
        @Path("parent_id") parentId: Int
    ) : Response<GetPostCategoriesResponse>

    @DELETE("post/delete/{post_id}")
    suspend fun deletePost(
        @Path("post_id") postId: Int
    ) : Response<DeletePostResponse>

    @GET("post/active/{user_id}")
    suspend fun getActivePosts(
        @Path("user_id") userId: Int
    ) : Response<GetAllPostResponse>

    @POST("post/update/publish/status")
    @FormUrlEncoded
    suspend fun updatePostPublishStatus(
        @Field("post_id") postId: Int,
        @Field("peer_id") peerId: Int,
        @Field("publish_status_id") publishStatusId: Int
    ) : Response<UpdatePostResponse>

    @POST("post/review")
    @FormUrlEncoded
    suspend fun postReview(
        @Field("user_id") userId: Int,
        @Field("post_id") postId: Int,
        @Field("rating") rating: Int,
        @Field("review") review: String
    ) : Response<CreateReviewResponse>
}