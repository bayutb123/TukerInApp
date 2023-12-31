package com.bayutb123.tukerin.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bayutb123.tukerin.data.source.local.dao.TukerInDao
import com.bayutb123.tukerin.data.source.local.entity.ChatEntity
import com.bayutb123.tukerin.data.source.local.entity.MessageEntity

@Database(entities = [ChatEntity::class, MessageEntity::class], version = 1)
abstract class TukerInDatabase : RoomDatabase() {
    abstract fun tukerInDao(): TukerInDao

    companion object {
        const val DATABASE_NAME = "tukerin_database"
    }

}