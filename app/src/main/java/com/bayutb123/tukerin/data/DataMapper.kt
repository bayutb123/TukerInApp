package com.bayutb123.tukerin.data

import com.bayutb123.tukerin.data.source.remote.response.auth.LoginUser
import com.bayutb123.tukerin.data.source.remote.response.auth.UserRegister
import com.bayutb123.tukerin.data.source.remote.response.chat.AllChatsResponse
import com.bayutb123.tukerin.data.source.remote.response.detail.DetailPost
import com.bayutb123.tukerin.data.source.remote.response.home.PostsItem
import com.bayutb123.tukerin.data.source.remote.response.message.AllMessageResponse
import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message
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
                        price = it.price,
                        thumbnailImage = it.thumbnail.imageName,
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

        fun mapChatResponseToChat(chatsResponse: AllChatsResponse) : List<Chat> {
            val result = mutableListOf<Chat>()

            chatsResponse.data.forEach { dataItem ->
                result.add(Chat(
                    id = dataItem.id,
                    userId = dataItem.userId,
                    receiver = dataItem.receiverId,
                    context = dataItem.context,
                    createdAt = dataItem.createdAt,
                    lastMessage = dataItem.lastMessage.message
                ))
            }
            return result
        }

        fun mapMessageResponseToMessage(messagesResponse: AllMessageResponse) : List<Message> {
            val result = mutableListOf<Message>()

            messagesResponse.data.forEach { message ->
                result.add(Message(
                    id = message.id,
                    senderId = message.senderId,
                    receiverId = message.receiverId,
                    message = message.message,
                    isRead = message.isRead == 1,
                    attachment = null, // Sementara null dulu
                    chatId = message.chatId,
                    createdAt = message.createdAt
                ))
            }

            return result
        }
    }
}