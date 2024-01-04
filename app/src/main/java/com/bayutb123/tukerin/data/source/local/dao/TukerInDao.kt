
package com.bayutb123.tukerin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bayutb123.tukerin.data.source.local.entity.chat.ChatEntity
import com.bayutb123.tukerin.data.source.local.entity.message.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TukerInDao {
    @Query("SELECT * FROM chat where ownerId = :ownerId")
    abstract fun getAllChats(ownerId: Int): Flow<List<ChatEntity>>

    @Query("SELECT * FROM message WHERE chatId = :chatId")
    abstract fun getAllMessage(chatId: Int): Flow<List<MessageEntity>>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    abstract suspend fun insertChat(chat: ChatEntity)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    abstract suspend fun insertChat(chats: List<ChatEntity>)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    abstract suspend fun insertMessage(message: MessageEntity)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    abstract suspend fun insertMessage(messages: List<MessageEntity>)

}