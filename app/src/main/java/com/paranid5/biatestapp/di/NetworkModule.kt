package com.paranid5.biatestapp.di

import com.paranid5.biatestapp.domain.BiaLogisticsClient
import com.paranid5.biatestapp.domain.biaLogisticsRetrofit
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
    fun provideBiaLogisticsClient(): BiaLogisticsClient =
        biaLogisticsRetrofit.create(BiaLogisticsClient::class.java)
}