package com.example.truecallerassignment.di

import com.example.truecallerassignment.data.repository.WebContentRepositoryImpl
import com.example.truecallerassignment.domain.repository.WebContentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindWebContentRepository(
        webContentRepositoryImpl: WebContentRepositoryImpl
    ): WebContentRepository
}