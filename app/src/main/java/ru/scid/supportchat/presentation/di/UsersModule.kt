package ru.scid.supportchat.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.scid.supportchat.data.apiservice.UsersService

@Module
@InstallIn(SingletonComponent::class)
object UsersModule {

    @Provides
    fun provideUsersService(retrofit: Retrofit): UsersService {
        return retrofit.create(UsersService::class.java)
    }

}