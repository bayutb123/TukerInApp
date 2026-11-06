package com.bayutb123.tukerin.data.source.remote.response.detail

import com.bayutb123.tukerin.domain.model.Post

fun DetailPostResponse.toPost() : Post {
    return Post(
        id = this.post.id,
        title = this.post.title,
        description = this.post.content,
        price = this.post.price,
        createdAt = this.post.createdAt,
        updatedAt = this.post.updatedAt,
        address = this.post.address,
        thumbnailImage = this.post.images[0],
        images = this.post.images,
        ownerId = this.post.userId,
        ownerName = this.post.authorName,
        status = this.post.status,
        isPublished = this.post.isPublished == 1,
        premium = this.post.isPremium == 1,
        canTradeIn = this.post.canTradeIn == 1,
        latitude = this.post.latitude,
        longitude = this.post.longitude,
        ownerPhone = this.post.authorPhone
    )
}