package com.bayutb123.tukerin.data.source.remote.response.auth.login

import com.bayutb123.tukerin.domain.model.User

fun LoginResponse.toDomain(): User? {
    return this.user?.let { user ->
        User(
            id = user.id,
            name = user.name,
            email = user.email,
            token = user.apiToken,
            phone = user.phone,
            isPremium = user.isPremiumUser == "1",
            rating = user.rating.toDouble(),
            trxPoints = user.trxPoints
        )
    }
}