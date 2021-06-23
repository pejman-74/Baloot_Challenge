package com.baloot.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baloot.data.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Query("SELECT * FROM ARTICLE WHERE title LIKE :query OR description LIKE :query OR content LIKE :query ")
    fun articleByName(query: String): PagingSource<Int, Article>

    @Query("DELETE FROM ARTICLE")
    suspend fun clearArticles()
}