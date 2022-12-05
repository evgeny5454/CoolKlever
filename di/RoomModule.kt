package com.evgeny5454.testapp.di

import android.content.Context
import com.evgeny5454.testapp.data.database.AppDao
import com.evgeny5454.testapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Singleton
    @Provides
    fun provideAppDao(appDatabase: AppDatabase): AppDao {
       return appDatabase.getAppDao()
    }
}