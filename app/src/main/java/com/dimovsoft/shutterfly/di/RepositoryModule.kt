package com.dimovsoft.shutterfly.di

import com.dimovsoft.shutterfly.data.RepositoryContract
import com.dimovsoft.shutterfly.data.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

	@Binds
	abstract fun bindRepository(
		repositoryImpl: RepositoryImpl
	): RepositoryContract
}