package com.bayutb123.tukerin.data.source.remote.response.home.posts

import com.bayutb123.tukerin.domain.model.Post

fun GetAllPostResponse.toPostList() : List<Post> {
    return this.posts.map { post->
        Post(
            id = post.id,
            title = post.title,
            description = post.content,
            price = post.price,
            createdAt = post.createdAt,
            address = post.city,
            thumbnailImage = post.thumbnail.imageName,
            images = listOf(post.thumbnail.imageName),
            ownerId = post.userId,
            ownerName = post.author.name,
            active = post.status == 1,
            premium = post.isPremium == 1,
        )
    }
}