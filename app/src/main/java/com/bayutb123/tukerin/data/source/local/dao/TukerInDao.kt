
package com.bayutb123.tukerin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bayutb123.tukerin.data.source.local.entity.ChatEntity
import com.bayutb123.tukerin.data.source.local.entity.MessageEntity

@Dao
interface TukerInDao {
    @Query("SELECT * FROM chat where ownerId = :ownerId")
    fun getAllChats(ownerId: Int): List<ChatEntity>

    @Query("SELECT * FROM message WHERE chatId = :chatId")
    fun getAllMessage(chatId: Int): List<MessageEntity>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertChat(chats: List<ChatEntity>)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messages: List<MessageEntity>)

}