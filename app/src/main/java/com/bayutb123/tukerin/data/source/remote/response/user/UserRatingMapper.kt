package com.bayutb123.tukerin.data.source.remote.response.user

import com.bayutb123.tukerin.domain.model.UserRating

fun UserRatingResponse.toModel() : UserRating {
    return UserRating(
        name = name,
        reviewCount = count,
        points = points,
        rating = rating.toDouble()
    )
}