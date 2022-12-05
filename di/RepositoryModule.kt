package com.evgeny5454.testapp.di

import android.content.Context
import com.evgeny5454.testapp.data.database.AppDao
import com.evgeny5454.testapp.data.network.ApiService
import com.evgeny5454.testapp.data.repository.RepositoryImpl
import com.evgeny5454.testapp.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepositoryNetwork(
        @ApplicationContext context: Context,
        apiService: ApiService,
        appDao: AppDao
    ): Repository = RepositoryImpl(apiService, context, appDao)
}
