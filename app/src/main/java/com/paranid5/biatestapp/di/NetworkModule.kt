package com.paranid5.biatestapp.di

import com.paranid5.biatestapp.domain.AuthClient
import com.paranid5.biatestapp.domain.authRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideAuthClient() = authRetrofit.create(AuthClient::class.java)
}