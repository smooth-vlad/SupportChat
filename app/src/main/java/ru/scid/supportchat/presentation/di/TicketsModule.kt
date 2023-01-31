package ru.scid.supportchat.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.scid.supportchat.data.apiservice.TicketsService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TicketsModule {

    @Provides
    fun provideTicketsService(retrofit: Retrofit): TicketsService {
        return retrofit.create(TicketsService::class.java)
    }

}