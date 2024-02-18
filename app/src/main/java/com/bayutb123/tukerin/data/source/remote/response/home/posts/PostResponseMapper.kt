package com.bayutb123.tukerin.data.source.remote.response.home.posts

import com.bayutb123.tukerin.domain.model.Post

fun GetAllPostResponse.toPostList() : List<Post> {
    return this.posts.map { post->
        Post(
            id = post.id,
            title = post.title,
            description = post.content,
            price = post.price,
            createdAt = post.created_at,
            address = post.city,
            thumbnailImage = post.thumnail?.image_name,
            images = if (post.thumnail != null) listOf(post.thumnail.image_name) else emptyList(),
            ownerId = post.user_id,
            ownerName = post.author.name,
            active = post.status == 1,
            premium = post.is_premium == 1,
        )
    }
}