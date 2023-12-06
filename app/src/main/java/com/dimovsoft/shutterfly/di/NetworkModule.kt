package com.dimovsoft.shutterfly.di

import com.dimovsoft.shutterfly.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

	@Singleton
	@Provides
	fun provideApiService(): ApiService {
		return ApiService.create()
	}
}