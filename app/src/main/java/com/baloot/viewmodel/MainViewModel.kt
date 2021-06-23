package com.baloot.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baloot.PAGE_SIZE
import com.baloot.data.model.Article
import com.baloot.data.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val articleRepository: ArticleRepository) :
    ViewModel() {

    private val pagingConfig = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false)
    private var currentSearchResult: Flow<PagingData<Article>>? = null
    private var currentQueryValue: String? = null
    fun paginatedArticle(query: String): Flow<PagingData<Article>> {
        if (query == currentQueryValue && currentSearchResult != null)
            return currentSearchResult!!
        currentSearchResult =
            articleRepository.getPaginatedArticle(query, pagingConfig).cachedIn(viewModelScope)
        currentQueryValue = query
        return currentSearchResult!!
    }
}