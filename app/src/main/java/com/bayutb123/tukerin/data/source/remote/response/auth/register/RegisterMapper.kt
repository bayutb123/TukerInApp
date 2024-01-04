package com.bayutb123.tukerin.data.source.remote.response.auth.register

import com.bayutb123.tukerin.domain.model.User

fun RegisterResponse.toDomain(): User {
    return this.user.let { user ->
        User(
            id = user.id,
            name = user.name,
            email = user.email,
            token = user.apiToken,
            isPremium = false
        )
    }
}