package com.baloot.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.baloot.data.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getPaginatedArticle(query: String,pageConfig: PagingConfig): Flow<PagingData<Article>>
}