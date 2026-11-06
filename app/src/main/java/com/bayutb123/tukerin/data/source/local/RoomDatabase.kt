package com.bayutb123.tukerin.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bayutb123.tukerin.data.source.local.dao.TukerInDao
import com.bayutb123.tukerin.data.source.local.entity.chat.ChatEntity
import com.bayutb123.tukerin.data.source.local.entity.message.MessageEntity

@Database(entities = [ChatEntity::class, MessageEntity::class], version = 1, exportSchema = false)
abstract class TukerInDatabase : RoomDatabase() {
    abstract fun tukerInDao(): TukerInDao

    companion object {
        const val DATABASE_NAME = "tukerin_database"
    }

}