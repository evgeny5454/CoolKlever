package com.evgeny5454.testapp.di

import com.evgeny5454.testapp.domain.usecase.GetCharactersFromApiUseCase
import com.evgeny5454.testapp.domain.repository.Repository
import com.evgeny5454.testapp.domain.usecase.GetMarvelCharactersUseCase
import com.evgeny5454.testapp.domain.usecase.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideUseCase(repository: Repository) =
        UseCase(
            getCharactersFromApiUseCase = GetCharactersFromApiUseCase(repository),
            getMarvelCharactersUseCase = GetMarvelCharactersUseCase(repository)
        )
}