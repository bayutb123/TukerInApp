
package com.bayutb123.tukerin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.bayutb123.tukerin.data.source.local.entity.ChatEntity
import com.bayutb123.tukerin.data.source.local.entity.MessageEntity

@Dao
interface TukerInDao {
    @Query("SELECT * FROM chat WHERE userId = :userId")
    fun getAllChats(userId: Int): List<ChatEntity>

    @Query("SELECT * FROM message WHERE chatId = :chatId")
    fun getAllMessage(chatId: Int): List<MessageEntity>

    @Transaction
    suspend fun insertAllChat(chat: List<ChatEntity>) {
        chat.forEach {
            insertChat(it)
        }
    }

    @Transaction
    suspend fun insertAllMessage(message: List<MessageEntity>) {
        message.forEach {
            insertMessage(it)
        }
    }

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

}