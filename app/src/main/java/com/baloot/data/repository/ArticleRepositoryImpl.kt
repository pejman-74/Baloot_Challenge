package com.baloot.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.baloot.data.database.ArticleDatabase
import com.baloot.data.model.Article
import com.baloot.data.network.ArticleApis
import com.baloot.data.pagination.ArticleRemoteMediator
import kotlinx.coroutines.flow.Flow

class ArticleRepositoryImpl(
    private val articleDatabase: ArticleDatabase,
    private val articleApis: ArticleApis
) : ArticleRepository {


    @OptIn(ExperimentalPagingApi::class)
    override fun getPaginatedArticle(
        query: String,
        pageConfig: PagingConfig
    ): Flow<PagingData<Article>> =
        Pager(
            pageConfig,
            null,
            ArticleRemoteMediator(query, articleDatabase, articleApis),
            {
                val dbQuery = "%${query.replace(' ', '%')}%"
                articleDatabase.articleDao().articleByName(dbQuery)
            }).flow

}