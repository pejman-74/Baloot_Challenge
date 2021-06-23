package com.baloot.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.baloot.data.database.ArticleDatabase
import com.baloot.data.model.Article
import com.baloot.data.model.RemoteKeys
import com.baloot.data.network.ArticleApis
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val query: String,
    private val articleDatabase: ArticleDatabase,
    private val articleApis: ArticleApis
) :
    RemoteMediator<Int, Article>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = articleApis.getArticle(page, query, state.config.pageSize)

            if (apiResponse.code() == 429)
                return MediatorResult.Error(TokenExpireException())

            val endOfPaginationReached = apiResponse.code() == 426

            articleDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    articleDatabase.remoteKeysDao().clearRemoteKeys()
                    articleDatabase.articleDao().clearArticles()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = apiResponse.body()?.articles?.map {
                    RemoteKeys(articleTitle = it.title, prevKey = prevKey, nextKey = nextKey)
                }
                keys?.let { articleDatabase.remoteKeysDao().insertAll(it) }
                apiResponse.body()?.articles?.let { articleDatabase.articleDao().insertAll(it) }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                articleDatabase.remoteKeysDao().remoteKeysArticleTitle(article.title)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                articleDatabase.remoteKeysDao().remoteKeysArticleTitle(article.title)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.title?.let { title ->
                articleDatabase.remoteKeysDao().remoteKeysArticleTitle(title)
            }
        }
    }
}