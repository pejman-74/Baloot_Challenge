package com.baloot.di

import com.baloot.data.database.ArticleDatabase
import com.baloot.data.network.ArticleApis
import com.baloot.data.repository.ArticleRepository
import com.baloot.data.repository.ArticleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {


    @Provides
    @Singleton
    fun provideArticleRepository(
        articleDatabase: ArticleDatabase,
        articleApis: ArticleApis
    ): ArticleRepository =
        ArticleRepositoryImpl(articleDatabase, articleApis)
}