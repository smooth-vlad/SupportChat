package ru.scid.supportchat.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.scid.supportchat.domain.repositories.UserDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataModule {

    @Singleton
    @Provides
    fun provideUserDataRepository(): UserDataRepository {
        return UserDataRepository()
    }

}