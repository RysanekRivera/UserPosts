package com.rysanek.userposts.di

import com.rysanek.userposts.data.apis.UserPostsApi
import com.rysanek.userposts.data.repositories.UserPostsRepository
import com.rysanek.userposts.domain.repositories.UserPostsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun provideUserPostsRepository(
        api: UserPostsApi
    ): UserPostsRepository = UserPostsRepositoryImpl(api)
}