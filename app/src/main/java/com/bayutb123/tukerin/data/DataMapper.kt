package com.bayutb123.tukerin.data

import com.bayutb123.tukerin.data.source.remote.response.LoginUser
import com.bayutb123.tukerin.data.source.remote.response.PostsItem
import com.bayutb123.tukerin.data.source.remote.response.UserRegister
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
            post.forEach {
                result.add(
                    Post(
                        id = it.id,
                        title = it.title,
                        description = it.content,
                        thumbnailImage = it.thumnail.imageName,
                        ownerId = it.userId,
                        ownerName = it.author.name,
                        active = it.status == 1,
                        premium = it.isPremium == 1,
                        createdAt = it.createdAt
                    )
                )
            }
            return result
        }
    }
}