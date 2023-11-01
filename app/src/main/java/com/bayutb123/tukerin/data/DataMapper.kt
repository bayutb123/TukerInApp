package com.bayutb123.tukerin.data

import com.bayutb123.tukerin.data.source.remote.response.LoginUser
import com.bayutb123.tukerin.data.source.remote.response.UserRegister
import com.bayutb123.tukerin.domain.model.User

class DataMapper {
    companion object{
        fun convertLoginUserToUser(loginUser: LoginUser) : User = User (
            id = loginUser.id,
            name = loginUser.name,
            email = loginUser.email,
            token = loginUser.apiToken,
            isPremium = loginUser.isPremiumUser == 1
        )

        fun convertRegisterUserToUser(register: UserRegister) : User = User (
            id = register.id,
            name = register.name,
            email = register.email,
            token = register.apiToken,
            isPremium = false
        )
    }
}