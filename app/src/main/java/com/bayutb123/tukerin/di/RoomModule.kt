package com.bayutb123.tukerin.di

import android.content.Context
import androidx.room.Room
import com.bayutb123.tukerin.data.source.local.dao.TukerInDao
import com.bayutb123.tukerin.data.source.local.TukerInDatabase
import com.bayutb123.tukerin.data.source.local.repository.RoomRepositoryImpl
import com.bayutb123.tukerin.domain.repository.RoomRepository
import com.bayutb123.tukerin.domain.usecase.RoomUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): TukerInDao {
        return Room.databaseBuilder(
            context,
            TukerInDatabase::class.java,
            TukerInDatabase.DATABASE_NAME
        ).build().tukerInDao()
    }

    @Provides
    fun provideRoomRepository(roomRepositoryImpl: RoomRepositoryImpl): RoomRepository {
        return roomRepositoryImpl
    }

    @Provides
    fun provideRoomUseCase(roomRepository: RoomRepository): RoomUseCase {
        return RoomUseCase(roomRepository)
    }
}
