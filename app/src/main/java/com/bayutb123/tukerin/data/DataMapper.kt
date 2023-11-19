package com.bayutb123.tukerin.data

import android.util.Log
import com.bayutb123.tukerin.data.source.remote.response.auth.LoginUser
import com.bayutb123.tukerin.data.source.remote.response.auth.UserRegister
import com.bayutb123.tukerin.data.source.remote.response.detail.DetailPost
import com.bayutb123.tukerin.data.source.remote.response.home.PostsItem
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.model.User

class DataMapper {
    companion object{
        fun mapLoginUserToUser(loginUser: LoginUser) : User = User (
            id = loginUser.id,
            name = loginUser.name,
            email = loginUser.email,
            token = loginUser.apiToken,
            isPremium = loginUser.isPremiumUser == 1
        )

        fun mapRegisterUserToUser(register: UserRegister) : User = User (
            id = register.id,
            name = register.name,
            email = register.email,
            token = register.apiToken,
            isPremium = false
        )

        fun mapPostResponseToPost(post: List<PostsItem>) : List<Post> {
            val result = mutableListOf<Post>()
            Log.d("DataMapper", "mapPostResponseToPost: $post")
            post.forEach {
                result.add(
                    Post(
                        id = it.id,
                        title = it.title,
                        description = it.content,
                        price = it.price,
                        thumbnailImage = it.thumnail.imageName,
                        ownerId = it.userId,
                        ownerName = it.author.name,
                        active = it.status == 1,
                        premium = it.isPremium == 1,
                        createdAt = it.createdAt,
                        address = it.city
                    )
                )
            }
            return result
        }

        fun mapPostDetailResponseToPost(post: DetailPost) : Post {
            return Post(
                id = post.id,
                title = post.title,
                description = post.content,
                price = post.price,
                thumbnailImage = post.images[0],
                ownerId = post.userId,
                ownerName = post.authorName,
                active = post.status == 1,
                premium = post.isPremium == 1,
                createdAt = post.createdAt,
                images = post.images,
                address = post.address
            )
        }
    }
}