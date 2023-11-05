package com.bayutb123.tukerin.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.bayutb123.tukerin.data.source.local.repository.DataStoreRepositoryImpl
import com.bayutb123.tukerin.domain.repository.DataStoreRepository
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val DATA_STORE_NAME = "tukerin_data_store"
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context : Context) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(context,DATA_STORE_NAME)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(DATA_STORE_NAME) }
        )
    }

    @Provides
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>) : DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore)
    }

    @Provides
    fun provideDataStoreUseCase(dataStoreRepository: DataStoreRepository) : DataStoreUseCase {
        return DataStoreUseCase(dataStoreRepository)
    }
}