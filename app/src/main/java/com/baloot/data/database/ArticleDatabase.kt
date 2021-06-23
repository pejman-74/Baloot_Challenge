package com.baloot.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.baloot.data.database.dao.ArticleDao
import com.baloot.data.database.dao.RemoteKeysDao
import com.baloot.data.model.Article
import com.baloot.data.model.RemoteKeys

@Database(entities = [Article::class,RemoteKeys::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}