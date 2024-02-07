package com.bayutb123.tukerin.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContextProvider {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
}